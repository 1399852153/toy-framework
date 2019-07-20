package com.xiongyx.parsing.handler;

import com.xiongyx.parsing.MappedStatementParseHelper;
import com.xiongyx.scripting.sqlnode.MixedSqlNode;
import com.xiongyx.scripting.sqlnode.SqlNode;
import com.xiongyx.scripting.sqlnode.WhereSqlNode;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import java.util.List;

/**
 * @author xiongyx
 * @date 2019/7/17
 */
public class WhereSqlNodeHandler implements SqlNodeHandler{
    @Override
    public void handleNode(Node nodeToHandle, List<SqlNode> targetContents) {
        List<SqlNode> contents = MappedStatementParseHelper.parseDynamicSqlNode((Element) nodeToHandle);
        MixedSqlNode mixedSqlNode = new MixedSqlNode(contents);
        WhereSqlNode whereSqlNode = new WhereSqlNode(mixedSqlNode);
        targetContents.add(whereSqlNode);
    }
}
