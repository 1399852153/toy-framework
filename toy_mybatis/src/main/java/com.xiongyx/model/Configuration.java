package com.xiongyx.model;

import com.xiongyx.constant.Constant;
import com.xiongyx.helper.MapperParseHelper;
import com.xiongyx.util.XmlUtil;
import org.dom4j.Document;
import org.dom4j.Element;

import java.io.File;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * @author xiongyx
 * @date 2019/6/24
 *
 * mybatis配置
 */
public class Configuration {

    private static Map<String,MappedStatement> mappedStatementMap = new HashMap<>();

    static{
        Document document = XmlUtil.readXml(new File("D:\\github\\toy-framework\\demo\\src\\main\\resources\\mapper\\UserMapper.xml"));

        // 获取xml中的根元素
        Element rootElement = document.getRootElement();

        // 不是beans根元素的，文件不对
        if (!Constant.XML_ROOT_LABEL.equals(rootElement.getName())) {
            System.err.println("mapper xml文件根元素不是mapper");
        }

        String namespace = rootElement.attributeValue(Constant.XML_SELECT_NAMESPACE);
        Iterator iterator = rootElement.elementIterator();
        while(iterator.hasNext()) {
            Element element = (Element)iterator.next();
            MappedStatement mappedStatement = MapperParseHelper.parseMappedStatement(namespace,element);

            // 存入map映射
            MappedStatement sameKey = mappedStatementMap.put(mappedStatement.getSqlId(),mappedStatement);
            if(sameKey != null){
                // 发现存在相同sqlID的 mappedStatement
                throw new RuntimeException("has same sqlID sqlId=" + mappedStatement.getSqlId());
            }
        }
    }
}
