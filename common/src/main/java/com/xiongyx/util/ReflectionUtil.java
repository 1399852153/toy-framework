package com.xiongyx.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * @Author xiongyx
 * on 2018/6/8.
 *
 * 反射工具类
 */
public class ReflectionUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(ReflectionUtil.class);

    /**
     * 创建实例
     * */
    public static Object newInstance(Class<?> clazz){
        Object instance;

        try {
            instance = clazz.newInstance();
            return instance;
        } catch (Exception e) {
            LOGGER.info("new instance failure",e);
           throw new RuntimeException(e);
        }
    }

    /**
     * 调用方法
     * */
    public static Object invokeMethod(Object obj,Method method,Object... args){
        Object result;

        try {
            // 设置方法为可访问
            method.setAccessible(true);
            // 执行方法
            result = method.invoke(obj,args);
            return result;
        } catch (Exception e) {
            LOGGER.info("invoke method failure",e);
            throw new RuntimeException(e);
        }
    }

    /**
     * 为成员变量赋值
     * */
    public static void setField(Object obj, Field field, Object value){
        try {
            // 设置属性为可访问
            field.setAccessible(true);
            // 为对象设置值
            field.set(obj,value);
        } catch (IllegalAccessException e) {
            LOGGER.info("set field failure",e);
            throw new RuntimeException(e);
        }
    }

    /***
     * 将通过keyName获得对应的bean对象的get方法名称的字符串
     * @param keyName 属性名
     * @return  返回get方法名称的字符串
     */
    public static String makeGetMethodName(String keyName){
        //:::将第一个字母转为大写
        String newKeyName = StringUtil.transFirstCharUpperCase(keyName);

        return "get" + newKeyName;
    }
}
