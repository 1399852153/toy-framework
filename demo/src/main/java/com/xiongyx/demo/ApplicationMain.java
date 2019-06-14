package com.xiongyx.demo;

import com.xiongyx.HelperLoader;
import com.xiongyx.helper.BeanFactory;

import java.util.Map;

/**
 * @author xiongyx
 * on 2019/6/12.
 */
public class ApplicationMain {

    public static void main(String[] args) {
        // 初始化框架
        HelperLoader.init();

        Map<Class<?>,Object> beanMap = BeanFactory.getBeanMap();

    }
}
