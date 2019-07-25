package com.xiongyx.mapping;

import java.util.Map;

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

    /**
     * 额外的参数对象
     * */
    private Map<String,Object> additionParams;

    public BoundSql(Object paramObject, String sqlText, Map<String, Object> additionParams) {
        this.paramObject = paramObject;
        this.sqlText = sqlText;
        this.additionParams = additionParams;
    }

    public Object getParamObject() {
        return paramObject;
    }

    public String getSqlText() {
        return sqlText;
    }

    public Map<String, Object> getAdditionParams() {
        return additionParams;
    }

}
