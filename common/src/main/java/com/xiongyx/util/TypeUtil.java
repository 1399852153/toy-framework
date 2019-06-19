package com.xiongyx.util;

/**
 * @author xiongyx
 * on 2019/6/18.
 */
public class TypeUtil {

    /**
     * 判断是否是简单类型
     * */
    public static boolean isSimpleType(Object keyField){
        String simpleClassName = keyField.getClass().getSimpleName();

        if (simpleClassName.equals("int") || simpleClassName.equals("Integer")) {
            return true;
        }
        if (simpleClassName.equals("long") || simpleClassName.equals("Long")) {
            return true;
        }
        if (simpleClassName.equals("double") || simpleClassName.equals("Double")) {
            return true;
        }
        if (simpleClassName.equals("boolean") || simpleClassName.equals("Boolean")) {
            return true;
        }
        if (simpleClassName.equals("float") || simpleClassName.equals("Float")) {
            return true;
        }
        if (simpleClassName.equals("String")) {
            return true;
        }

        return false;
    }

    public static Object stringToSimpleType(Class clazz, String value){
        String simpleClassName = clazz.getSimpleName();

        if (simpleClassName.equals("int") || simpleClassName.equals("Integer")) {
            return Integer.parseInt(value);
        }
        if (simpleClassName.equals("long") || simpleClassName.equals("Long")) {
            return Long.parseLong(value);
        }
        if (simpleClassName.equals("double") || simpleClassName.equals("Double")) {
            return Double.parseDouble(value);
        }
        if (simpleClassName.equals("boolean") || simpleClassName.equals("Boolean")) {
            return Boolean.parseBoolean(value);
        }
        if (simpleClassName.equals("float") || simpleClassName.equals("Float")) {
            return Float.parseFloat(value);
        }
        if (simpleClassName.equals("String")) {
            return value;
        }

        throw new RuntimeException("unknown simpleType clazz=" + clazz.getSimpleName());
    }

    /**
     * 是否是基本类型
     * */
    public static boolean isPrimitiveType(Class clazz){
        String simpleClassName = clazz.getSimpleName();

        switch (simpleClassName){
            case "int":
            case "long":
            case "double":
            case "boolean":
            case "float":
                return true;
            default:
                return false;
        }
    }
}
