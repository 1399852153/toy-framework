package com.xiongyx.execute;

import com.xiongyx.helper.DataBaseHelper;
import com.xiongyx.model.Configuration;
import com.xiongyx.model.MappedStatement;

import java.net.ConnectException;
import java.sql.Connection;
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
    public <E> List<E> doQuery(MappedStatement ms, Object parameter) {
        // todo 查询数据库

        // 获得数据库连接
        Connection connection = DataBaseHelper.getConnection();



        return null;
    }

    @Override
    public void doUpdate(MappedStatement ms, Object parameter) {

    }
}
