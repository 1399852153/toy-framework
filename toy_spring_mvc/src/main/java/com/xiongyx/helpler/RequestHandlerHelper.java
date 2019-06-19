package com.xiongyx.helpler;

import com.xiongyx.annotation.MyRequestBody;
import com.xiongyx.annotation.MyRequestParam;
import com.xiongyx.bean.MyModel;
import com.xiongyx.bean.MyModelAndView;
import com.xiongyx.util.JsonUtil;
import com.xiongyx.util.TypeUtil;
import org.apache.commons.lang.StringUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.lang.annotation.Annotation;
import java.nio.charset.StandardCharsets;
import java.util.Map;

/**
 * @author xiongyx
 * on 2019/6/19.
 */
public class RequestHandlerHelper {

    public static Object handleAnnotationParam(Class methodParameterType, Map<Class<? extends Annotation>,Annotation> oneParamAnnotationClasses, HttpServletRequest request, HttpServletResponse response){
        if(oneParamAnnotationClasses.get(MyRequestParam.class) != null){
            // 如果存在MyRequestParam
            return handleMyRequestParam(methodParameterType,oneParamAnnotationClasses,request);
        }else if(oneParamAnnotationClasses.get(MyRequestBody.class) != null){
            // 如果存在MyRequestBody
            return handleMyRequestBody(methodParameterType,oneParamAnnotationClasses,request);
        }

        return null;
    }

    /**
     * 处理返回值
     * */
    public static void handleResult(Object result,HttpServletRequest request,HttpServletResponse response) throws IOException, ServletException {
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

    /**
     * 处理带 @MyRequestParam的参数
     * */
    private static Object handleMyRequestParam(Class methodParameterType, Map<Class<? extends Annotation>,Annotation> oneParamAnnotationClasses,HttpServletRequest request){
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

    /**
     * 处理带 @MyRequestBody的参数
     * */
    @SuppressWarnings("unchecked")
    private static Object handleMyRequestBody(Class methodParameterType, Map<Class<? extends Annotation>,Annotation> oneParamAnnotationClasses,HttpServletRequest request){
        try {
            BufferedReader streamReader = new BufferedReader(new InputStreamReader(request.getInputStream(),"UTF-8"));
            StringBuilder jsonStr = new StringBuilder();
            String inputStr;
            while ((inputStr = streamReader.readLine()) != null){
                jsonStr.append(inputStr);
            }

            Object requestBodyParam = JsonUtil.jsonStringToObject(jsonStr.toString(),methodParameterType);

            return requestBodyParam;
        } catch (IOException e) {
            throw new RuntimeException("handleMyRequestBody error",e);
        }
    }
}
