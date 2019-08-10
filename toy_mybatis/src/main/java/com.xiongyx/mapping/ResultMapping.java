package com.xiongyx.mapping;

import java.util.StringJoiner;

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

    public String getProperty() {
        return property;
    }

    public String getJdbcType() {
        return jdbcType;
    }

    public boolean isId() {
        return isId;
    }

    public int getOrder() {
        return order;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", ResultMapping.class.getSimpleName() + "[", "]").add("column='" + column + "'")
            .add("property='" + property + "'")
            .add("jdbcType='" + jdbcType + "'")
            .add("isId=" + isId)
            .add("order=" + order)
            .toString();
    }
}
