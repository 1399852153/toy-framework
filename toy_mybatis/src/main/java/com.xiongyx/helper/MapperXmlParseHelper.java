package com.xiongyx.helper;

import com.xiongyx.constant.Constant;
import com.xiongyx.model.MappedStatement;
import com.xiongyx.util.XmlUtil;
import org.apache.commons.lang3.StringUtils;
import org.dom4j.Document;
import org.dom4j.Element;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @author xiongyx
 * @date 2019/6/24
 *
 * mapper-xml文件 解析器
 */
public class MapperXmlParseHelper {

    public static List<MappedStatement> parseMapperXml(File xmlUrl){
        Document document = XmlUtil.readXmlByFile(xmlUrl);

        // 获取xml中的根元素
        Element rootElement = document.getRootElement();

        // 不是beans根元素的，文件不对
        if (!Constant.XML_ROOT_LABEL.equals(rootElement.getName())) {
            System.err.println("mapper xml文件根元素不是mapper");
        }

        String namespace = rootElement.attributeValue(Constant.XML_SELECT_NAMESPACE);
        Iterator iterator = rootElement.elementIterator();

        // 当前xml文件中的sql单元列表
        List<MappedStatement> mappedStatementList = new ArrayList<>();
        while(iterator.hasNext()) {
            Element element = (Element)iterator.next();
            MappedStatement mappedStatement = MapperXmlParseHelper.parseMappedStatement(namespace,element);

            // 加入 当前xml文件中的sql单元列表
            mappedStatementList.add(mappedStatement);
        }

        return mappedStatementList;
    }

    private static MappedStatement parseMappedStatement(String namespace, Element statementElement){
        String eleName = statementElement.getName();

        MappedStatement statement = new MappedStatement();

        if (Constant.SqlType.SELECT.value().equals(eleName)) {
            String resultType = statementElement.attributeValue(Constant.XML_SELECT_RESULTTYPE);
            statement.setResultType(resultType);
            statement.setSqlCommandType(Constant.SqlType.SELECT);
        } else if (Constant.SqlType.UPDATE.value().equals(eleName)) {
            statement.setSqlCommandType(Constant.SqlType.UPDATE);
        } else {
            // 其他标签自己实现
            statement.setSqlCommandType(Constant.SqlType.DEFAULT);
        }

        //设置SQL的唯一ID
        String sqlId = namespace + "." + statementElement.attributeValue(Constant.XML_ELEMENT_ID);

        statement.setSqlId(sqlId);
        statement.setNameSpace(namespace);
        statement.setSqlSource(StringUtils.trim(statementElement.getStringValue()));

        return statement;
    }
}
