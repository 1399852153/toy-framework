package com.xiongyx.demo;

import com.xiongyx.HelperLoader;
import com.xiongyx.annotation.MyComponent;
import com.xiongyx.annotation.MyService;
import com.xiongyx.demo.service.impl.TestServiceImpl;
import com.xiongyx.util.MetaAnnotationUtil;

import java.lang.annotation.Annotation;

/**
 * @author xiongyx
 * on 2019/6/12.
 */
public class ApplicationMain {

    public static void main(String[] args) {
        HelperLoader.init();

//        MetaAnnotationUtil.readAllMetaAnnotation(TestServiceImpl.class);

//        boolean result1 = MyService.class.isAnnotationPresent(MyComponent.class);
//
//        boolean result2 = TestServiceImpl.class.isAnnotationPresent(MyService.class);
//        boolean result3 = TestServiceImpl.class.isAnnotationPresent(MyComponent.class);
//
//        Annotation myService = TestServiceImpl.class.getAnnotations()[0];
//        Class clazz = myService.annotationType();
//        Annotation[] annotations = clazz.getAnnotations();
    }
}
