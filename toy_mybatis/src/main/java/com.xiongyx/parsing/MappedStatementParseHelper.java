package com.xiongyx.parsing;

import com.xiongyx.mapping.sqlsource.DynamicSqlSource;
import com.xiongyx.mapping.sqlsource.SqlSource;
import com.xiongyx.parsing.handler.SqlNodeHandler;
import com.xiongyx.parsing.handler.SqlNodeHandlerFactory;
import com.xiongyx.scripting.sqlnode.MixedSqlNode;
import com.xiongyx.scripting.sqlnode.SqlNode;
import com.xiongyx.scripting.sqlnode.TextSqlNode;
import org.w3c.dom.CharacterData;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.util.ArrayList;
import java.util.List;

/**
 * @author xiongyx
 * on 2019/7/14.
 *
 * MappedStatement解析器
 */
public class MappedStatementParseHelper {

    /**
     * 解析sql单元，构造SqlSource
     * */
    public static SqlSource parseSqlUnit(Element sqlUnitNode){
        // 解析对应的 sqlUnitNode，获得SqlNode有序列表
        List<SqlNode> contents = parseDynamicSqlNode(sqlUnitNode);

        // rootNode是一个MixedNode，持有一个 contentsList
        MixedSqlNode rootNode = new MixedSqlNode(contents);

        return new DynamicSqlSource(rootNode);
    }

    public static List<SqlNode> parseDynamicSqlNode(Element sqlUnitNode){
        // 当前Element节点解析之后对应的SqlNode有序列表
        List<SqlNode> contents = new ArrayList<>();

        // 获取sqlUnitNode的所有一级孩子节点,判断孩子节点类型
        NodeList children = sqlUnitNode.getChildNodes();
        for(int i=0; i<children.getLength(); i++){
            Node nodeItem = children.item(i);

            // 判断孩子节点类型
            if(nodeItem.getNodeType() == Node.CDATA_SECTION_NODE || nodeItem.getNodeType() == Node.TEXT_NODE) {
                // 文本节点
                String data = ((CharacterData) nodeItem).getData();
                TextSqlNode textSqlNode = new TextSqlNode(data);
                contents.add(textSqlNode);
            }else{
                // 判断是否是注释节点
                if(nodeItem.getNodeType() != Node.COMMENT_NODE){
                    // 复合节点
                    String nodeName = nodeItem.getNodeName();
                    // 根据节点名称从工厂中获取对应的SqlNodeHandler
                    SqlNodeHandler sqlNodeHandler = SqlNodeHandlerFactory.getSqlNodeHandlerByType(nodeName);
                    // SqlNodeHandler对特定类型的节点进行相应的处理
                    sqlNodeHandler.handleNode(nodeItem,contents);
                }
            }
        }

        return contents;
    }
}
