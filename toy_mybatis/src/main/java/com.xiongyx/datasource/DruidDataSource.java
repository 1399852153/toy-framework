package com.xiongyx.datasource;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;

/**
 * @author xiongyx
 * on 2019/7/10.
 */
public class DruidDataSource implements DataSource{

    private String driver;
    private String url;
    private String username;
    private String password;

    public DruidDataSource(Map<String,String> properties) {
        this.driver = properties.get("driver");
        this.url = properties.get("url");
        this.username = properties.get("username");
        this.password = properties.get("password");

        // 初始化数据源
        init();
    }

    private void init(){

    }

    @Override
    public Connection getConnection() throws SQLException {
        return null;
    }

    @Override
    public Connection getConnection(String username, String password) throws SQLException {
        return null;
    }
}
