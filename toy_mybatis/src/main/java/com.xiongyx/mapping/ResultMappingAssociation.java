package com.xiongyx.mapping;

import java.util.List;

/**
 * @author xiongyx
 * on 2019/8/7.
 *
 * 嵌套resultMap association
 */
public class ResultMappingAssociation extends ResultMapping{

    /**
     * 映射的类型
     * */
    private String type;

    /**
     * 内部复合的 字段映射集合
     * */
    private List<ResultMapping> compositeResultMappingList;

    public ResultMappingAssociation(String column, String property, String jdbcType, boolean isId) {
        super(column, property, jdbcType, isId,ResultMappingEnum.ASSOCIATION);
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<ResultMapping> getCompositeResultMappingList() {
        return compositeResultMappingList;
    }

    public void setCompositeResultMappingList(List<ResultMapping> compositeResultMappingList) {
        this.compositeResultMappingList = compositeResultMappingList;
    }
}
