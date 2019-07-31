package com.xiongyx.model;

import com.xiongyx.constant.Constant;
import com.xiongyx.mapping.ResultMapping;
import com.xiongyx.mapping.sqlsource.SqlSource;

import java.util.List;
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

    private List<ResultMapping> resultMappingList;

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

    public List<ResultMapping> getResultMappingList() {
        return resultMappingList;
    }

    public void setResultMappingList(List<ResultMapping> resultMappingList) {
        this.resultMappingList = resultMappingList;
    }

    public Constant.SqlType getSqlCommandType() {
        return sqlCommandType;
    }

    public void setSqlCommandType(Constant.SqlType sqlCommandType) {
        this.sqlCommandType = sqlCommandType;
    }

    @Override
    public String toString() {
        return "MappedStatement{" +
                "nameSpace='" + nameSpace + '\'' +
                ", sqlId='" + sqlId + '\'' +
                ", paramType='" + paramType + '\'' +
                ", resultType='" + resultType + '\'' +
                ", sqlSource=" + sqlSource +
                ", resultMappingList=" + resultMappingList +
                ", sqlCommandType=" + sqlCommandType +
                '}';
    }
}
