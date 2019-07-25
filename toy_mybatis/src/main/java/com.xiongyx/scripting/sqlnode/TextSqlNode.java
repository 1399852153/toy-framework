package com.xiongyx.scripting.sqlnode;

import com.xiongyx.scripting.DynamicSqlParseContext;

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
        // 文本节点，直接拼接
        context.appendSql(this.text);
    }
}
