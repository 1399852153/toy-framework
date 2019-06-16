package com.xiongyx.bean;

import com.xiongyx.enums.RequestHttpMethodEnum;

/**
 * @author xiongyx
 * on 2019/6/16.
 */
public class RequestHandlerKey {

    /**
     * 完整的url请求路径
     * */
    private String completeRequestPath;

    /**
     * 请求的方法类型
     * */
    private RequestHttpMethodEnum method;

    public String getCompleteRequestPath() {
        return completeRequestPath;
    }

    public void setCompleteRequestPath(String completeRequestPath) {
        this.completeRequestPath = completeRequestPath;
    }

    public RequestHttpMethodEnum getMethod() {
        return method;
    }

    public void setMethod(RequestHttpMethodEnum method) {
        this.method = method;
    }
}
