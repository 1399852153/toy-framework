package com.xiongyx.util;

import java.lang.annotation.Annotation;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author xiongyx
 * @date 2019/6/13
 *
 * 元注解工具类
 */
public class MetaAnnotationUtil {

    /**
     * 获得当前类所有元注解
     * */
    public static Set<Class<? extends Annotation>> readAllMetaAnnotation(Class<?> clazz){
        Set<Class<? extends Annotation>> metaAnnotationSet = new HashSet<>();

        Annotation[] annotations = clazz.getAnnotations();
        readAllMetaAnnotation(metaAnnotationSet,annotations);

        return metaAnnotationSet;
    }

    private static void readAllMetaAnnotation(Set<Class<? extends Annotation>> annotationSet,Annotation[] metaAnnotations){
        if(metaAnnotations.length == 0){
            return;
        }
        // 加入元注解集合
        boolean hasNewAnnotation = annotationSet.addAll(
            Arrays.stream(metaAnnotations)
                .map(Annotation::annotationType)
                .collect(Collectors.toSet())
        );
        if(!hasNewAnnotation){
            // 如果不存在新的注解，退出递归，避免死循环
            return;
        }

        for(Annotation item : metaAnnotations){
            // 递归获取所有的 元注解
            Annotation[] subMetaAnnotations = item.annotationType().getAnnotations();
            readAllMetaAnnotation(annotationSet,subMetaAnnotations);
        }
    }
}
