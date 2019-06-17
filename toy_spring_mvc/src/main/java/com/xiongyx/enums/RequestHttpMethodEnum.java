package com.xiongyx.enums;

/**
 * @author xiongyx
 * on 2019/6/16.
 *
 * http请求方式枚举
 */
public enum RequestHttpMethodEnum {

    /**
     * get请求
     * */
    GET("GET","get请求"),

    /**
     * post请求
     * */
    POST("POST","post请求");

    private String code;
    private String message;

    RequestHttpMethodEnum(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public static RequestHttpMethodEnum getEnumByCode(String code){
        for(RequestHttpMethodEnum requestHttpMethodEnum : RequestHttpMethodEnum.values()){
            if(requestHttpMethodEnum.code.equals(requestHttpMethodEnum.code)){
                return requestHttpMethodEnum;
            }
        }

        return null;
    }
}
