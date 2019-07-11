package com.xiongyx.helper;

import com.xiongyx.datasource.DataSource;
import com.xiongyx.datasource.DruidDataSource;
import com.xiongyx.environment.Environment;
import com.xiongyx.model.Configuration;
import com.xiongyx.util.XmlUtil;
import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.Element;

import java.io.Reader;
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


    public static Configuration parseMapperXml(Reader reader){
        Document document = XmlUtil.readXml(reader);
        // 获取xml中的根元素
        Element rootElement = document.getRootElement();
        // 根元素是否匹配
        if (!ROOT_ELEMENT.equals(rootElement.getName())) {
            logger.info("config-xml文件根元素不是" + ROOT_ELEMENT);
        }

        // todo 解析environment 目前只有dataSource配置
        Element environmentNode = rootElement.element("environments");

        // 解析出Environment配置
        Environment environment = parseEnviromentNode(environmentNode);

        return null;
    }

    /**
     * 解析Environment节点
     * */
    private static Environment parseEnviromentNode(Element environmentNode){
        Element dataSourceNode = environmentNode.element("dataSource");

        List<Element> properties = dataSourceNode.elements("property");
        Map<String,String> dataSourceProperties = new HashMap<>();
        for(Element property : properties){
            String name = property.attributeValue("name");
            String value = property.attributeValue("value");
            dataSourceProperties.put(name,value);
        }

        // 构建数据源
        DataSource dataSource = new DruidDataSource(dataSourceProperties);

        // 返回Environment
        return new Environment(dataSource);
    }
}
