package com.xiongyx.util;

import com.xiongyx.annotation.MyAliasFor;
import org.apache.commons.lang.StringUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * @author xiongyx
 * on 2019/6/12.
 */
public class AliasForUtil {

    public static boolean isAliasForAnnotation(Class<?> target, Class<? extends Annotation> aliasFor){
        // 判断当前类是否存在value属性
        Method valueMethod = getValueMethod(target);
        if(valueMethod == null){
            // 不存在value属性，不是对应的别名注解
            return false;
        }
        // 判断value属性上是否存在@AliasFor注解
        MyAliasFor myAliasFor = valueMethod.getAnnotation(MyAliasFor.class);
        if(myAliasFor == null){
            // value属性中不存在myAliasFor注解，不是对应的别名注解
            return false;
        }

        // 判断@AliasFor注解中的别名是否与aliasFor一致
        return (myAliasFor.annotation() == aliasFor);
    }

    private static Method getValueMethod(Class<?> target){
        Method[] valueMethods = target.getDeclaredMethods();

        for(Method method : valueMethods){
            if(StringUtils.equals(method.getName(),"value") && method.getReturnType() == String.class){
                return method;
            }
        }

        return null;
    }
}
