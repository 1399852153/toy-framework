package com.xiongyx.service.impl;

import com.xiongyx.annotation.MyService;
import com.xiongyx.service.TestService;

/**
 * @author xiongyx
 * @date 2019/6/11
 */
@MyService("testService")
public class TestServiceImpl implements TestService {

    public String method1(String param) {
        return param + "_ok!!!";
    }
}
