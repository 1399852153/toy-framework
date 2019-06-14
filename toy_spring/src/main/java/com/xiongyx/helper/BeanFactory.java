package com.xiongyx.helper;

import com.xiongyx.util.ReflectionUtil;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * @author xiongyx
 * on 2019/6/11.
 */
public class BeanFactory {

    /**
     * 原始的beanMap
     * */
    private static Map<Class<?>,Object> BEAN_MAP = new HashMap<>();

    /**
     * 按类型 beanMap缓存
     * */
    private static Map<Class<?>,Set<Object>> TYPE_BEAN_MAP_CACHE = new HashMap<>();

    static{
        // 获得扫描得到的 beanClass集合
        Set<Class<?>> beanClassSet = ClassHelper.getComponentClassSet();
        // 遍历beanClass集合
        for(Class<?> beanClass : beanClassSet){
            // 创建bean对象
            Object beanObj = ReflectionUtil.newInstance(beanClass);
            // 将bean对象存入 bean工厂
            BEAN_MAP.put(beanClass,beanObj);
        }
    }

    /**
     * 获得bean工厂
     */
    public static Map<Class<?>,Object> getBeanMap(){
        return BEAN_MAP;
    }

    /**
     * 按照类型获得符合要求的bean
     * */
    public static Set<Object> getClassSetByType(Class<?> clazz){
        // 尝试从Type缓存中获取
        Set<Object> beanFromCache = TYPE_BEAN_MAP_CACHE.get(clazz);
        if(beanFromCache != null){
            // 缓存中已经存在对应类型的bean，直接返回
            return beanFromCache;
        }else{
            // 缓存中不存在对应类型的bean
            Set<Object> typeClasses = new HashSet<>();
            // 遍历整个BEAN_MAP，获取所有符合要求的bean
            for(Map.Entry<Class<?>,Object> entry : BEAN_MAP.entrySet()){
                Class<?> beanClass = entry.getKey();
                Object bean = entry.getValue();
                // bean是否是对应类型的类或者子类/接口实现
                if(clazz.isAssignableFrom(beanClass)){
                    typeClasses.add(bean);
                }
            }

            // 加入Type缓存
            TYPE_BEAN_MAP_CACHE.put(clazz,typeClasses);
            return typeClasses;
        }
    }
}
