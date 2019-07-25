package com.xiongyx.scripting.sqlnode;

import com.xiongyx.scripting.DynamicSqlParseContext;

import java.util.List;

/**
 * @author xiongyx
 * @date 2019/7/17
 *
 * 混合节点
 */
public class MixedSqlNode implements SqlNode{

    private List<SqlNode> contents;

    public MixedSqlNode(List<SqlNode> contents) {
        this.contents = contents;
    }

    @Override
    public void apply(DynamicSqlParseContext context) {
        for(SqlNode sqlNode : contents){
            sqlNode.apply(context);
        }
    }
}
