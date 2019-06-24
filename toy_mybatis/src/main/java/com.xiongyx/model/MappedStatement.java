package com.xiongyx.model;

import com.xiongyx.constant.Constant;


/**
 * @author xiongyx
 * @date 2019/6/21
 *
 * sql单元
 */
public class MappedStatement {

    private String nameSpace;

    private String sqlId;

    private String paramType;

    private String resultType;

    private String sqlSource;

    private Constant.SqlType sqlCommandType;

    public String getNameSpace() {
        return nameSpace;
    }

    public void setNameSpace(String nameSpace) {
        this.nameSpace = nameSpace;
    }

    public String getSqlId() {
        return sqlId;
    }

    public void setSqlId(String sqlId) {
        this.sqlId = sqlId;
    }

    public String getParamType() {
        return paramType;
    }

    public void setParamType(String paramType) {
        this.paramType = paramType;
    }

    public String getResultType() {
        return resultType;
    }

    public void setResultType(String resultType) {
        this.resultType = resultType;
    }

    public String getSqlSource() {
        return sqlSource;
    }

    public void setSqlSource(String sqlSource) {
        this.sqlSource = sqlSource;
    }

    public Constant.SqlType getSqlCommandType() {
        return sqlCommandType;
    }

    public void setSqlCommandType(Constant.SqlType sqlCommandType) {
        this.sqlCommandType = sqlCommandType;
    }
}
