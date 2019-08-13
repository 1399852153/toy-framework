package com.xiongyx.mapping;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.StringJoiner;

/**
 * @author xiongyx
 * on 2019/7/31.
 *
 * mapper文件中的 resultMap
 */
public class ResultMap {

    private String id;
    private Class<?> type;
    private boolean isNested;
    private List<ResultMapping> resultMappings;
    private List<ResultMapping> idResultMapping = new ArrayList<>();
    private List<ResultMapping> simpleResultMappings = new ArrayList<>();
    private List<ResultMapping> compositeResultMappings = new ArrayList<>();

    public ResultMap(String id, Class<?> type) {
        this.id = id;
        this.type = type;
    }

    public String getId() {
        return id;
    }

    public Class<?> getType() {
        return type;
    }

    public boolean isNested() {
        return isNested;
    }

    public List<ResultMapping> getResultMappings() {
        return resultMappings;
    }

    public void setResultMappings(List<ResultMapping> resultMappings) {
        this.resultMappings = resultMappings;

        // 对resultMappingList进行排序，使得普通的映射在排在前
        resultMappings.sort(Comparator.comparingInt(ResultMapping::getOrder));

        for(ResultMapping resultMapping : resultMappings){
            if(resultMapping instanceof ResultMappingNested){
                if(!isNested){
                    // 当存在复合结果映射时，isNested = true
                    isNested = true;
                }
                // 复合结果映射
                compositeResultMappings.add(resultMapping);
            }else{
                if(resultMapping.isId()){
                    // id结果映射
                    idResultMapping.add(resultMapping);
                }

                // 简单结果映射
                simpleResultMappings.add(resultMapping);
            }
        }
    }

    public List<ResultMapping> getIdResultMapping() {
        return idResultMapping;
    }

    public List<ResultMapping> getSimpleResultMappings() {
        return simpleResultMappings;
    }

    public List<ResultMapping> getCompositeResultMappings() {
        return compositeResultMappings;
    }

    @Override
    public String toString() {
        return "ResultMap{" +
                "id='" + id + '\'' +
                ", type=" + type +
                ", isNested=" + isNested +
                ", resultMappings=" + resultMappings +
                ", idResultMapping=" + idResultMapping +
                ", simpleResultMappings=" + simpleResultMappings +
                ", compositeResultMappings=" + compositeResultMappings +
                '}';
    }
}
