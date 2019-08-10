package com.xiongyx.parsing;

import com.xiongyx.mapping.*;
import org.omg.CORBA.TRANSACTION_MODE;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.util.ArrayList;
import java.util.List;

/**
 * @author xiongyx
 * @date 2019/8/10
 */
public class ResultMapParseHelpler {

    /**
     * 解析resultMap
     * */
    public static ResultMap parseResultMap(String namespace, Element resultMapNode) throws ClassNotFoundException {
        String id = namespace + "." + resultMapNode.getAttribute("id");
        String type = resultMapNode.getAttribute("type");

        List<ResultMapping> resultMappingList = parseResultMappingList(resultMapNode);

        //设置resultMap的唯一ID = namespace + "." + resultMap.id
        ResultMap resultMap = new ResultMap(id,Class.forName(type));
        resultMap.setResultMappings(resultMappingList);

        return resultMap;
    }


    private static List<ResultMapping> parseResultMappingList(Element parent){
        List<ResultMapping> resultMappingList = new ArrayList<>();

        NodeList children = parent.getChildNodes();
        for(int i=0; i<children.getLength(); i++){
            Node child = children.item(i);
            if(child instanceof Element){
                Element element = (Element)child;
                String nodeName = element.getNodeName();
                ResultMapping compositeResultMapping = parseResultMapping(element,nodeName);
                resultMappingList.add(compositeResultMapping);
            }
        }

        return resultMappingList;
    }

    private static ResultMapping parseResultMapping(Element element, String nodeName){
        if(ResultMappingEnum.ID.getName().equals(nodeName)) {
            // 普通映射
            String column = element.getAttribute("column");
            String property = element.getAttribute("property");
            String jdbcType = element.getAttribute("property");

            return new ResultMapping(column,property,jdbcType, true,ResultMappingEnum.ID);
        } else if(ResultMappingEnum.RESULT.getName().equals(nodeName)){
            // 普通映射
            String column = element.getAttribute("column");
            String property = element.getAttribute("property");
            String jdbcType = element.getAttribute("property");

            return new ResultMapping(column,property,jdbcType,false,ResultMappingEnum.RESULT);
        } else if(ResultMappingEnum.ASSOCIATION.getName().equals(nodeName)){
            // 普通映射字段
            String column = element.getAttribute("column");
            String property = element.getAttribute("property");
            String jdbcType = element.getAttribute("property");

            ResultMappingAssociation association = new ResultMappingAssociation(column,property,jdbcType,false);

            String javaType = element.getAttribute("javaType");

            List<ResultMapping> resultMappingList = parseResultMappingList(element);
            association.setType(javaType);
            association.setCompositeResultMappingList(resultMappingList);

            return association;
        }else if(ResultMappingEnum.COLLECTION.getName().equals(nodeName)){
            // 普通映射字段
            String column = element.getAttribute("column");
            String property = element.getAttribute("property");
            String jdbcType = element.getAttribute("property");

            ResultMappingCollection collection = new ResultMappingCollection(column,property,jdbcType,false);

            String javaType = element.getAttribute("javaType");

            List<ResultMapping> resultMappingList = parseResultMappingList(element);
            collection.setType(javaType);
            collection.setCompositeResultMappingList(resultMappingList);

            return collection;
        }

        throw new RuntimeException("unknown nodeName=" + nodeName);
    }
}
