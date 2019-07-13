package com.xiongyx.helper;

import com.xiongyx.datasource.DataSource;
import com.xiongyx.datasource.DruidDataSourceManager;
import com.xiongyx.environment.Environment;
import com.xiongyx.model.Configuration;
import com.xiongyx.model.MappedStatement;
import com.xiongyx.util.MapperXmlScanUtil;
import com.xiongyx.util.XmlUtil;
import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.Element;

import java.io.IOException;
import java.io.Reader;
import java.net.URL;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author xiongyx
 * @date 2019/7/10
 *
 * config配置xml文件 解析器
 */
public class ConfigXmlParseHelper {

    private static final Logger logger = Logger.getLogger(ConfigXmlParseHelper.class);

    private static final String ROOT_ELEMENT = "configuration";


    public static Configuration parseConfigXml(Reader reader){
        Document document = XmlUtil.readXml(reader);
        // 获取xml中的根元素
        Element rootElement = document.getRootElement();
        // 根元素是否匹配
        if (!ROOT_ELEMENT.equals(rootElement.getName())) {
            logger.info("config-xml文件根元素不是" + ROOT_ELEMENT);
        }

        // 解析出environment配置
        Environment environment = parseEnvironmentNode(rootElement.element("environments"));
        // 解析出mapper配置
        Map<String, MappedStatement> mappedStatementMap = parseMapperNode(rootElement.element("mappers"));

        return new Configuration.Builder()
                .environment(environment)
                .mappedStatementMap(mappedStatementMap)
                .build();
    }

    /**
     * 解析Environment节点
     * */
    private static Environment parseEnvironmentNode(Element environmentNode){
        Element dataSourceNode = environmentNode.element("dataSource");

        List<Element> properties = dataSourceNode.elements("property");
        Map<String,String> dataSourceProperties = new HashMap<>();
        for(Element property : properties){
            String name = property.attributeValue("name");
            String value = property.attributeValue("value");
            dataSourceProperties.put(name,value);
        }

        // 构建数据源
        DataSource dataSource = new DruidDataSourceManager(dataSourceProperties);

        // 返回Environment
        return new Environment(dataSource);
    }

    /**
     * 解析mapper节点
     * */
    private static Map<String, MappedStatement> parseMapperNode(Element mapperNode){
        Element scanMapperNode = mapperNode.element("scan-mapper");
        // scan-mapper扫描
        if(scanMapperNode != null){
            String directorPath = scanMapperNode.attributeValue("package");
            return scanMapperByPackage(directorPath);
        }

        // mapper列表配置扫描
        List<Element> mapperElementList = mapperNode.elements("mapper");
        if(!mapperElementList.isEmpty()){
            return parseMapperByMapperList(mapperElementList);
        }

        // 如果 scan-mapper和mapper配置全都不存在，抛出异常
        throw new RuntimeException("parseMapperNode error mapper node scan-mapper or mappers not exist");
    }

    /**
     * 通过扫描指定文件夹 解析mapper-xml文件
     * */
    private static Map<String, MappedStatement> scanMapperByPackage(String directorPath){
        Map<String, MappedStatement> mappedStatementMap = new HashMap<>();
        // 扫描mapper文件夹下所有mapper文件
        try {
            Enumeration<URL> urls = Configuration.class.getClassLoader().getResources(directorPath);

            while(urls.hasMoreElements()){
                URL url = urls.nextElement();
                List<MappedStatement> mappedStatementList = MapperXmlScanUtil.scanMapperXml(url);

                for(MappedStatement mappedStatement : mappedStatementList){
                    MappedStatement old = mappedStatementMap.put(mappedStatement.getSqlId(),mappedStatement);
                    if(old != null){
                        // 存在相同的sqlId相同的mappedStatement
                        throw new RuntimeException("has same sqlId =>" + old.getSqlId());
                    }
                }
            }
        } catch (IOException e) {
            logger.info("scan mapper-xml error",e);
            throw new RuntimeException(e);
        }

        return mappedStatementMap;
    }

    /**
     * 通过mapper配置列表 解析mapper-xml文件
     * */
    private static Map<String, MappedStatement> parseMapperByMapperList(List<Element> mapperList){
        Map<String, MappedStatement> mappedStatementMap = new HashMap<>();

        for(Element mapperElement : mapperList){
            String resourcePath = mapperElement.attributeValue("resource");
            URL url = Configuration.class.getClassLoader().getResource(resourcePath);
            List<MappedStatement> mappedStatementList = MapperXmlScanUtil.parseMapperXmlInFile(url);

            for(MappedStatement mappedStatement : mappedStatementList){
                MappedStatement old = mappedStatementMap.put(mappedStatement.getSqlId(),mappedStatement);
                if(old != null){
                    // 存在相同的sqlId相同的mappedStatement
                    throw new RuntimeException("has same sqlId =>" + old.getSqlId());
                }
            }
        }

        return mappedStatementMap;
    }
}
