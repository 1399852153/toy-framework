package com.xiongyx.util;

import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Author xiongyx
 * @Create 2018/6/14.
 *
 * json工具类
 */
public class JsonUtil {
    private static Logger LOGGER = LoggerFactory.getLogger(JsonUtil.class);

    private static final Gson gson = new Gson();

    /**
     * json字符串转java对象
     * */
    public static <T> T jsonStringToObject(String jsonStr,Class<T> clazz){
        return gson.fromJson(jsonStr,clazz);
    }

    /**
     * java对象转json字符串
     * */
    public static <T> String objectToJsonString(T obj){
        return gson.toJson(obj);
    }
}
