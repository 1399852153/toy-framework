package com.xiongyx.helper;

import com.xiongyx.constant.Constant;
import com.xiongyx.model.MappedStatement;
import com.xiongyx.util.XmlUtil;
import org.apache.commons.lang3.StringUtils;
import org.dom4j.Document;
import org.dom4j.Element;
import java.io.File;
import com.xiongyx.constant.Constant.SqlType;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @author xiongyx
 * @date 2019/6/24
 *
 * 解析mapper-xml文件
 */
public class MapperParseHelper {

    static{
        List<Statement> statementList = new ArrayList<>();

        Document document = XmlUtil.readXml(new File("D:\\github\\toy-framework\\demo\\src\\main\\resources\\mapper\\UserMapper.xml"));

        // 获取xml中的根元素
        Element rootElement = document.getRootElement();

        // 不是beans根元素的，文件不对
        if (!Constant.XML_ROOT_LABEL.equals(rootElement.getName())) {
            System.err.println("mapper xml文件根元素不是mapper");
        }

        String namespace = rootElement.attributeValue(Constant.XML_SELECT_NAMESPACE);

        List<MappedStatement> statements = new ArrayList<>();
        for (Iterator iterator = rootElement.elementIterator(); iterator.hasNext();) {
            Element element = (Element)iterator.next();
            String eleName = element.getName();

            MappedStatement statement = new MappedStatement();

            if (SqlType.SELECT.value().equals(eleName)) {
                String resultType = element.attributeValue(Constant.XML_SELECT_RESULTTYPE);
                statement.setResultType(resultType);
                statement.setSqlCommandType(SqlType.SELECT);
            }
            else if (SqlType.UPDATE.value().equals(eleName)) {
                statement.setSqlCommandType(SqlType.UPDATE);
            }
            else {
                // 其他标签自己实现
                System.err.println("不支持此xml标签解析:" + eleName);
                statement.setSqlCommandType(SqlType.DEFAULT);
            }

            //设置SQL的唯一ID
            String sqlId = namespace + "." + element.attributeValue(Constant.XML_ELEMENT_ID);

            statement.setSqlId(sqlId);
            statement.setNameSpace(namespace);
            statement.setSqlSource(StringUtils.trim(element.getStringValue()));
            statements.add(statement);

            System.out.println(statement);
//            configuration.addMappedStatement(sqlId, statement);
//
//            //这里其实是在MapperRegistry中生产一个mapper对应的代理工厂
//            configuration.addMapper(Class.forName(namespace));
        }
    }
}
