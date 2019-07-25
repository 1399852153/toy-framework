package com.xiongyx.scripting.sqlnode;

import ognl.Ognl;
import ognl.OgnlException;
import org.apache.log4j.Logger;

import java.math.BigDecimal;
import java.util.Map;

/**
 * @author xiongyx
 * on 2019/7/22.
 */
public class DynamicContextOgnlEvaluator {

    private static final Logger logger = Logger.getLogger(DynamicContextOgnlEvaluator.class);

    /**
     * 解析 boolean表达式
     * */
    public static boolean evaluateBoolean(String expression, DynamicSqlParseContext context) {
        try {
            Map<String, Object> bindings = context.getBindings();

            Object value = Ognl.getValue(expression, bindings);

            // 如果是false
            if(value instanceof Boolean && !((boolean)value)){
                // 再从传入的paramObject中获取
                value = Ognl.getValue(expression, context.getParamObject());
            }

            if (value instanceof Boolean) {
                return (Boolean) value;
            }
            if (value instanceof Number) {
                return !new BigDecimal(String.valueOf(value)).equals(BigDecimal.ZERO);
            }
            return value != null;
        } catch (OgnlException e) {
            logger.info("ognl evaluateBoolean error",e);
            throw new RuntimeException("ognl evaluateBoolean error",e);
        }
    }

    public static Iterable<?> evaluateIterable(String expression,DynamicSqlParseContext context){
        try {
            Map<String, Object> bindings = context.getBindings();

            // 先尝试从 binding中获取
            Object value = Ognl.getValue(expression, bindings);

            if(value == null){
                // 再从传入的paramObject中获取
                value = Ognl.getValue(expression, context.getParamObject());
            }

            if (value == null) {
                throw new RuntimeException("The expression '" + expression + "' evaluated to a null value.");
            }
            if (value instanceof Iterable) {
                return (Iterable<?>) value;
            }
            if (value instanceof Map) {
                return ((Map) value).entrySet();
            }
            throw new RuntimeException("Error evaluating expression '" + expression + "'.  Return value (" + value + ") was not iterable.");
        } catch (OgnlException e) {
            logger.info("ognl evaluateBoolean error",e);
            throw new RuntimeException("ognl evaluateBoolean error",e);
        }
    }
}
