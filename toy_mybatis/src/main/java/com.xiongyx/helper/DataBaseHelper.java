package com.xiongyx.helper;

import com.alibaba.druid.pool.DruidDataSource;
import com.xiongyx.util.BeanUtil;
import com.xiongyx.util.CollectionUtil;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.MapListHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 数据库操作辅助类
 * @Author xiongyx
 * */
public class DataBaseHelper {

    private static final Logger LOGGER = LoggerFactory.getLogger(DataBaseHelper.class);

    /**
     * 驱动包
     * */
    private static final String DRIVER;

    /**
     * 连接字符串url
     */
    private static final String URL;

    /**
     * 连接账户
     */
    private static final String USERNAME;

    /**
     * 连接密码
     */
    private static final String PASSWORD;

    /**
     * 查询辅助对象
     */
    private static final QueryRunner QUERY_RUNNER = new QueryRunner();

    /**
     * 数据库连接 线程变量
     * */
    private static final ThreadLocal<Connection> CONNECTION_HOLDER = new ThreadLocal<>();

    /**
     * 数据库连接池
     * */
    private static final DruidDataSource DATA_SOURCE;

    static{
        DRIVER = DataBaseConfigHelper.getDBDriver();
        URL = DataBaseConfigHelper.getDBUrl();
        USERNAME = DataBaseConfigHelper.getDBUserName();
        PASSWORD = DataBaseConfigHelper.getDBPassword();

        try {
            // 尝试获取jdbc驱动类
            Class.forName(DRIVER);
        } catch (ClassNotFoundException e) {
            LOGGER.error("can not load jdbc driver",e);
        }

        // 初始化druid数据库连接池
        DATA_SOURCE = new DruidDataSource();
        DATA_SOURCE.setDriverClassName(DRIVER);
        DATA_SOURCE.setUrl(URL);
        DATA_SOURCE.setUsername(USERNAME);
        DATA_SOURCE.setPassword(PASSWORD);
    }

    /**
     * 获取数据库连接
     * */
    public static Connection getConnection(){
        // 从线程变量中获得数据库连接
        Connection conn = CONNECTION_HOLDER.get();

        if(conn == null){
            try {
                // 获得数据库连接
                conn = DATA_SOURCE.getConnection();

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
    public static void beginTransaction(){
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
    public static void commitTransaction(){
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
    public static void rollbackTransaction(){
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


    //===============================私有方法==================================

    /**
     * 根据类名获得表名(暂时靠约定: 表名 == 类名)
     */
    private static String getTableName(Class entityClass){
        return entityClass.getSimpleName();
    }


}