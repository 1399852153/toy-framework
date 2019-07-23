package com.xiongyx.session;

import com.xiongyx.model.Configuration;

import java.util.List;

/**
 * @author xiongyx
 * on 2019/6/24.
 */
public interface SqlSession {


    /**
     * 获取mapper代理对象
     *
     * @param type
     * @param <T>
     * @return
     */
    <T> T getMapper(Class<T> type);

    /**
     * 查询单条记录
     *
     * @param statementId
     * @param parameter
     * @return
     * @see
     */
    <T> T selectOne(String statementId, Object parameter);

    /**
     * 查询多条记录
     *
     * @param statementId
     * @param parameter
     * @return
     * @see
     */
    <E> List<E> selectList(String statementId, Object parameter);

    /**
     * update
     *
     * @param statementId
     * @param parameter
     */
    void update(String statementId, Object parameter);


    /**
     * insert
     *
     * @param statementId
     * @param parameter
     */
    void insert(String statementId, Object parameter);

    Configuration getConfiguration();
}
