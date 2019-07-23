package com.xiongyx.session.defaults;

import com.xiongyx.binding.MapperProxy;
import com.xiongyx.executor.Executor;
import com.xiongyx.executor.SimpleExecutor;
import com.xiongyx.model.Configuration;
import com.xiongyx.model.MappedStatement;
import com.xiongyx.session.SqlSession;

import java.lang.reflect.Proxy;
import java.util.List;



/**
 * @author xiongyx
 * on 2019/6/24.
 */
public class DefaultSqlSession implements SqlSession {

    private final Configuration configuration;

    private final Executor executor;

    public DefaultSqlSession(Configuration configuration) {
        this.configuration = configuration;
        this.executor = new SimpleExecutor(configuration);
    }

    @Override
    public <T> T getMapper(Class<T> type) {

        // TODO mapperInter 接口获取
        Class mapperInterface = type;
        MapperProxy<T> tMapperProxy = new MapperProxy<>(this, mapperInterface);
        return (T) Proxy.newProxyInstance(mapperInterface.getClassLoader(), new Class[]{mapperInterface}, tMapperProxy);
    }

    @Override
    public <T> T selectOne(String statementId, Object parameter) {
        throw new RuntimeException("未实现");
        // return null;
    }

    @Override
    public <E> List<E> selectList(String statementId, Object parameter) {
        MappedStatement mappedStatement = configuration.getMappedStatement(statementId);

        return executor.doQuery(mappedStatement,parameter);
    }

    @Override
    public void update(String statementId, Object parameter) {

    }

    @Override
    public void insert(String statementId, Object parameter) {

    }

    @Override
    public Configuration getConfiguration() {
        return configuration;
    }

}
