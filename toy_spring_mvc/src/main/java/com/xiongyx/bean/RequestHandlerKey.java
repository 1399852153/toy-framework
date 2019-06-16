package com.xiongyx.bean;

import com.xiongyx.enums.RequestHttpMethodEnum;

import java.util.Objects;

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

    public RequestHandlerKey(String completeRequestPath, RequestHttpMethodEnum method) {
        this.completeRequestPath = completeRequestPath;
        this.method = method;
    }

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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        RequestHandlerKey that = (RequestHandlerKey) o;
        return Objects.equals(completeRequestPath, that.completeRequestPath) &&
                method == that.method;
    }

    @Override
    public int hashCode() {
        return Objects.hash(completeRequestPath, method);
    }
}
