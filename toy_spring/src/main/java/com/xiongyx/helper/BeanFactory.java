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

    private static Map<Class<?>,Object> BEAN_MAP;

    static{
        BEAN_MAP = new HashMap<>();

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
        Set<Object> typeClasses = new HashSet<>();
        for(Map.Entry<Class<?>,Object> entry : BEAN_MAP.entrySet()){
            Class<?> beanClass = entry.getKey();
            Object bean = entry.getValue();

            if(clazz.isAssignableFrom(beanClass)){
                typeClasses.add(bean);
            }
        }
        return typeClasses;
    }
}
