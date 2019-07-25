package com.xiongyx.mapping.sqlsource;

import com.xiongyx.mapping.BoundSql;

/**
 * @author xiongyx
 * @date 2019/7/17
 */
public interface SqlSource {

    BoundSql getBoundSql(Object paramObject);
}
