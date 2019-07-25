package com.xiongyx.scripting.sqlnode;

import java.util.Map;

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
        Map<String,Object> bindings = context.getBindings();
        if(evalTestExpression(bindings)){
            contents.apply(context);
        }
    }

    private boolean evalTestExpression(Map<String,Object> bindings){
        return true;
    }
}
