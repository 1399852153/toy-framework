package com.xiongyx.helper;

import com.xiongyx.annotation.MyAutowired;
import com.xiongyx.util.ReflectionUtil;

import java.lang.reflect.Field;
import java.util.Map;

/**
 * @author xiongyx
 * on 2019/6/12.
 */
public class IOCHelper {

    static{
        Map<Class<?>,Object> beanMap = BeanFactory.getBeanMap();

        // 如果beanMap不为空
        if(!beanMap.isEmpty()){
            // 遍历beanMap
            for(Map.Entry<Class<?>,Object> mapEntry : beanMap.entrySet()){
                Class<?> beanClass = mapEntry.getKey();
                Object beanInstance = mapEntry.getValue();

                // 获得当前bean的所有属性
                Field[] fields = beanClass.getDeclaredFields();
                // 遍历bean的所有属性
                for(Field beanField : fields){
                    // 如果带有 依赖注入的注解
                    if(beanField.isAnnotationPresent(MyAutowired.class)){
                        // 获得属性的class类型
                        Class<?> beanFieldClass = beanField.getType();
                        // 从bean容器中获得对应的bean实例
                        Object beanFieldInstance = beanMap.get(beanFieldClass);

                        // 如果bean容器中实例存在
                        if(beanFieldInstance != null){
                            // 通过反射，将其注入进当前bean对象的属性之中
                            ReflectionUtil.setField(beanInstance,beanField,beanFieldInstance);
                        }else{
                            // 否则抛出异常
                            throw new RuntimeException("can not find bean" +
                                    "current bean is:" + beanClass.getName() +
                                    "field is:" + beanField.getName() +
                                    "the bean need to autowired name is:" + beanFieldClass.getName()
                            );
                        }
                    }
                }
            }
        }
    }
}
