package com.xiongyx.util;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;

import java.util.Collection;
import java.util.Map;

/**
 * @Author xiongyx
 * @Create 2018/5/4.
 */
public class CollectionUtil {

    /**
     * collection是否为空
     */
    public static boolean isEmpty(Collection<?> collection){
        return CollectionUtils.isEmpty(collection);
    }

    /**
     * collection是否不为空
     */
    public static boolean isNotEmpty(Collection<?> collection){
        return !CollectionUtils.isEmpty(collection);
    }

    /**
     * map是否为空
     */
    public static boolean isEmpty(Map<?,?> map){
        return MapUtils.isEmpty(map);
    }

    /**
     * map是否不为空
     */
    public static boolean isNotEmpty(Map<?,?> map){
        return !isEmpty(map);
    }


}
