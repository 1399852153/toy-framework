package com.xiongyx.mapping;

/**
 * @author xiongyx
 * @date 2019/8/10
 */
public enum ResultMappingEnum {
    ID("id",1),
    RESULT("result",2),
    ASSOCIATION("association",3),
    COLLECTION("collection",4)
    ;

    private String name;
    private int order;

    ResultMappingEnum(String name, int order) {
        this.name = name;
        this.order = order;
    }

    public String getName() {
        return name;
    }

    public int getOrder() {
        return order;
    }
}
