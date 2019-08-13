package com.xiongyx.mapping;

import java.util.List;

/**
 * @author xiongyx
 * on 2019/8/7.
 *
 * 嵌套resultMap
 */
public class ResultMappingNested extends ResultMapping{

    /**
     * 映射的类型
     * */
    private String type;

    /**
     * 内部复合的 字段映射集合
     * */
    private List<ResultMapping> compositeResultMappingList;

    /**
     * 类型
     * */
    private ResultMappingEnum resultMappingEnum;

    /**
     * 内存resultMap
     * */
    private ResultMap innerResultMap;

    public ResultMappingNested(ResultMap resultMap,String column, String property, String jdbcType,
                               boolean isId, ResultMappingEnum resultMappingEnum, List<ResultMapping> compositeResultMappingList,String type) {
        super(resultMap,column, property, jdbcType, isId,resultMappingEnum);
        this.resultMappingEnum = resultMappingEnum;
        this.compositeResultMappingList = compositeResultMappingList;
        this.type = type;

        // 初始化内部 innerResultMap
        String innerResultMapId = resultMap.getId() + "[" + resultMappingEnum.getName() + "]";

        try {
            ResultMap innerResultMap = new ResultMap(innerResultMapId,Class.forName(type));
            innerResultMap.setResultMappings(compositeResultMappingList);
            this.innerResultMap = innerResultMap;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            throw new RuntimeException("ResultMappingNested init error",e);
        }
    }

    public String getType() {
        return type;
    }

    public List<ResultMapping> getCompositeResultMappingList() {
        return compositeResultMappingList;
    }

    public ResultMappingEnum getResultMappingEnum() {
        return resultMappingEnum;
    }

    public ResultMap getInnerResultMap() {
        return innerResultMap;
    }
}
