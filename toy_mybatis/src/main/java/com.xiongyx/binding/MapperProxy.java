package com.xiongyx.binding;

import com.xiongyx.model.MappedStatement;
import com.xiongyx.session.SqlSession;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Program Name: toy-framework
 * <p>
 * Description:
 * <p>
 *
 * @author zhangjianwei
 * @version 1.0
 * @date 2019/7/23 1:58 PM
 */
public class MapperProxy<T> implements InvocationHandler {
    // private Connection connection;
    private SqlSession sqlSession;
    private Class<T> mapperInterface;

    public MapperProxy(SqlSession sqlSession, Class<T> mapperInterface) {
        this.sqlSession = sqlSession;
        this.mapperInterface = mapperInterface;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

        Object invokeObject;
        // Object 类的方法如tostring()直接调用
        if (Object.class.equals(method.getDeclaringClass())) {
            invokeObject= method.invoke(proxy, args);
        } else {
            invokeObject = this.execute(method, args);
        }

        return invokeObject;
    }


    Object execute(Method method, Object[] args) {
        // TODO 多个分支执行
        System.out.println("多个分支");
        // 接口全类名+statement
        String statementId = this.mapperInterface.getName() + "." + method.getName();
        MappedStatement mappedStatement = this.sqlSession.getConfiguration().getMappedStatement(statementId,true);

        Object param;
        Object result = null;
        switch (mappedStatement.getSqlCommandType()) {
            case SELECT:
                param = convertArgsToSqlCommandParam(args);
                Class<?> returnType = method.getReturnType();
                if (Collection.class.isAssignableFrom(returnType)) {
                    result = sqlSession.selectList(statementId, param);
                } else {
                    result = sqlSession.selectOne(statementId, param);
                }

            default:
        }
        return result;
    }


    /**
     *
     * // TODO args 的类型需要转回原始类型
     *
     * @param args
     * @return
     */
    private Object convertArgsToSqlCommandParam(Object[] args) {
        HashMap<String, Object> map = (HashMap<String, Object>) args[0];
        return map;
    }

}
