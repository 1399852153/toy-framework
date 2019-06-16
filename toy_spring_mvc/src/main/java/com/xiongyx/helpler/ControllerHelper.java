package com.xiongyx.helpler;

import com.xiongyx.annotation.MyController;
import com.xiongyx.helper.ClassHelper;

import java.util.Set;

/**
 * @author xiongyx
 * on 2019/6/16.
 *
 * controller 初始化
 */
public class ControllerHelper {

    static{
        // 获得所有的controller bean
        Set<Class<?>> controllerBeanSet = ClassHelper.getClassSetByAnnotation(MyController.class);


    }
}
