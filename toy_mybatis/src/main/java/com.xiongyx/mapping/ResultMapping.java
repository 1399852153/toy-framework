package com.xiongyx.mapping;

/**
 * @author xiongyx
 * on 2019/7/31.
 */
public class ResultMapping {

    private String column;
    private String property;

    public ResultMapping(String column, String property) {
        this.column = column;
        this.property = property;
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

    @Override
    public String toString() {
        return "ResultMapping{" +
                "column='" + column + '\'' +
                ", property='" + property + '\'' +
                '}';
    }
}
