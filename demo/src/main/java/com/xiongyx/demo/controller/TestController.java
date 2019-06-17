package com.xiongyx.demo.controller;

import com.xiongyx.annotation.MyAutowired;
import com.xiongyx.annotation.MyRequestMapping;
import com.xiongyx.annotation.MyRestController;
import com.xiongyx.bean.MyModel;
import com.xiongyx.demo.service.OrderService;
import com.xiongyx.demo.service.ProductService;
import com.xiongyx.demo.service.UserService;
import com.xiongyx.enums.RequestHttpMethodEnum;
import com.xiongyx.util.ClassUtil;

import javax.servlet.http.HttpServletRequest;
import java.util.Set;

/**
 * @author xiongyx
 * @date 2019/6/11
 */
@MyRestController
@MyRequestMapping("/test")
public class TestController {

    @MyAutowired
    private OrderService orderService;

    @MyAutowired
    private ProductService productService;

    @MyAutowired
    private ProductService productService2;

    @MyAutowired
    private UserService userService;

    @MyRequestMapping(value = "/echo",method = {RequestHttpMethodEnum.GET,RequestHttpMethodEnum.POST})
    public MyModel echo(HttpServletRequest request){
        String message = request.getParameter("message");

        MyModel myModel = new MyModel();
        myModel.addObject("message",message + " echo !");
        return myModel;
    }
}
