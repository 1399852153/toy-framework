package com.xiongyx.model;

import java.sql.SQLType;

/**
 * @author xiongyx
 * @date 2019/6/21
 */
public class MappedStatement {

    private String nameSpace;

    private String sqlId;

    private String paramType;

    private String resultType;

    private String sqlSource;

    private SQLType sqlCommandType;

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

    public SQLType getSqlCommandType() {
        return sqlCommandType;
    }

    public void setSqlCommandType(SQLType sqlCommandType) {
        this.sqlCommandType = sqlCommandType;
    }
}
