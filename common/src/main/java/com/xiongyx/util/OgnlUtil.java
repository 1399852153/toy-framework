package com.xiongyx.util;

import ognl.Ognl;
import ognl.OgnlException;
import org.apache.log4j.Logger;

import java.math.BigDecimal;
import java.util.Map;

/**
 * @author xiongyx
 * on 2019/7/21.
 */
public class OgnlUtil {

    private static final Logger logger = Logger.getLogger(OgnlUtil.class);

    /**
     * 解析 boolean表达式
     * */
    public static boolean evaluateBoolean(String expression, Object parameterObject) {
        try {
            Object value = Ognl.getValue(expression, parameterObject);

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

    /**
     * 解析 iterable表达式
     * */
    public static Iterable<?> evaluateIterable(String expression, Object parameterObject) {
        try {
            Object value = Ognl.getValue(expression, parameterObject);
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
