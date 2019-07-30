/**
 * 
 */
package com.xiongyx.executor.resultset;


import com.xiongyx.model.MappedStatement;
import com.xiongyx.util.JsonUtil;
import com.xiongyx.util.ReflectionUtil;
import com.xiongyx.util.TypeConvertUtil;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * DefaultResultSetHandler.java
 * 
 * @author PLF
 * @date 2019年3月6日
 */
public class DefaultResultSetHandler implements ResultSetHandler
{

    private final MappedStatement mappedStatement;

    /**
     * @param mappedStatement
     */
    public DefaultResultSetHandler(MappedStatement mappedStatement)
    {
        this.mappedStatement = mappedStatement;
    }

    /**
     * 处理查询结果，通过反射设置到返回的实体类
     */
    @SuppressWarnings("unchecked")
    @Override
    public <E> List<E> handleResultSets(ResultSet resultSet) {
        try {

            List<E> result = new ArrayList<>();

            if (null == resultSet) {
                return null;
            }



            // FIXME mappedStatement  中的eclass  不正确
            Class<?> eClass = Class.forName(mappedStatement.getResultType());

            ResultSetMetaData metaData = resultSet.getMetaData();
            while (resultSet.next()) {
                E entity = (E) eClass.newInstance();
                // 遍历metaData的列
                for (int i = 1; i <= metaData.getColumnCount(); i++) {
                    // 获得当前列的名称
                    String columnName = metaData.getColumnLabel(i);
                    // jdbcType
                    String jdbcType = metaData.getColumnTypeName(i);

                    // 获得setter方法
                    Method setterMethod = ReflectionUtil.getSetterMethod(eClass,columnName,true);
                    // pojo setter方法的javaType
                    Class setterParamType = setterMethod.getParameterTypes()[0];
                    // 从resultSet中获取对应的值
                    Object columnValue = TypeConvertUtil.getResultValueByType(resultSet,columnName,jdbcType,setterParamType.getName());

                    HashMap<String, Object> map = new HashMap<>();
                    map.put(columnName, columnValue);
                    System.out.println(JsonUtil.objectToJsonString(map));
                    for (Map.Entry<String, Object> entry : map.entrySet()) {
                        String fieldName = entry.getKey();
                        Object fieldValue = entry.getValue();
                        Field field = eClass.getDeclaredField(fieldName);
                        field.setAccessible(true);
                        field.set(entity, fieldValue);
                    }
                }
                result.add(entity);
            }

            return result;
        }
        catch (Exception e) {
            throw new RuntimeException("handleResultSets error",e);
        }
    }

}
