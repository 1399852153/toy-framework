package com.xiongyx.parsing.handler;

import com.xiongyx.parsing.MappedStatementParseHelper;
import com.xiongyx.scripting.sqlnode.ForEachSqlNode;
import com.xiongyx.scripting.sqlnode.SqlNode;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import java.util.List;

/**
 * @author xiongyx
 * @date 2019/7/17
 */
public class ForEachSqlNodeHandler implements SqlNodeHandler{
    @Override
    public void handleNode(Node nodeToHandle, List<SqlNode> targetContents) {
        Element element = (Element)nodeToHandle;

        List<SqlNode> contents = MappedStatementParseHelper.parseDynamicSqlNode(element);
        MixedSqlNode mixedSqlNode = new MixedSqlNode(contents);

        String collection = element.getAttribute("collection");
        String item = element.getAttribute("item");
        String index = element.getAttribute("index");
        String open = element.getAttribute("open");
        String close = element.getAttribute("close");
        String separator = element.getAttribute("separator");

        // 构造 ForEachSqlNode
        ForEachSqlNode forEachSqlNode = new ForEachSqlNode.Builder()
                .collectionExpression(collection)
                .item(item)
                .index(index)
                .open(open)
                .close(close)
                .separator(separator)
                .content(mixedSqlNode)
                .build();

        targetContents.add(forEachSqlNode);
    }
}
