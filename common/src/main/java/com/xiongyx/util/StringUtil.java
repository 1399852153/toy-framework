package com.xiongyx.util;

import org.apache.commons.lang3.StringUtils;

/**
 * @author xiongyx
 * on 2019/7/6.
 */
public class StringUtil {

    public static boolean isEmpty(String str){
        return StringUtils.isEmpty(str);
    }

    /**
     * 将字符串的第一个字母转为大写
     * @param str 需要被转变的字符串
     * @return 返回转变之后的字符串
     */
    public static String transFirstCharUpperCase(String str){
        return str.replaceFirst(str.substring(0, 1), str.substring(0, 1).toUpperCase());
    }
}
