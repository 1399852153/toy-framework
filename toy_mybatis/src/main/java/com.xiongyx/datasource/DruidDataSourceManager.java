package com.xiongyx.datasource;

import com.alibaba.druid.pool.DruidDataSource;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;

/**
 * @author xiongyx
 * on 2019/7/10.
 *
 * druid 数据源管理
 */
public class DruidDataSourceManager implements DataSource{

    private static final Logger LOGGER = Logger.getLogger(DruidDataSourceManager.class);

    private String driver;
    private String url;
    private String username;
    private String password;

    /**
     * druid 数据源
     * */
    private DruidDataSource druidDataSource;

    /**
     * 数据库连接 线程变量
     * */
    private static final ThreadLocal<Connection> CONNECTION_HOLDER = new ThreadLocal<>();

    public DruidDataSourceManager(Map<String,String> properties) {
        this.driver = properties.get("driver");
        this.url = properties.get("url");
        this.username = properties.get("username");
        this.password = properties.get("password");

        // 初始化数据源
        init();
    }

    private void init(){
        try {
            // 尝试获取jdbc驱动类
            Class.forName(this.driver);
        } catch (ClassNotFoundException e) {
            LOGGER.error("can not load jdbc driver",e);
        }

        // 初始化druid数据库连接池
        this.druidDataSource = new DruidDataSource();
        this.druidDataSource.setDriverClassName(this.driver);
        this.druidDataSource.setUrl(this.url);
        this.druidDataSource.setUsername(this.username);
        this.druidDataSource.setPassword(this.password);
    }

    @Override
    public Connection getConnection() {
        // 从线程变量中获得数据库连接
        Connection conn = CONNECTION_HOLDER.get();

        if(conn == null){
            try {
                // 获得数据库连接
                conn = druidDataSource.getConnection();

                // 将数据库连接存入线程变量中保存
                CONNECTION_HOLDER.set(conn);
            } catch (SQLException e) {
                // 获取数据库连接失败
                LOGGER.error("get connection fail",e);
            }
        }
        return conn;
    }

    //===============================事务相关==================================
    /**
     * 开启事务
     * */
    public void beginTransaction(){
        Connection conn = getConnection();
        if(conn != null){
            try {
                conn.setAutoCommit(false);
            } catch (SQLException e) {
                LOGGER.error("begin transaction failure",e);
                throw new RuntimeException(e);
            }finally {
                CONNECTION_HOLDER.set(conn);
            }
        }
    }

    /**
     * 提交事务
     * */
    public void commitTransaction(){
        Connection conn = getConnection();
        if(conn != null){
            try {
                conn.commit();
                conn.close();
            } catch (SQLException e) {
                LOGGER.error("commit transaction failure",e);
                throw new RuntimeException(e);
            }finally {
                CONNECTION_HOLDER.remove();
            }
        }
    }

    /**
     * 回滚事务
     * */
    public void rollbackTransaction(){
        Connection conn = getConnection();
        if(conn != null){
            try {
                conn.rollback();
                conn.close();
            } catch (SQLException e) {
                LOGGER.error("rollback transaction failure",e);
                throw new RuntimeException(e);
            }finally {
                CONNECTION_HOLDER.remove();
            }
        }
    }
}
