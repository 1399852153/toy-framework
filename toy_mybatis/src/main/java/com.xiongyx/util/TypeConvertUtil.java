package com.xiongyx.util;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author xiongyx
 * on 2019/7/30.
 */
public class TypeConvertUtil {

    public static Object getResultValueByType(ResultSet resultSet, String columnName, String jdbcType, String javaType){
        try{
            switch (javaType){
                case "java.lang.String":
                    // string
                    return resultSet.getString(columnName);
                case "int":
                case "java.lang.Integer":
                    // integer
                    return resultSet.getInt(columnName);
                case "double":
                case "java.lang.Double":
                    // double
                    return resultSet.getDouble(columnName);
                case "boolean":
                case "java.lang.Boolean":
                    return resultSet.getBoolean(columnName);
                default:
                    return resultSet.getObject(columnName);
            }
        }catch (SQLException e) {
            throw new RuntimeException("getResultValueByType error",e);
        }
    }
}
