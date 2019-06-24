package com.xiongyx.session.defaults;

import com.xiongyx.session.SqlSession;

import java.util.List;

/**
 * @author xiongyx
 * on 2019/6/24.
 */
public class DefaultSqlSession implements SqlSession {
    @Override
    public <T> T selectOne(String statementId, Object parameter) {
        return null;
    }

    @Override
    public <E> List<E> selectList(String statementId, Object parameter) {
        return null;
    }

    @Override
    public void update(String statementId, Object parameter) {

    }

    @Override
    public void insert(String statementId, Object parameter) {

    }
}
