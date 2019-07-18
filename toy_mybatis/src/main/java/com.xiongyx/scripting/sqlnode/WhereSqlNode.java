package com.xiongyx.scripting.sqlnode;

/**
 * @author xiongyx
 * @date 2019/7/17
 *
 * Where节点
 */
public class WhereSqlNode implements SqlNode{

    private MixedSqlNode contents;

    public WhereSqlNode(MixedSqlNode contents) {
        this.contents = contents;
    }

    @Override
    public void apply(DynamicSqlParseContext context) {

    }
}
