package test.demo.service.impl;

import com.xiongyx.annotation.MyAutowired;
import com.xiongyx.annotation.MyService;
import test.demo.service.OrderService;
import test.demo.service.ProductService;
import test.demo.service.UserService;

/**
 * @author xiongyx
 * on 2019/6/13.
 */
@MyService
public class OrderServiceImpl implements OrderService {

    @MyAutowired
    private ProductService productService;

    @MyAutowired
    private UserService userService;

    @Override
    public void insertOrder() {

    }
}
