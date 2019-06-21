package com.xiongyx.helpler;

import com.xiongyx.annotation.MyController;
import com.xiongyx.annotation.MyRequestMapping;
import com.xiongyx.bean.Controller;
import com.xiongyx.bean.RequestHandler;
import com.xiongyx.bean.RequestHandlerKey;
import com.xiongyx.constant.URLConstant;
import com.xiongyx.enums.RequestHttpMethodEnum;
import com.xiongyx.helper.ClassHelper;
import org.apache.log4j.Logger;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @author xiongyx
 * on 2019/6/16.
 *
 * controller 初始化
 */
public class ControllerHelper {

    private static final Logger logger = Logger.getLogger(ControllerHelper.class);

    /**
     * controller路径和controller映射关系
     * */
    private static Map<String,Controller> controllerMap = new HashMap<>();

    /**
     * 完整的url地址和handler映射关系
     * */
    private static Map<RequestHandlerKey, RequestHandler> urlMappingMap = new HashMap<>();

    static{
        logger.info("初始化 controller requestMapping映射 开始 ========================================");

        // 获得所有的controller bean
        Set<Class<?>> controllerBeanSet = ClassHelper.getClassSetByAnnotation(MyController.class);

        controllerBeanSet.forEach(controllerClass ->{
                    String controllerRequestPath;
                    // 如果controller含有requestMapping注解
                    if(controllerClass.isAnnotationPresent(MyRequestMapping.class)){
                        MyRequestMapping myRequestMapping = controllerClass.getAnnotation(MyRequestMapping.class);
                        // 获得映射路径
                        controllerRequestPath = myRequestMapping.value();

                        // 不以"/"开头，默认补上
                        if(!controllerRequestPath.startsWith(URLConstant.URL_SEPARATOR)){
                            controllerRequestPath = URLConstant.URL_SEPARATOR + controllerRequestPath;
                        }
                    }else{
                        controllerRequestPath = "";
                    }

                    // 构造controllerBean
                    Controller controllerBean = initControllerBean(controllerRequestPath,controllerClass);
                    // 置入controllerMap之中
                    controllerMap.put(controllerRequestPath,controllerBean);
                }
        );

        logger.info("初始化 controller requestMapping映射 结束 ========================================");
    }

    public static RequestHandler getHandler(RequestHandlerKey requestHandlerKey){
        // todo /* 之类的动态匹配暂时不支持
        return urlMappingMap.get(requestHandlerKey);
    }

    /**
     * 初始化 controllerBean
     * */
    private static Controller initControllerBean(String controllerRequestPath,Class<?> controllerClass){
        Controller controllerBean = new Controller();
        // 设置映射路径
        controllerBean.setRequestPath(controllerRequestPath);

        // 构造handlerMap
        Map<RequestHandlerKey,RequestHandler> handlerMap = new HashMap<>();
        controllerBean.setHandlerMap(handlerMap);

        for(Method method : controllerClass.getMethods()){
            // 如果存在requestMapping注解
            if(method.isAnnotationPresent(MyRequestMapping.class)){
                MyRequestMapping methodRequestMapping = method.getAnnotation(MyRequestMapping.class);
                // 获得映射的路径

                String actionRequestPath = methodRequestMapping.value();

                RequestHttpMethodEnum[] requestHttpMethodEnum = methodRequestMapping.method();
                if(requestHttpMethodEnum.length == 0){
                    throw new RuntimeException("RequestHttpMethod is empty! method:" + method);
                }

                // 不以"/"开头，默认补上
                if(!actionRequestPath.startsWith("/")){
                    actionRequestPath = "/" + actionRequestPath;
                }

                String completeRequestPath = controllerRequestPath + actionRequestPath;

                for(RequestHttpMethodEnum httpMethodItem : requestHttpMethodEnum){
                    RequestHandlerKey requestHandlerKey = new RequestHandlerKey(completeRequestPath,httpMethodItem);
                    // 如果找到了完全相同的映射方法路径
                    if(urlMappingMap.containsKey(requestHandlerKey)){
                        RequestHandler sameURLHandler = urlMappingMap.get(requestHandlerKey);

                        // 抛出异常
                        throw new RuntimeException(
                                "same completeRequestPath in " + method.toString() + "and " + sameURLHandler.getHandleMethod());
                    }else{
                        // 设置handler对象
                        RequestHandler handler = new RequestHandler();
                        handler.setMethod(httpMethodItem);
                        handler.setActionRequestPath(actionRequestPath);
                        handler.setCompleteRequestPath(completeRequestPath);
                        handler.setHandleMethod(method);
                        handler.setControllerClass(controllerClass);

                        // 为controller的handleMap赋值
                        handlerMap.put(requestHandlerKey,handler);

                        // 设置完整的url地址和handler映射关系
                        urlMappingMap.put(requestHandlerKey,handler);

                        logger.info("requestMapping scan:" + requestHandlerKey);
                    }
                }
            }
        }

        return controllerBean;
    }
}
