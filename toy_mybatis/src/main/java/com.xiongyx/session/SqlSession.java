package com.xiongyx.session;

import java.util.List;

/**
 * @author xiongyx
 * on 2019/6/24.
 */
public interface SqlSession {

    /**
     * 查询单条记录
     * @param statementId
     * @param parameter
     * @return
     * @see
     */
    <T> T selectOne(String statementId, Object parameter);

    /**
     * 查询多条记录
     * @param statementId
     * @param parameter
     * @return
     * @see
     */
    <E> List<E> selectList(String statementId, Object parameter);

    /**
     * update
     * @param statementId
     * @param parameter
     */
    void update(String statementId, Object parameter);


    /**
     * insert
     * @param statementId
     * @param parameter
     */
    void insert(String statementId, Object parameter);
}
