package com.xiongyx.parsing;

import com.xiongyx.annotation.MyComponent;
import com.xiongyx.util.MetaAnnotationUtil;
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
        CLASS_SET = ClassUtil.scanAllClasses(appBasePackage);
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
        return CLASS_SET.stream()
                // 过滤出符合要求的类
                .filter(item-> isClassByAnnotation(item,annotationClass))
                .collect(Collectors.toSet());
    }

    private static boolean isClassByAnnotation(Class<?> clazz,Class<? extends Annotation> annotationClass){
        boolean isPresent = clazz.isAnnotationPresent(annotationClass);
        if(isPresent){
            return true;
        }

        Set<Class<? extends Annotation>> metaAnnotations = MetaAnnotationUtil.readAllMetaAnnotation(clazz);

        // 判断当前类 向上继承的所有元注解是否存在对应的注解
        return metaAnnotations.contains(annotationClass);
    }
}
