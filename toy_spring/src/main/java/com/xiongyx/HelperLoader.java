package com.xiongyx;

import com.xiongyx.parsing.BeanFactory;
import com.xiongyx.parsing.ClassHelper;
import com.xiongyx.parsing.IOCHelper;
import com.xiongyx.util.ClassUtil;

/**
 * @Author xiongyx
 * on 2018/6/13.
 *
 * 框架初始化
 */
public final class HelperLoader {

    public static void init(){
        // 初始化 进行关键类文件的加载
        Class<?>[] classes = {
                ClassHelper.class,
                BeanFactory.class,
                IOCHelper.class,
        };

        for(Class<?> clazz : classes){
            ClassUtil.loadClass(clazz.getName(),true);
        }
    }
}
