/**
 * 
 */
package com.xiongyx.executor.statement;


import com.xiongyx.model.MappedStatement;
import com.xiongyx.pattern.Patterns;
import org.apache.commons.lang3.StringUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.regex.Matcher;


/**
 * Statement处理类
 * 
 * @author PLF
 * @date 2019年3月6日
 */
public class SimpleStatementHandler implements StatementHandler {


    private MappedStatement mappedStatement;

    public static void main(String[] args) {
        String source = "select * from user where id=#{id}, age=#{age}, money=#{money}";
        Matcher matcher = Patterns.param_pattern.matcher(source);

        while(matcher.find()){
            String str = matcher.group();
            System.out.println(str);
        }
    }

    /**
     * 默认构造方法
     * 
     * @param mappedStatement
     */
    public SimpleStatementHandler(MappedStatement mappedStatement)
    {
        this.mappedStatement = mappedStatement;
    }

    /**
     * prepare
     *
     * @param paramConnection
     * @return
     * @throws SQLException
     */
    @Override
    public PreparedStatement prepare(Connection paramConnection) throws SQLException {
        String originalSql = mappedStatement.getSqlSource();

        if (StringUtils.isNotEmpty(originalSql)) {
            // 替换#{}，预处理，防止SQL注入
            String sql = parseSymbol(originalSql);
            return paramConnection.prepareStatement(sql);
        }
        else {
            throw new SQLException("original sql is null.");
        }
    }

    /**
     * query
     *
     * @param preparedStatement
     * @return
     * @throws SQLException
     */
    @Override
    public ResultSet query(PreparedStatement preparedStatement) throws SQLException {
        return preparedStatement.executeQuery();
    }

    /**
     * update
     *
     * @param preparedStatement
     * @throws SQLException
     */
    @Override
    public void update(PreparedStatement preparedStatement) throws SQLException {
        preparedStatement.executeUpdate();
    }

    /**
     * 将SQL语句中的#{}替换为？，源码中是在SqlSourceBuilder类中解析的
     * 
     * @param source
     * @return
     */
    private static String parseSymbol(String source) {
        source = source.trim();
        Matcher matcher = Patterns.param_pattern.matcher(source);
        return matcher.replaceAll("?");
    }

}
