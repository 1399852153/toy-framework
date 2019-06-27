package com.xiongyx.executor;

import com.xiongyx.executor.parameter.DefaultParameterHandler;
import com.xiongyx.executor.parameter.ParameterHandler;
import com.xiongyx.executor.resultset.DefaultResultSetHandler;
import com.xiongyx.executor.resultset.ResultSetHandler;
import com.xiongyx.executor.statement.SimpleStatementHandler;
import com.xiongyx.executor.statement.StatementHandler;
import com.xiongyx.helper.DataBaseHelper;
import com.xiongyx.model.Configuration;
import com.xiongyx.model.MappedStatement;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * @author xiongyx
 * on 2019/6/26.
 */
public class SimpleExecutor implements Executor{

    private Configuration conf;

    public SimpleExecutor(Configuration conf) {
        this.conf = conf;
    }

    @Override
    public <E> List<E> doQuery(MappedStatement mappedStatement, Object parameter) {
        try {
            // 获得数据库连接
            Connection connection = DataBaseHelper.getConnection();

            // 实例化StatementHandler对象，
            StatementHandler statementHandler = new SimpleStatementHandler(mappedStatement);

            // 通过StatementHandler和connection获取PreparedStatement
            PreparedStatement preparedStatement = statementHandler.prepare(connection);

            // 实例化ParameterHandler，将SQL语句中？参数化
            ParameterHandler parameterHandler = new DefaultParameterHandler(parameter);
            parameterHandler.setParameters(preparedStatement);

            // 执行SQL，得到结果集ResultSet
            ResultSet resultSet = statementHandler.query(preparedStatement);

            //7.实例化ResultSetHandler，通过反射将ResultSet中结果设置到目标resultType对象中
            ResultSetHandler resultSetHandler = new DefaultResultSetHandler(mappedStatement);
            return resultSetHandler.handleResultSets(resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("doQuery error",e);
        }
    }

    @Override
    public void doUpdate(MappedStatement mappedStatement, Object parameter) {

    }
}
