package com.xiongyx.helper;

import com.xiongyx.util.ReflectionUtil;

import java.util.Map;
import java.util.Set;

/**
 * @author xiongyx
 * on 2019/6/11.
 */
public class BeanFactory {

    private static Map<Class<?>,Object> BEAN_MAP;

    static{
        //:::获得扫描得到的 beanClass集合
        Set<Class<?>> beanClassSet = ClassHelper.getComponentClassSet();
        //:::遍历beanClass集合
        for(Class<?> beanClass : beanClassSet){
            //:::创建bean对象
            Object beanObj = ReflectionUtil.newInstance(beanClass);
            //:::将bean对象存入 bean工厂
            BEAN_MAP.put(beanClass,beanObj);
        }
    }
}
