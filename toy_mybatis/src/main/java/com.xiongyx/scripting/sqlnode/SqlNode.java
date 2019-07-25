package com.xiongyx.scripting.sqlnode;

/**
 * @author xiongyx
 * @date 2019/7/17
 */
public interface SqlNode {

    /**
     * 访问者模式 解析SqlNode
     * */
    void apply(DynamicSqlParseContext context);
}
