package com.xiongyx.scripting.sqlnode;

/**
 * @author xiongyx
 * @date 2019/7/17
 *
 * Set节点
 */
public class SetSqlNode implements SqlNode{

    private MixedSqlNode contents;

    public SetSqlNode(MixedSqlNode contents) {
        this.contents = contents;
    }

    @Override
    public void apply(DynamicSqlParseContext context) {
        contents.apply(context);
    }
}
