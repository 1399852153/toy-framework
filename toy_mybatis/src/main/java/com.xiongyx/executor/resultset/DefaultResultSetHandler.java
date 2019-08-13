package com.xiongyx.executor.resultset;

import com.xiongyx.mapping.ResultMap;
import com.xiongyx.mapping.ResultMapping;
import com.xiongyx.mapping.ResultMappingEnum;
import com.xiongyx.mapping.ResultMappingNested;
import com.xiongyx.model.Configuration;
import com.xiongyx.model.MappedStatement;
import com.xiongyx.util.LinkedObjectUtil;
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


/**
 * DefaultResultSetHandler.java
 * 
 * @author xiongyx
 * @date 2019/07/10
 */
public class DefaultResultSetHandler <E> implements ResultSetHandler {

    private static final Logger logger = Logger.getLogger(DefaultResultSetHandler.class);

    private final MappedStatement mappedStatement;

    private HashMap<String,E> nestedResultObjects = new HashMap<>();

    public DefaultResultSetHandler(MappedStatement mappedStatement) {
        this.mappedStatement = mappedStatement;
    }

    /**
     * 处理查询结果，通过反射设置到返回的实体类
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<E> handleResultSets(ResultSet resultSet) {
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
    private List<E> handleResultType(ResultSet resultSet) throws Exception {
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
    private List<E> handlerResultMap(ResultSet resultSet) throws Exception{
        String resultMapKey = this.mappedStatement.getResultMap();
        // 尝试用全名获取
        ResultMap resultMap = Configuration.getInstance().getResultMap(resultMapKey,false);
        if(resultMap == null){
            // 当前mapper的namespace + resultMapKey再获取一次
            String fullyKey = this.mappedStatement.getNameSpace() + "." + resultMapKey;
            resultMap = Configuration.getInstance().getResultMap(fullyKey,true);
        }

        if(resultMap.isNested()){
            // 处理嵌套resultMap
            return handleNestedResultMap(resultMap,resultSet);
        }else{
            // 处理简单resultMap
            return handleSimpleResultMap(resultMap,resultSet);
        }
    }

    /**
     * 处理简单resultMap
     * */
    @SuppressWarnings("unchecked")
    private List<E> handleSimpleResultMap(ResultMap resultMap, ResultSet resultSet) throws Exception {
        List<E> resultList = new ArrayList<>();
        // 映射的对象类型
        Class<?> eClass = resultMap.getType();

        List<ResultMapping> resultMappingList = resultMap.getResultMappings();

        while (resultSet.next()) {
            E entity = (E) eClass.newInstance();

            for(ResultMapping resultMapping : resultMappingList){
                // 简单映射
                handleSimpleResultMapping(entity,eClass,resultSet,resultMapping);
            }
            resultList.add(entity);
            logger.info("=========================");
        }

        return resultList;
    }

    /**
     * 处理嵌套resultMap
     * */
    @SuppressWarnings("unchecked")
    private List<E> handleNestedResultMap(ResultMap resultMap, ResultSet resultSet) throws Exception{
        // 映射的对象类型
        Class<?> eClass = resultMap.getType();

        List<ResultMapping> resultMappingList = resultMap.getResultMappings();

        while (resultSet.next()) {
            E entity = (E)ReflectionUtil.newInstance(eClass);

            for(ResultMapping resultMapping : resultMappingList){
                if(resultMapping instanceof ResultMappingNested){
                    // 嵌套映射
                    getRowValue(resultMap,resultSet,entity);
                }else{
                    // 简单映射
                    handleSimpleResultMapping(entity,eClass,resultSet,resultMapping);
                }
            }
            logger.info("=========================");
        }

        // 嵌套查询 返回storeObjects的数据
        return new ArrayList<>(nestedResultObjects.values());
    }

    @SuppressWarnings("unchecked")
    private Object getRowValue(ResultMap resultMap, ResultSet resultSet, E entity) throws Exception {
        // 映射的对象类型
        Class<?> eClass = resultMap.getType();
        List<ResultMapping> resultMappingList = resultMap.getResultMappings();

        for(ResultMapping resultMapping : resultMappingList){
            if(resultMapping instanceof ResultMappingNested){
                ResultMappingNested resultMappingNested = (ResultMappingNested)resultMapping;
                // association/collection

                // 根据简单映射和已经完成字段映射的对象生成唯一的key
                List<ResultMapping> rowKeyResultMappings = getResultMappingListByRowKey(resultMap);
                String rowKey = getRowKey(resultMap,rowKeyResultMappings,entity);
                // 由于已经排序完成，走到这里说明最外层主属性已经映射完毕

                if(!nestedResultObjects.containsKey(rowKey)){
                    nestedResultObjects.put(rowKey,entity);
                }

                // 从resultMapping中获取嵌套resultMap信息
                ResultMap innerResultMap = resultMappingNested.getInnerResultMap();
                // 从resultMapping中获取嵌套resultMap子对象类型
                Class clazzType = Class.forName(resultMappingNested.getType());

                Object subObject = getRowValue(innerResultMap,resultSet,(E)ReflectionUtil.newInstance(clazzType));
                // 将subObject和parentObject关联起来
                if(resultMappingNested.getResultMappingEnum().equals(ResultMappingEnum.ASSOCIATION)){
                    LinkedObjectUtil.setAssociationProperty(entity,resultMapping.getProperty(),subObject);
                }else{
                    LinkedObjectUtil.setCollectionProperty(entity,resultMapping.getProperty(),"java.util.ArrayList",subObject);
                }
            }else{
                // 简单映射
                handleSimpleResultMapping(entity,eClass,resultSet,resultMapping);
            }
        }
        logger.info("=========================");

        return entity;
    }

    /**
     * 嵌套对象和外部对象进行关联
     * */
    private void linkedObject(ResultMapping nestedResultMapping,E entity){

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

    private String getRowKey(ResultMap resultMap,List<ResultMapping> rowKeyResultMappings,E entity){
        StringBuilder rowKey = new StringBuilder(resultMap.getId()).append(":");
        for(ResultMapping rowKeyItem : rowKeyResultMappings){
            String property = rowKeyItem.getProperty();
            Object propertyValue = ReflectionUtil.getPropertyValue(property,entity);
            rowKey.append(propertyValue).append("_");
        }

        return rowKey.toString();
    }

    private void handleSimpleResultMapping(E entity,Class<?> eClass,ResultSet resultSet,ResultMapping resultMapping) throws Exception {
        // 获得当前列的名称
        String columnName = resultMapping.getColumn();
        String jdbcType = resultMapping.getJdbcType();
        String property = resultMapping.getProperty();

        setColumnValue(entity,eClass,resultSet,property,columnName,jdbcType);
    }

    private void setColumnValue(E entity,Class<?> eClass,ResultSet resultSet,String property,String columnName,String jdbcType) throws Exception {
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
