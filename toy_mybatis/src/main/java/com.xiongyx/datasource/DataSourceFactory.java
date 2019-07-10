package com.xiongyx.datasource;

import javax.sql.DataSource;

/**
 * @author xiongyx
 * on 2019/7/10.
 */
public interface DataSourceFactory {

    DataSource getDataSource();
}
