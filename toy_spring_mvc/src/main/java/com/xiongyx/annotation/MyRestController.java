package com.xiongyx.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author xiongyx
 * @date 2019/6/13
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@MyController
public @interface MyRestController {
    String value() default "";
}
