package com.xiongyx.util;

import org.apache.commons.beanutils.BeanUtils;

import java.util.Map;

/**
 * @Author xiongyx
 * @Create 2018/5/4.
 */
public class BeanUtil {

    /**
     * 对象转map
     */
    public static Map<String,Object> beanToMap(Object bean) throws Exception{
        if(bean == null){
            return null;
        }

        Map map = BeanUtils.describe(bean);

        //:::由于父类Object中存在getClass方法,会生成class属性,暂时打个补丁,去掉class字段
        map.remove("class");

        return map;
    }

    /**
     * map转对象
     */
    public static <T> T mapToObject(Map map,Class<T> clazz) throws Exception{
        if(map == null){
            return null;
        }

        T obj = clazz.newInstance();

        BeanUtils.populate(obj,map);

        return obj;
    }
}
