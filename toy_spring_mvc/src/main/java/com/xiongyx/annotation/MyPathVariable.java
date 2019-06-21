package com.xiongyx.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author xiongyx
 * @date 2019/6/11
 *
 * todo MyPathVariable 涉及到动态url匹配，比较复杂，暂不支持
 * todo 处理逻辑可以参考spring-core中的 AntPathMatcher
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface MyPathVariable {
}
