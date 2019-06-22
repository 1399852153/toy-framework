package com.xiongyx.helper;

import com.alibaba.druid.pool.DruidDataSource;
import com.xiongyx.util.BeanUtil;
import com.xiongyx.util.CollectionUtil;
import com.xiongyx.util.PropsUtil;
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
import java.util.Properties;

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
        //:::从配置文件中初始化数据
        Properties properties = PropsUtil.loadProps("smart.properties");
        DRIVER = PropsUtil.getString(properties,"jdbc.driver");
        URL = PropsUtil.getString(properties,"jdbc.url");
        USERNAME = PropsUtil.getString(properties,"jdbc.username");
        PASSWORD = PropsUtil.getString(properties,"jdbc.password");

        try {
            //:::尝试获取jdbc驱动类
            Class.forName(DRIVER);
        } catch (ClassNotFoundException e) {
            LOGGER.error("can not load jdbc driver",e);
        }

        //:::初始化druid数据库连接池
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
        //:::从线程变量中获得数据库连接
        Connection conn = CONNECTION_HOLDER.get();

        if(conn == null){
            try {
                //:::获得数据库连接
                conn = DATA_SOURCE.getConnection();

                //:::将数据库连接存入线程变量中保存
                CONNECTION_HOLDER.set(conn);
            } catch (SQLException e) {
                //:::获取数据库连接失败
                LOGGER.error("get connection fail",e);
            }
        }
        return conn;
    }

    /**
     * 通用更新接口 (insert，delete，update),直接传sql
     * @return 受影响的记录行数
     */
    public static int executeUpdate(String sql,Object... params){
        int rows;

        try {
            Connection conn = getConnection();
            //:::执行更新语句
            rows = QUERY_RUNNER.update(conn,sql,params);
        } catch (SQLException e) {
            LOGGER.error("execute update fail",e);
            throw new RuntimeException(e);
        }

        return rows;
    }

    /**
     * 通用连表查询接口,直接传sql
     */
    public static List<Map<String,Object>> executeQuery(String sql,Object... params){
        List<Map<String,Object>> result;

        try {
            Connection conn = getConnection();
            //:::进行查询
            result = QUERY_RUNNER.query(conn,sql,new MapListHandler(),params);
        } catch (SQLException e) {
            LOGGER.error("execute query fail",e);
            throw new RuntimeException(e);
        }

        return result;
    }


    /**
     * 查询单个实体,直接传sql
     */
    public static <T> T queryEntity(Class<T> entityClass,String sql,Object... params){
        T entity;

        try {
            Connection conn = getConnection();
            //:::进行查询
            entity = QUERY_RUNNER.query(conn,sql,new BeanHandler<T>(entityClass),params);
        } catch (SQLException e) {
            LOGGER.error("query entity fail",e);
            throw new RuntimeException(e);
        }
        return entity;
    }

    /**
     * 查询实体列表,直接传sql
     * */
    public static <T> List<T> queryEntityList(Class<T> entityClass,String sql,Object... params){
        List<T> entityList;

        try {
            Connection conn = getConnection();
            //:::进行查询
            entityList = QUERY_RUNNER.query(conn,sql,new BeanListHandler<>(entityClass),params);
        } catch (SQLException e) {
            //:::查询失败
            LOGGER.error("query entity list fail",e);
            throw new RuntimeException(e);
        }

        return  entityList;
    }

    /**
     * insert插入接口
     */
    public static <T> boolean insertEntity(T obj,Class entityClass) throws Exception {
        Map<String,Object> fieldMap = BeanUtil.beanToMap(obj);

        if(CollectionUtil.isEmpty(fieldMap)){
            LOGGER.error("can not insert entity: filedMap is Empty");
            return false;
        }

        //:::按照约定,获得表名
        String tableName = getTableName(entityClass);
        //:::构造insert语句开头
        String sql = "insert into " + tableName;

        //:::insert语句列的部分
        StringBuilder columns = new StringBuilder("(");
        //:::insert语句values的部分
        StringBuilder values = new StringBuilder("(");
        //:::参数列表值
        List<Object> params = new ArrayList<>();

        //:::遍历字段map,构造sql
        for(String fieldName : fieldMap.keySet()){
            //:::构造列的部分
            columns.append(fieldName).append(", ");
            //:::构造values的部分
            values.append("?, ");

            //:::获得key对应的value
            Object value = fieldMap.get(fieldName);
            //:::将其加入参数值列表
            params.add(value);
        }

        //:::将列和value的最后一个逗号删除,并且在字符串的最后用 ')' 代替
        columns.replace(columns.lastIndexOf(", "),columns.length()-1,")");
        values.replace(values.lastIndexOf(", "),values.length()-1,")");

        //:::拼接构造出最终的sql
        sql += columns + "values" + values;

        return executeUpdate(sql,params.toArray()) == 1;
    }

    /**
     * delete删除接口
     */
    public static <T> boolean deleteEntity(String id,Class<T> entityClass){
        //:::获得表名
        String tableName = getTableName(entityClass);

        //:::按照约定,主键名必须为id
        String sql = "delete from " + tableName + " where id = ?";

        return executeUpdate(sql,id) == 1;
    }

    /**
     * update更新接口
     */
    public static <T> boolean updateEntity(String id,T obj,Class entityClass) throws Exception {
        Map<String,Object> fieldMap = BeanUtil.beanToMap(obj);

        if(CollectionUtil.isEmpty(fieldMap)){
            LOGGER.error("can not update entity: filedMap is Empty");
            return false;
        }

        //:::按照约定,获得表名
        String tableName = getTableName(entityClass);
        //:::构造update语句开头
        String sql = "update " + tableName + " set ";

        //:::sql列的部分
        StringBuilder columns = new StringBuilder();
        //:::参数部分
        List<Object> params = new ArrayList<>();

        //:::构造update语句主体
        for(String fieldName : fieldMap.keySet()){
            Object value = fieldMap.get(fieldName);

            //:::如果字段不为空,才加入sql
            if(value != null){
                params.add(value);
                columns.append(fieldName).append("=?, ");
            }
        }

        //:::将列的最后一个逗号删除
        columns.replace(columns.lastIndexOf(", "),columns.length(),"");
        //:::加上查询条件 (查询条件默认为主键id)
        columns.append(" where id = ?");
        //:::'?'参数最后加上查询条件id
        params.add(id);

        //:::将sql字符串拼接
        sql += columns;

        return executeUpdate(sql,params.toArray()) == 1;
    }

    /**
     * 执行sql文件
     * */
    public static void executeSqlFile(String filePath){
        InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream(filePath);

        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(is));

        try {
            String sql;
            while((sql = bufferedReader.readLine()) != null){
                //:::执行sql语句
                executeUpdate(sql);
            }
        } catch (IOException e) {
            LOGGER.error("execute sql file fail",e);
            throw new RuntimeException(e);
        }
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