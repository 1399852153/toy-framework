package com.xiongyx.helper;

import com.xiongyx.pattern.Patterns;
import com.xiongyx.util.ReflectionUtil;
import org.apache.log4j.Logger;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
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

                // 获得get方法名
                String getMethodName = ReflectionUtil.makeGetMethodName(formalParamItemName);
                Method getMethod = param.getClass().getMethod(getMethodName);
                // 反射调用get方法，获得实参值
                Object realParam = ReflectionUtil.invokeMethod(param,getMethod);

                realParamList.add(realParam);
            }

            return realParamList;
        } catch (NoSuchMethodException e) {
            logger.info("parseSqlParam error",e);
            throw new RuntimeException(e);
        }
    }

    /**
     * 提取出形参的名字
     * */
    private static String getParamName(String param){
        // #{name} -> name
        return param.substring(2,param.length()-1);
    }
}
