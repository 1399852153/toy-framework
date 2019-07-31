package com.xiongyx.parsing;

import com.xiongyx.constant.Constant;
import com.xiongyx.mapping.ResultMap;
import com.xiongyx.mapping.ResultMapping;
import com.xiongyx.mapping.sqlsource.SqlSource;
import com.xiongyx.model.Configuration;
import com.xiongyx.model.MappedStatement;
import org.apache.log4j.Logger;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * @author xiongyx
 * @date 2019/6/24
 *
 * mapper-xml文件 解析器
 */
public class MapperXmlParseHelper {

    private static final Logger logger = Logger.getLogger(ConfigXmlParseHelper.class);

    public static List<MappedStatement> parseMapperXml(File mapperXmlFile){
        try {
            DocumentBuilder dBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
org.w3c.dom.Document doc = dBuilder.parse(mapperXmlFile);
            doc.getDocumentElement().normalize();
            XPath xPath =  XPathFactory.newInstance().newXPath();

            // 解析mapper标签
            String mapperPath = "/mapper";
            Node mapperNode = (Node) xPath.compile(mapperPath).evaluate(doc, XPathConstants.NODE);
            if(mapperNode.getNodeType() != Node.ELEMENT_NODE){
                // 无法解析当前mapper文件
                logger.info("can not parse any mapper element" + mapperXmlFile);
                return new ArrayList<>();
            }

            // 获取namespace
            String namespace = ((Element)mapperNode).getAttribute("namespace");
            NodeList nodeList = mapperNode.getChildNodes();

            List<MappedStatement> mappedStatementList = new ArrayList<>();
            // 当前xml文件中的sql单元列表
            for(int i=0; i<nodeList.getLength(); i++){
                Node node = nodeList.item(i);

                if(node.getNodeType() == Node.ELEMENT_NODE){
                    if("resultMap".equals(node.getNodeName())){
                        // resultMap
                        ResultMap resultMap = parseResultMap(namespace,(Element)node);

                    }else{
                        MappedStatement mappedStatement = parseMappedStatement(namespace,(Element)node);
                        // 加入 当前xml文件中的sql单元列表
                        mappedStatementList.add(mappedStatement);
                    }
                }
            }
            return mappedStatementList;
        } catch (Exception e) {
            logger.info("parseMapperXml error",e);

            throw new RuntimeException(e);
        }
    }

    /**
     * 解析sql单元 insert/update/delete/select
     * */
    private static MappedStatement parseMappedStatement(String namespace, Element sqlUnitNode){
        String eleName = sqlUnitNode.getNodeName();
        MappedStatement statement = new MappedStatement();

        if (Constant.SqlType.SELECT.value().equals(eleName)) {
            // select
            String resultType = sqlUnitNode.getAttribute("resultType");
            statement.setResultType(resultType);
            statement.setSqlCommandType(Constant.SqlType.SELECT);
        } else if (Constant.SqlType.UPDATE.value().equals(eleName)) {
            // update
            statement.setSqlCommandType(Constant.SqlType.UPDATE);
        } else if (Constant.SqlType.INSERT.value().equals(eleName)) {
            // insert
            statement.setSqlCommandType(Constant.SqlType.INSERT);
        } else if (Constant.SqlType.DELETE.value().equals(eleName)) {
            // delete
            statement.setSqlCommandType(Constant.SqlType.DELETE);
        } else{
            // default 默认类型
            statement.setSqlCommandType(Constant.SqlType.DEFAULT);
        }

        //设置SQL的唯一ID = namespace + "." + mapper.id
        String sqlId = namespace + "." + sqlUnitNode.getAttribute("id");

        statement.setSqlId(sqlId);
        statement.setNameSpace(namespace);

        // 构造SqlSource
        SqlSource sqlSource = MappedStatementParseHelper.parseSqlUnit(sqlUnitNode);
        statement.setSqlSource(sqlSource);

        return statement;
    }

    /**
     * 解析resultMap
     * */
    private static ResultMap parseResultMap(String namespace, Element resultMapNode) throws ClassNotFoundException {
        String id = namespace + "." + resultMapNode.getAttribute("id");
        String type = resultMapNode.getAttribute("type");

        List<ResultMapping> resultMappingList = new ArrayList<>();

        NodeList children = resultMapNode.getChildNodes();
        for(int i=0; i<children.getLength(); i++){
            Element element = (Element)children.item(i);
            if(element.getNodeName().equals("result")){
                String column = element.getAttribute("column");
                String property = element.getAttribute("property");

                resultMappingList.add(new ResultMapping(column,property));
            }
        }

        //设置resultMap的唯一ID = namespace + "." + resultMap.id
        ResultMap resultMap = new ResultMap();
        resultMap.setId(id);
        resultMap.setType(Class.forName(type));
        resultMap.setResultMappings(resultMappingList);


        return resultMap;
    }
}
