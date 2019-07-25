package com.xiongyx.parsing.handler;

import com.xiongyx.scripting.sqlnode.SqlNode;
import org.w3c.dom.Node;

import java.util.List;

/**
 * @author xiongyx
 * @date 2019/7/17
 */
public interface SqlNodeHandler {

    void handleNode(Node nodeToHandle, List<SqlNode> targetContents);
}
