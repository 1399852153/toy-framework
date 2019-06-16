package com.xiongyx.bean;

import java.util.Map;

/**
 * @Author xiongyx
 * on 2018/6/11.
 *
 * Controller
 */
public class Controller {

    /**
     * 请求的路径
     * */
    private String requestPath;

    /**
     * 请求处理对象 集合
     * */
    private Map<RequestHandlerKey,RequestHandler> handlerMap;

    public String getRequestPath() {
        return requestPath;
    }

    public void setRequestPath(String requestPath) {
        this.requestPath = requestPath;
    }

    public Map<RequestHandlerKey, RequestHandler> getHandlerMap() {
        return handlerMap;
    }

    public void setHandlerMap(Map<RequestHandlerKey, RequestHandler> handlerMap) {
        this.handlerMap = handlerMap;
    }
}
