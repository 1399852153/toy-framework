package com.xiongyx.mapping;

/**
 * @author xiongyx
 * @date 2019/7/17
 *
 * 上下文环境和sql脚本绑定之后的 sql复合对象
 */
public class BoundSql {

    /**
     * 参数对象
     * */
    private Object paramObject;

    /**
     * sql文本
     * */
    private String sqlText;

    public BoundSql(Object paramObject, String sqlText) {
        this.paramObject = paramObject;
        this.sqlText = sqlText;
    }

    public Object getParamObject() {
        return paramObject;
    }

    public void setParamObject(Object paramObject) {
        this.paramObject = paramObject;
    }

    public String getSqlText() {
        return sqlText;
    }

    public void setSqlText(String sqlText) {
        this.sqlText = sqlText;
    }
}
