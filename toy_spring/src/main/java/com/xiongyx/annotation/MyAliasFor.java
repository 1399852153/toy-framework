package com.xiongyx.annotation;

import java.lang.annotation.*;

/**
 * @author xiongyx
 * on 2019/6/12.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface MyAliasFor {

    Class<? extends Annotation> annotation() default Annotation.class;
}
