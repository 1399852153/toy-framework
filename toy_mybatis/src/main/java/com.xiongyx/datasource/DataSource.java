package com.xiongyx.datasource;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * @author xiongyx
 * on 2019/7/10.
 */
public interface DataSource {

    Connection getConnection() throws SQLException;
}
