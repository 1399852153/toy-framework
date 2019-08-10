package com.xiongyx.parsing;

import com.xiongyx.mapping.ResultMap;
import com.xiongyx.model.Configuration;
import com.xiongyx.model.MappedStatement;
import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;
import java.io.File;

/**
 * @author xiongyx
 * @date 2019/6/24
 *
 * mapper-xml文件 解析器
 */
public class MapperXmlParseHelper {

    private static final Logger logger = Logger.getLogger(ConfigXmlParseHelper.class);

    public static void parseMapperXml(File mapperXmlFile){
        try {
            DocumentBuilder dBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            Document doc = dBuilder.parse(mapperXmlFile);
            doc.getDocumentElement().normalize();
            XPath xPath =  XPathFactory.newInstance().newXPath();

            // 解析mapper标签
            String mapperPath = "/mapper";
            Node mapperNode = (Node) xPath.compile(mapperPath).evaluate(doc, XPathConstants.NODE);
            if(mapperNode.getNodeType() != Node.ELEMENT_NODE){
                // 无法解析当前mapper文件
                logger.info("can not parse any mapper element" + mapperXmlFile);
            }

            // 获取namespace
            String namespace = ((Element)mapperNode).getAttribute("namespace");
            NodeList nodeList = mapperNode.getChildNodes();

            // 当前xml文件中的sql单元列表
            for(int i=0; i<nodeList.getLength(); i++){
                Node node = nodeList.item(i);

                if(node.getNodeType() == Node.ELEMENT_NODE){
                    if("resultMap".equals(node.getNodeName())){
                        // resultMap解析
                        ResultMap resultMap = ResultMapParseHelper.parseResultMap(namespace,(Element)node);
                        ResultMap old = Configuration.getInstance().addResultMap(resultMap.getId(),resultMap);
                        if(old != null){
                            // 存在相同的sqlId相同的resultMap
                            throw new RuntimeException("has same resultMap id =>" + old.getId());
                        }
                    }else{
                        MappedStatement mappedStatement = MappedStatementParseHelper.parseMappedStatement(namespace,(Element)node);
                        // 加入 当前xml文件中的sql单元列表
                        MappedStatement old = Configuration.getInstance().addMappedStatement(mappedStatement.getSqlId(),mappedStatement);
                        if(old != null){
                            // 存在相同的sqlId相同的mappedStatement
                            throw new RuntimeException("has same mappedStatement sqlId =>" + old.getSqlId());
                        }
                    }
                }
            }
        } catch (Exception e) {
            logger.info("parseMapperXml error",e);

            throw new RuntimeException(e);
        }
    }



}
