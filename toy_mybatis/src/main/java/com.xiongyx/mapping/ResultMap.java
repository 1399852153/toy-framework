package com.xiongyx.mapping;

import java.util.ArrayList;
import java.util.List;

/**
 * @author xiongyx
 * on 2019/7/31.
 *
 * mapper文件中的 resultMap
 */
public class ResultMap {

    private String id;
    private Class<?> type;
    private List<ResultMapping> resultMappings;
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

    public List<ResultMapping> getResultMappings() {
        return resultMappings;
    }

    public void setResultMappings(List<ResultMapping> resultMappings) {
        this.resultMappings = resultMappings;

        // 对resultMappingList进行排序，使得普通的映射在排在前
        resultMappings.sort((o1, o2) -> {
            if(o1 instanceof ResultMappingAssociation || o1 instanceof ResultMappingCollection ){
                return -1;
            }else{
                return 0;
            }
        });

        for(ResultMapping resultMapping : resultMappings){
            if(resultMapping instanceof ResultMappingAssociation || resultMapping instanceof ResultMappingCollection){
                compositeResultMappings.add(resultMapping);
            }else{
                simpleResultMappings.add(resultMapping);
            }
        }
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
                ", resultMappings=" + resultMappings +
                ", simpleResultMappings=" + simpleResultMappings +
                ", compositeResultMappings=" + compositeResultMappings +
                '}';
    }
}
