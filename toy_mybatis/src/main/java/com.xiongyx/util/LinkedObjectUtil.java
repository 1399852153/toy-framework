package com.xiongyx.util;

import java.lang.reflect.Field;
import java.util.*;

/**
 * @author xiongyx
 * @date 2019/8/13
 */
public class LinkedObjectUtil {

    /**
     * 设置Association属性
     * */
    public static void setAssociationProperty(Object parent,String propertyName,Object subObject) throws Exception {
        Class parentClass = parent.getClass();
        Field field = parentClass.getDeclaredField(propertyName);
        ReflectionUtil.setField(parent,field,subObject);
    }

    /**
     * 设置Collection属性
     * */
    @SuppressWarnings("unchecked")
    public static void setCollectionProperty(Object parent,String propertyName,Object subObject) throws Exception {
        Object collectionObj = ReflectionUtil.getPropertyValue(propertyName,parent);
        if(collectionObj == null){

            Class collectionClass = ReflectionUtil.getPropertyType(propertyName,parent);
            Class collectionClassImpl = getCollectionImplByClass(collectionClass);
            Object newCollection = ReflectionUtil.newInstance(collectionClassImpl);
            if(newCollection instanceof Collection){
                ((Collection)newCollection).add(subObject);
                Class parentClass = parent.getClass();
                Field field = parentClass.getDeclaredField(propertyName);
                ReflectionUtil.setField(parent,field,newCollection);
            }else{
                throw new RuntimeException("setCollectionProperty error property is not a collection: propertyName=" + propertyName);
            }
        }else{
            if(collectionObj instanceof Collection){
                ((Collection)collectionObj).add(subObject);
            }else{
                throw new RuntimeException("setCollectionProperty error property is not a collection: propertyName=" + propertyName);
            }
        }
    }

    /**
     * 将collectionClass接口 转化为其对应的实现
     * 暂时只支持常见的集合类转化
     * */
    private static Class getCollectionImplByClass(Class collectionClass){
        if(collectionClass == List.class || collectionClass == Collection.class){
            return ArrayList.class;
        }

        if(collectionClass == Map.class){
            return HashMap.class;
        }

        if(collectionClass == Set.class){
            return HashSet.class;
        }

        // 默认情况，返回原class
        return collectionClass;
    }
}
