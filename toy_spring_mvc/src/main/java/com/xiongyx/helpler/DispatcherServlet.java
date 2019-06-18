package com.xiongyx.helpler;

import com.xiongyx.HelperLoader;
import com.xiongyx.annotation.MyRequestParam;
import com.xiongyx.bean.MyModel;
import com.xiongyx.bean.MyModelAndView;
import com.xiongyx.bean.RequestHandler;
import com.xiongyx.bean.RequestHandlerKey;
import com.xiongyx.enums.RequestHttpMethodEnum;
import com.xiongyx.helper.BeanFactory;
import com.xiongyx.util.ClassUtil;
import com.xiongyx.util.JsonUtil;
import com.xiongyx.util.ReflectionUtil;
import com.xiongyx.util.TypeUtil;
import org.apache.commons.lang.StringUtils;
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
import java.io.PrintWriter;
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
        handleResult(result,request,response);
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
                Object injectedParam = handleAnnotationParam(methodParameterType,oneParamAnnotationClasses,request,response);
                methodParams[i] = injectedParam;
            }
        }

        return methodParams;
    }

    /**
     * 处理参数
     * */
    private Object handleAnnotationParam(Class methodParameterType,Map<Class<? extends Annotation>,Annotation> oneParamAnnotationClasses, HttpServletRequest request, HttpServletResponse response){
        // 如果存在MyRequestParam
        if(oneParamAnnotationClasses.get(MyRequestParam.class) != null){
            // MyRequestParam 只支持简单类型的映射
            if(!TypeUtil.isSimpleType(methodParameterType.getSimpleName())){
                throw new RuntimeException("MyRequestParam need inject by simpleType methodParameterType=" + methodParameterType.getSimpleName());
            }

            // 获取注解中param的key
            MyRequestParam myRequestParam = (MyRequestParam)oneParamAnnotationClasses.get(MyRequestParam.class);
            String paramName = myRequestParam.value();
            // 从请求中获取对应的value
            String paramValue = request.getParameter(paramName);

            if(myRequestParam.required() && paramValue == null){
                // 注解中标明必传 但是value是null
                throw new RuntimeException("MyRequestParam required a value paramName=" + paramName);
            }

            if(TypeUtil.isPrimitiveType(methodParameterType) && paramValue == null){
                // 如果参数类型是基本类型 但是value是null
                throw new RuntimeException("MyRequestParam param declared as a primitive type, but value is null paramName=" + paramName);
            }


            // 根据类型转化为对应的类型
            return TypeUtil.stringToSimpleType(methodParameterType,paramValue);
        }

        return null;
    }

    /**
     * 处理返回值
     * */
    private void handleResult(Object result,HttpServletRequest request,HttpServletResponse response) throws IOException, ServletException {
        // 如果返回的是modelAndView
        if(result instanceof MyModelAndView){
            // 返回jsp
            MyModelAndView mv = (MyModelAndView) result;
            String path = mv.getViewName();
            if(StringUtils.isNotEmpty(path)){
                if(path.startsWith("/")){
                    response.sendRedirect(request.getContextPath() + path);
                }else{
                    Map<String,Object> model = mv.getModelMap();
                    for(Map.Entry<String,Object> entry : model.entrySet()){
                        request.setAttribute(entry.getKey(),entry.getValue());
                    }

                    request.getRequestDispatcher(MVCConfigHelper.getAppJspPath() + path).forward(request,response);
                }
            }
        }else if(result instanceof MyModel){
            // 返回json字符串
            MyModel model = (MyModel) result;

            Map modelMap = model.getModelMap();

            if(modelMap != null){
                response.setContentType("application/json");
                response.setCharacterEncoding("UTF-8");
                PrintWriter writer = response.getWriter();
                String json = JsonUtil.objectToJsonString(modelMap);
                writer.write(json);
                writer.flush();
                writer.close();
            }
        }
    }
}
