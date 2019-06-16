package com.xiongyx.bean;

import com.xiongyx.enums.RequestHttpMethodEnum;

import java.lang.reflect.Method;

/**
 * @author xiongyx
 * on 2019/6/16.
 */
public class RequestHandler {

    /**
     * 完整的url请求路径
     * */
    private String completeRequestPath;

    /**
     * 请求的方法类型
     * */
    private RequestHttpMethodEnum method;

    /**
     * 处理请求的路径
     * */
    private String actionRequestPath;

    /**
     * 处理请求的方法
     * */
    private Method handleMethod;

    /**
     * 处理方法所属的controller类
     * */
    private Class<?> controllerClass;

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

    public String getActionRequestPath() {
        return actionRequestPath;
    }

    public void setActionRequestPath(String actionRequestPath) {
        this.actionRequestPath = actionRequestPath;
    }

    public Method getHandleMethod() {
        return handleMethod;
    }

    public void setHandleMethod(Method handleMethod) {
        this.handleMethod = handleMethod;
    }

    public Class<?> getControllerClass() {
        return controllerClass;
    }

    public void setControllerClass(Class<?> controllerClass) {
        this.controllerClass = controllerClass;
    }
}
