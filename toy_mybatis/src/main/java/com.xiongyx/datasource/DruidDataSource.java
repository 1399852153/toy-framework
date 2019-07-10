package com.xiongyx.datasource;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * @author xiongyx
 * on 2019/7/10.
 */
public class DruidDataSource implements DataSource{
    @Override
    public Connection getConnection() throws SQLException {
        return null;
    }

    @Override
    public Connection getConnection(String username, String password) throws SQLException {
        return null;
    }
}
