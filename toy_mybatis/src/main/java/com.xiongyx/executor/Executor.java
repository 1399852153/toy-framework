package com.xiongyx.executor;

import com.xiongyx.model.MappedStatement;

import java.util.List;

/**
 * @author xiongyx
 * on 2019/6/26.
 */
public interface Executor {

    /**
     * 查询
     */
    <E> List<E> doQuery(MappedStatement mappedStatement, Object parameter);

    /**
     * 更新
     */
    void doUpdate(MappedStatement mappedStatement, Object parameter);
}
