package com.xiongyx.model;

import com.xiongyx.constant.Constant;
import com.xiongyx.mapping.sqlsource.SqlSource;

import java.util.StringJoiner;

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

    private SqlSource sqlSource;

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

    public SqlSource getSqlSource() {
        return sqlSource;
    }

    public void setSqlSource(SqlSource sqlSource) {
        this.sqlSource = sqlSource;
    }

    public Constant.SqlType getSqlCommandType() {
        return sqlCommandType;
    }

    public void setSqlCommandType(Constant.SqlType sqlCommandType) {
        this.sqlCommandType = sqlCommandType;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", MappedStatement.class.getSimpleName() + "[", "]").add("nameSpace='" + nameSpace + "'")
            .add("sqlId='" + sqlId + "'")
            .add("paramType='" + paramType + "'")
            .add("resultType='" + resultType + "'")
            .add("sqlSource='" + sqlSource + "'")
            .add("sqlCommandType=" + sqlCommandType)
            .toString();
    }
}
