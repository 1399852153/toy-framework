package com.xiongyx.scripting.sqlnode;

import com.xiongyx.scripting.DynamicContextOgnlEvaluator;
import com.xiongyx.scripting.DynamicSqlParseContext;

/**
 * @author xiongyx
 * @date 2019/7/17
 *
 * If节点
 */
public class IfSqlNode implements SqlNode{

    /**
     * test条件表达式
     * */
    private String testExpression;

    private SqlNode contents;

    public IfSqlNode(SqlNode contents,String testExpression) {
        this.contents = contents;
        this.testExpression = testExpression;
    }

    @Override
    public void apply(DynamicSqlParseContext context) {
        if(DynamicContextOgnlEvaluator.evaluateBoolean(testExpression,context)){
            contents.apply(context);
        }
    }
}
