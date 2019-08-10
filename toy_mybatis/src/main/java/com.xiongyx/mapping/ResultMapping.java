package com.xiongyx.mapping;

/**
 * @author xiongyx
 * on 2019/7/31.
 */
public class ResultMapping {

    private String column;
    private String property;
    private String jdbcType;
    private boolean isId;
    private int order;

    public ResultMapping(String column, String property, String jdbcType, boolean isId, ResultMappingEnum resultMappingEnum) {
        this.column = column;
        this.property = property;
        this.jdbcType = jdbcType;
        this.isId = isId;
        this.order = resultMappingEnum.getOrder();
    }

    public String getColumn() {
        return column;
    }

    public void setColumn(String column) {
        this.column = column;
    }

    public String getProperty() {
        return property;
    }

    public void setProperty(String property) {
        this.property = property;
    }

    public String getJdbcType() {
        return jdbcType;
    }

    public void setJdbcType(String jdbcType) {
        this.jdbcType = jdbcType;
    }

    public boolean isId() {
        return isId;
    }

    public void setId(boolean id) {
        isId = id;
    }

    @Override
    public String toString() {
        return "ResultMapping{" +
                "column='" + column + '\'' +
                ", property='" + property + '\'' +
                ", jdbcType='" + jdbcType + '\'' +
                ", isId=" + isId +
                '}';
    }
}
