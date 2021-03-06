package com.xiongyx.helpler;

import com.xiongyx.HelperLoader;
import com.xiongyx.bean.RequestHandler;
import com.xiongyx.bean.RequestHandlerKey;
import com.xiongyx.enums.RequestHttpMethodEnum;
import com.xiongyx.parsing.BeanFactory;
import com.xiongyx.util.ClassUtil;
import com.xiongyx.util.ReflectionUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Writer;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author xiongyx
 * on 2019/6/16.
 */
@WebServlet(urlPatterns = "/*" ,loadOnStartup = 0)
public class DispatcherServlet extends HttpServlet {

    private static final Logger logger = LoggerFactory.getLogger(DispatcherServlet.class);

    @Override
    public void init(ServletConfig servletConfig){
        // 加载框架
        HelperLoader.init();

        // 加载ControllerHelper
        ClassUtil.loadClass(ControllerHelper.class.getName(),true);

        // 获得ServletContext对象
        ServletContext servletContext = servletConfig.getServletContext();

        // 注册处理jsp的servlet
        ServletRegistration jspServlet = servletContext.getServletRegistration("jsp");
        jspServlet.addMapping(MVCConfigHelper.getAppJspPath() + "*");

        // 注册处理静态资源的servlet
        ServletRegistration defaultServlet = servletContext.getServletRegistration("default");
        defaultServlet.addMapping(MVCConfigHelper.getAppAssetPath() + "*");
    }

    @Override
    public void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 获取请求的路径 + http请求的方法
        String requestPath = request.getPathInfo();
        String requestMethod = request.getMethod();
        logger.info("doService requestPath=" + requestPath + " requestMethod=" + requestMethod);

        RequestHttpMethodEnum methodEnum = RequestHttpMethodEnum.getEnumByCode(requestMethod);
        if(methodEnum == null){
            throw new RuntimeException("un support http method requestMethod=" + requestMethod);
        }

        // 从ControllerHelper中获取匹配的method反射调用
        RequestHandler requestHandler = ControllerHelper.getHandler(new RequestHandlerKey(requestPath,methodEnum));
        if(requestHandler == null){
            // 未找到匹配的handler
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            Writer writer = response.getWriter();

            writer.write("There was an unexpected error 404");
            writer.close();
            return;
        }

        // 根据参数列表(参数注解)注入对应的参数
        // 获得当前处理器的controller类
        Class<?> controllerClass = requestHandler.getControllerClass();
        // 获得bean工厂中的controller对象
        Object controllerBean = BeanFactory.getBean(controllerClass);

        Method actionMethod = requestHandler.getHandleMethod();

        // 获得需要传递给映射方法的参数列表
        Object[] methodParams = getMethodParams(actionMethod,request,response);

        // 反射调用方法,获得返回值
        Object result = ReflectionUtil.invokeMethod(controllerBean,actionMethod,methodParams);

        // 处理返回值
        RequestHandlerHelper.handleResult(result,request,response);
    }

    /**
     * 获得需要传递给映射方法的参数列表
     * */
    private Object[] getMethodParams(Method actionMethod,HttpServletRequest request,HttpServletResponse response){
        // 获得匹配到的方法的参数类型列表
        Class<?>[] methodParameterTypes = actionMethod.getParameterTypes();
        // 获得方法的各个参数注解
        Annotation[][] parameterAnnotations = actionMethod.getParameterAnnotations();

        // 执行的参数类型列表
        Object[] methodParams = new Object[methodParameterTypes.length];

        for(int i=0; i<methodParameterTypes.length; i++){
            Class methodParameterType = methodParameterTypes[i];
            // 当前参数的 注解列表
            Annotation[] oneParamAnnotations = parameterAnnotations[i];

            if(methodParameterType.equals(HttpServletRequest.class)){
                // 注入request
                methodParams[i] = request;
            }else if(methodParameterType.equals(HttpServletResponse.class)){
                // 注入response
                methodParams[i] = response;
            }else {
                Map<Class<? extends Annotation>,Annotation> oneParamAnnotationClasses = Arrays.stream(oneParamAnnotations)
                        .collect(Collectors.toMap(Annotation::annotationType, item-> item));

                // 根据注解 生成相应的参数对象，注入对应的请求参数
                Object injectedParam = RequestHandlerHelper.handleAnnotationParam(methodParameterType,oneParamAnnotationClasses,request,response);
                methodParams[i] = injectedParam;
            }
        }

        return methodParams;
    }

}
