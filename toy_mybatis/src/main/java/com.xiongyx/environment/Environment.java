package com.xiongyx.environment;

import com.xiongyx.datasource.DataSource;

/**
 * @author xiongyx
 * on 2019/7/10.
 */
public final class Environment {

    private final DataSource dataSource;

    public Environment(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public DataSource getDataSource() {
        return dataSource;
    }
}
