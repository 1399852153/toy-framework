package com.xiongyx.demo.controller;

import com.xiongyx.annotation.MyAutowired;
import com.xiongyx.annotation.MyRequestMapping;
import com.xiongyx.annotation.MyRestController;
import com.xiongyx.demo.service.OrderService;
import com.xiongyx.demo.service.ProductService;
import com.xiongyx.demo.service.UserService;
import com.xiongyx.util.ClassUtil;

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
}
