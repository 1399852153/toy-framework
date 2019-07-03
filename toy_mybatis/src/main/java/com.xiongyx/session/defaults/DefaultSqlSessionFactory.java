package com.xiongyx.session.defaults;

import com.xiongyx.model.Configuration;
import com.xiongyx.session.SqlSession;
import com.xiongyx.session.SqlSessionFactory;

/**
 * @author xiongyx
 * on 2019/6/24.
 */
public class DefaultSqlSessionFactory implements SqlSessionFactory {

    private Configuration configuration;

    public DefaultSqlSessionFactory(Configuration configuration) {
        this.configuration = configuration;
    }

    @Override
    public SqlSession getSession() {
        return new DefaultSqlSession(configuration);
    }
}
