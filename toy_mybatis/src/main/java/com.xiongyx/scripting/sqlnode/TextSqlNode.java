package com.xiongyx.scripting.sqlnode;

/**
 * @author xiongyx
 * @date 2019/7/17
 *
 * 文本节点
 */
public class TextSqlNode implements SqlNode{

    private String text;

    public TextSqlNode(String text) {
        this.text = text;
    }

    @Override
    public void apply(DynamicSqlParseContext context) {
        // todo 解析动态参数
        context.appendSql(this.text);
    }
}
