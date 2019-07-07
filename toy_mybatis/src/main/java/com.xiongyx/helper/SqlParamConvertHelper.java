package com.xiongyx.helper;

import com.xiongyx.pattern.Patterns;
import com.xiongyx.util.ReflectionUtil;
import org.apache.log4j.Logger;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;

/**
 * @author xiongyx
 * on 2019/7/6.
 *
 * sql参数形参->实参的转换
 */
public class SqlParamConvertHelper {

    private static Logger logger = Logger.getLogger(SqlParamConvertHelper.class);

    public static List parseSqlParam(String sqlSource, Object param) {
        try {
            List<Object> realParamList = new ArrayList<>();

            // 参数 #{}匹配
            Matcher matcher = Patterns.param_pattern.matcher(sqlSource);
            while(matcher.find()){
                String formalParamItem = matcher.group();
                // 获得形参名
                String formalParamItemName = getParamName(formalParamItem);

                if(param instanceof Map){
                    Object realParam = handleMapParam(param,formalParamItemName);
                    realParamList.add(realParam);
                }else{
                    Object realParam = handlePojoParam(param,formalParamItemName);
                    realParamList.add(realParam);
                }
            }

            return realParamList;
        } catch (NoSuchMethodException e) {
            logger.info("parseSqlParam error",e);
            throw new RuntimeException(e);
        }
    }

    private static Object handleMapParam(Object param, String formalParamItemName){
        Map mapParam = (Map)param;
        // map参数对象 直接get
        return mapParam.get(formalParamItemName);
    }

    private static Object handlePojoParam(Object param, String formalParamItemName) throws NoSuchMethodException {
        // 获得get方法名
        String getMethodName = ReflectionUtil.makeGetMethodName(formalParamItemName);
        Method getMethod = param.getClass().getMethod(getMethodName);
        // 反射调用get方法，获得实参值

        return ReflectionUtil.invokeMethod(param,getMethod);
    }


    /**
     * 提取出形参的名字
     * */
    private static String getParamName(String param){
        // #{name} -> name
        return param.substring(2,param.length()-1);
    }
}
