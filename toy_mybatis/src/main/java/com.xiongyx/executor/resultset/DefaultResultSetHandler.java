/**
 * 
 */
package com.xiongyx.executor.resultset;

import com.xiongyx.mapping.ResultMap;
import com.xiongyx.mapping.ResultMapping;
import com.xiongyx.mapping.ResultMappingAssociation;
import com.xiongyx.mapping.ResultMappingCollection;
import com.xiongyx.model.Configuration;
import com.xiongyx.model.MappedStatement;
import com.xiongyx.util.ReflectionUtil;
import com.xiongyx.util.StringUtil;
import com.xiongyx.util.TypeConvertUtil;
import org.apache.log4j.Logger;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
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
public class DefaultResultSetHandler implements ResultSetHandler{

    private static final Logger logger = Logger.getLogger(DefaultResultSetHandler.class);

    private final MappedStatement mappedStatement;

    private Map<String,Object> storeObjects = new HashMap<>();

    public DefaultResultSetHandler(MappedStatement mappedStatement) {
        this.mappedStatement = mappedStatement;
    }

    /**
     * 处理查询结果，通过反射设置到返回的实体类
     */
    @SuppressWarnings("unchecked")
    @Override
    public <E> List<E> handleResultSets(ResultSet resultSet) {
        try {
            if (null == resultSet) {
                return null;
            }

            if(!StringUtil.isEmpty(this.mappedStatement.getResultType())){
                return handleResultType(resultSet);
            }else{
                return handlerResultMap(resultSet);
            }
        }
        catch (Exception e) {
            throw new RuntimeException("handleResultSets error",e);
        }
    }

    /**
     * 处理resultType类型
     * */
    private <E> List<E> handleResultType(ResultSet resultSet) throws Exception {
        List<E> result = new ArrayList<>();
        // 映射的对象类型
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

                // columnName和property相等
                setColumnValue(entity,eClass,resultSet,columnName,columnName,jdbcType);
            }
            result.add(entity);
            logger.info("=========================");
        }

        return result;
    }

    /**
     * 处理resultMap类型
     * */
    private <E> List<E> handlerResultMap(ResultSet resultSet) throws Exception{
        String resultMapKey = this.mappedStatement.getResultMap();
        // 尝试用全名获取
        ResultMap resultMap = Configuration.getInstance().getResultMap(resultMapKey,false);
        if(resultMap == null){
            // 当前mapper的namespace + resultMapKey再获取一次
            String fullyKey = this.mappedStatement.getNameSpace() + "." + resultMapKey;
            resultMap = Configuration.getInstance().getResultMap(fullyKey,true);
        }

        List<E> result = new ArrayList<>();
        // 映射的对象类型
        Class<?> eClass = resultMap.getType();

        List<ResultMapping> resultMappingList = resultMap.getResultMappings();

        while (resultSet.next()) {
            E entity = (E) eClass.newInstance();

            for(ResultMapping resultMapping : resultMappingList){
                if(resultMapping instanceof ResultMappingAssociation || resultMapping instanceof ResultMappingCollection){
                    // association/collection
                    // 由于前面已经排序完成，走到这里说明最外层主属性已经映射完毕

                    // todo 根据简单映射和已经完成字段映射的对象生成唯一的key
                    // todo 存入storeObjects
                    List<ResultMapping> rowKeyResultMappings = getResultMappingListByRowKey(resultMap);

                }else{
                    // 简单映射
                    handleSimpleResultMapping(entity,eClass,resultSet,resultMapping);
                }
            }
            result.add(entity);
            logger.info("=========================");
        }

        return result;
    }

    /**
     * 获取resultMapping集合 用于构建 orm映射对象的唯一key
     * */
    private List<ResultMapping> getResultMappingListByRowKey(ResultMap resultMap){
        if(resultMap.getIdResultMapping().isEmpty()){
            // 如果不存在id集合映射，将所有的简单集合综合起来作为唯一标识
            return resultMap.getSimpleResultMappings();
        }else{
            // 将id集合映射 综合起来作为唯一标识
            return resultMap.getIdResultMapping();
        }
    }

    private <E> void handleSimpleResultMapping(E entity,Class<?> eClass,ResultSet resultSet,ResultMapping resultMapping) throws Exception {
        // 获得当前列的名称
        String columnName = resultMapping.getColumn();
        String jdbcType = resultMapping.getJdbcType();
        String property = resultMapping.getProperty();

        setColumnValue(entity,eClass,resultSet,property,columnName,jdbcType);
    }

    private <E> void setColumnValue(E entity,Class<?> eClass,ResultSet resultSet,String property,String columnName,String jdbcType) throws Exception {
        // 获得setter方法 todo 效率不高，可以使用objectWrapper将setterMethod封装起来
        Method setterMethod = ReflectionUtil.getSetterMethod(eClass,property,true);
        // pojo setter方法的javaType
        Class setterParamType = setterMethod.getParameterTypes()[0];

        // 从resultSet中获取对应的值
        Object columnValue = TypeConvertUtil.getResultValueByType(resultSet,columnName,jdbcType,setterParamType.getName());
        logger.info("{" + property + ":" + columnValue + "}");

        Field field = eClass.getDeclaredField(property);
        field.setAccessible(true);
        field.set(entity, columnValue);
    }
}
