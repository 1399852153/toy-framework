package com.xiongyx.demo.service.impl;

import com.xiongyx.annotation.MyAutowired;
import com.xiongyx.annotation.MyService;
import com.xiongyx.demo.service.ProductService;
import com.xiongyx.demo.service.UserService;

/**
 * @author xiongyx
 * on 2019/6/13.
 */
@MyService
public class ProductServiceImpl implements ProductService {

    @MyAutowired
    private UserService userService;

    @Override
    public void insertProduct() {

    }
}
