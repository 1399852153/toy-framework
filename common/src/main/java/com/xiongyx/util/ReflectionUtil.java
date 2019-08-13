package com.xiongyx.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

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

    /**
     * 获取属性对应的value值
     * */
    public static Object getPropertyValue(String propertyName, Object target){
        String getterName = makeGetMethodName(propertyName);

        try {
            Method method = target.getClass().getMethod(getterName);
            return method.invoke(target);
        } catch (Exception e) {
            throw new RuntimeException("getPropertyValue error",e);
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

    /***
     * 将通过keyName获得对应的bean对象的set方法名称的字符串
     * @param keyName 属性名
     * @return  返回set方法名称的字符串
     */
    public static String makeSetMethodName(String keyName){
        //:::将第一个字母转为大写
        String newKeyName = StringUtil.transFirstCharUpperCase(keyName);

        return "set" + newKeyName;
    }

    /**
     * 获取指定类 指定属性名的method
     * @param clazz 指定类
     * @param propertyName 属性名
     * @param mustExist 是否必须存在 如果为true，当没有找到对应setter方法时，将抛出异常
     * */
    public static Method getSetterMethod(Class clazz, String propertyName,boolean mustExist){
        // 迭代当前类的所有public方法
        for (Method method : clazz.getDeclaredMethods()) {
            //set方法至少长度为4,非静态，返回值为空，参数只有一个

            // 非静态方法 && 返回值void && 参数为1
            if (!Modifier.isStatic(method.getModifiers())
                    && method.getReturnType().equals(Void.TYPE)
                    && method.getParameterTypes().length == 1) {

                String setMethodName = makeSetMethodName(propertyName);
                if(setMethodName.equals(method.getName())){
                    return method;
                }
            }
        }

        if(mustExist){
            throw new RuntimeException("no setter method clazz=" + clazz.getName() + " propertyName=" + propertyName);
        }else{
            return null;
        }
    }
}
