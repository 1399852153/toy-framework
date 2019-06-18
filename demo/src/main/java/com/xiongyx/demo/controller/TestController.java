package com.xiongyx.demo.controller;

import com.xiongyx.annotation.MyAutowired;
import com.xiongyx.annotation.MyRequestMapping;
import com.xiongyx.annotation.MyRequestParam;
import com.xiongyx.annotation.MyRestController;
import com.xiongyx.bean.MyModel;
import com.xiongyx.bean.MyModelAndView;
import com.xiongyx.demo.service.OrderService;
import com.xiongyx.demo.service.ProductService;
import com.xiongyx.demo.service.UserService;
import com.xiongyx.enums.RequestHttpMethodEnum;
import com.xiongyx.util.ClassUtil;

import javax.servlet.http.HttpServletRequest;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
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

    @MyRequestMapping(value = "/testJsp",method = {RequestHttpMethodEnum.GET,RequestHttpMethodEnum.POST})
    public MyModelAndView testJsp(HttpServletRequest request){

        DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");
        String currentTime = dateFormat.format(new Date());

        MyModelAndView mv = new MyModelAndView();
        mv.setViewName("hello.jsp");
        mv.addObject("currentTime",currentTime);
        return mv;
    }

    @MyRequestMapping(value = "/testRequestParam",method = {RequestHttpMethodEnum.GET,RequestHttpMethodEnum.POST})
    public MyModel testRequestParam(
            @MyRequestParam("name") String name,
            @MyRequestParam("age") Integer age,
            @MyRequestParam(value = "money",required = false) Double money){
        System.out.println("testRequestParam: name=" + name + " age=" + age + " money=" + money);

        MyModel myModel = new MyModel();
        myModel.addObject("message","ojbk");
        return myModel;
    }
}
