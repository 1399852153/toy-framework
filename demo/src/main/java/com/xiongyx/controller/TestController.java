package com.xiongyx.controller;

import com.xiongyx.annotation.MyAutowired;
import com.xiongyx.annotation.MyController;
import com.xiongyx.annotation.MyRequestMapping;
import com.xiongyx.annotation.MyResponseBody;
import com.xiongyx.service.TestService;

import javax.servlet.http.HttpServletRequest;

/**
 * @author xiongyx
 * @date 2019/6/11
 */
@MyController
@MyRequestMapping("/test")
public class TestController {

    @MyAutowired
    private TestService testService;

    @MyRequestMapping("/method1")
    @MyResponseBody
    public String method1(HttpServletRequest request){
        String param = request.getParameter("param");
        return testService.method1(param);
    }
}
