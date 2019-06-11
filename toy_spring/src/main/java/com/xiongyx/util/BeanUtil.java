package com.xiongyx.util;

import com.xiongyx.annotation.MyComponent;

import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author xiongyx
 * on 2019/6/11.
 */
public class BeanUtil {

    public Set<Class<?>> getAllComponents(Set<Class<?>> classSet){
        return classSet.stream().filter(
                clazz-> clazz.isAnnotationPresent(MyComponent.class)
        ).collect(Collectors.toSet());
    }
}
