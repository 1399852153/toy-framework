package com.xiongyx.scripting.sqlnode;

/**
 * @author xiongyx
 * @date 2019/7/17
 *
 * Set节点
 */
public class SetSqlNode implements SqlNode{

    private MixedSqlNode mixedSqlNode;

    public SetSqlNode(MixedSqlNode mixedSqlNode) {
        this.mixedSqlNode = mixedSqlNode;
    }

    @Override
    public void apply(DynamicSqlParseContext context) {

    }
}
