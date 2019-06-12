package com.xiongyx.helper;

import com.xiongyx.annotation.MyComponent;
import com.xiongyx.util.AliasForUtil;
import com.xiongyx.util.ClassUtil;

import java.lang.annotation.Annotation;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @Author xiongyx
 * on 2018/6/7.
 *
 * 类扫描helper
 */
public final class ClassHelper {

    /**
     * bean 工厂
     * */
    private static Set<Class<?>> CLASS_SET;

    static{
        String appBasePackage = ConfigHelper.getAppBasePackage();
        CLASS_SET = ClassUtil.getClasses(appBasePackage);
    }

    /**
     * 获取应用包下的类集合
     * */
    public static Set<Class<?>> getClassSet(){
        return CLASS_SET;
    }

    /**
     * 获取应用包下的 component组件对象
     * */
    public static Set<Class<?>> getComponentClassSet(){
        // 获得所有的component组件
        return new HashSet<>(getClassSetByAnnotation(MyComponent.class));
    }

    /**
     * 获取应用包下某一个带有某一注解的类
     * */
    public static Set<Class<?>> getClassSetByAnnotation(Class<? extends Annotation> annotationClass){
        Set<Class<?>> classSet = CLASS_SET.stream()
                .filter(
                        clazz -> {
                            boolean isComponentClass = clazz.isAnnotationPresent(annotationClass);
                            if(isComponentClass){
                                return true;
                            }else{
                                // todo 待实现 通过AliasFor进行继承
                                return AliasForUtil.isAliasForAnnotation(null,annotationClass);
                            }
                        })
                .collect(Collectors.toSet());
        return classSet;
    }
}
