package com.xiongyx.controller;

import com.xiongyx.annotation.MyAutowired;
import com.xiongyx.annotation.MyController;
import com.xiongyx.annotation.MyRequestMapping;
import com.xiongyx.annotation.MyResponseBody;
import com.xiongyx.service.TestService;
import com.xiongyx.util.ClassUtil;

import javax.servlet.http.HttpServletRequest;
import java.util.Set;

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

    public static void main(String[] args) {
        String packageName = "com.xiongyx";
        Set set = ClassUtil.getClasses(packageName);
        System.out.println(set);
    }
}
