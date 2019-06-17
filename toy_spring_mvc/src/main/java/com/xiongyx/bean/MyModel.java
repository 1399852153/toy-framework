package com.xiongyx.bean;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author xiongyx
 * on 2018/6/11.
 *
 * 纯json请求返回对象
 */
public class MyModel {

    private Map<String,Object> modelMap;

    public MyModel(){
        modelMap = new HashMap<>();
    }

    /**
     * 设置单个属性
     * */
    public void addObject(String key,Object value){
        modelMap.put(key,value);
    }

    /**
     * 设置多个属性
     * */
    public void addAllObjects(Map<String,Object> allObjects){
        modelMap.putAll(allObjects);
    }

    public Map<String, Object> getModelMap() {
        return modelMap;
    }
}
