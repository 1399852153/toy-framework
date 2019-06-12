package com.xiongyx.demo.service.impl;

import com.xiongyx.annotation.MyService;
import com.xiongyx.demo.service.TestService;

/**
 * @author xiongyx
 * @date 2019/6/11
 */
@MyService("testService")
public class TestServiceImpl implements TestService {

    @Override
    public String method1(String param) {
        return param + "_ok!!!";
    }
}
