package com.xiongyx.scripting;

import java.util.HashMap;
import java.util.Map;

/**
 * @author xiongyx
 * @date 2019/7/17
 *
 * 动态sql解析 上下文环境
 */
public class DynamicSqlParseContext {

    private final Map<String,Object> bindings = new HashMap<>();

    private final StringBuilder sqlBuilder = new StringBuilder();

    private Object paramObject;

    /**
     * 上下文环境中，绑定key参数时 用于区别不同参数(Foreach item)
     * */
    private int uniqueNumber = -1;

    public DynamicSqlParseContext(Object paramObject) {
        // 根据参数对象，生成K/V 上下文环境
        this.paramObject = paramObject;
    }

    /**
     * 获得绑定的K/V键值对
     * */
    public Map<String, Object> getBindings() {
        return bindings;
    }

    /**
     * 进行绑定
     * */
    public void bind(String name, Object value) {
        bindings.put(name, value);
    }

    public Object getParamObject() {
        return paramObject;
    }

    /**
     * 获得sqlBuilder
     * */
    public StringBuilder getSqlBuilder() {
        return sqlBuilder;
    }

    /**
     * 拓展sql
     * */
    public void appendSql(String sql) {
        sqlBuilder.append(sql);
        sqlBuilder.append(" ");
    }

    public int getUniqueNumber() {
        uniqueNumber++;
        return uniqueNumber;
    }
}
