package com.xiongyx.helper;

import com.xiongyx.constant.Constant;
import com.xiongyx.model.MappedStatement;
import org.apache.commons.lang3.StringUtils;
import org.dom4j.Element;

/**
 * @author xiongyx
 * @date 2019/6/24
 *
 * 解析mapper-xml文件
 */
public class MapperParseHelper {

    public static MappedStatement parseMappedStatement(String namespace, Element statementElement){
        String eleName = statementElement.getName();

        MappedStatement statement = new MappedStatement();

        if (Constant.SqlType.SELECT.value().equals(eleName)) {
            String resultType = statementElement.attributeValue(Constant.XML_SELECT_RESULTTYPE);
            statement.setResultType(resultType);
            statement.setSqlCommandType(Constant.SqlType.SELECT);
        }
        else if (Constant.SqlType.UPDATE.value().equals(eleName)) {
            statement.setSqlCommandType(Constant.SqlType.UPDATE);
        }
        else {
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
