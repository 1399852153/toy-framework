package com.xiongyx.session;

import com.xiongyx.model.Configuration;
import com.xiongyx.session.defaults.DefaultSqlSessionFactory;

/**
 * @author xiongyx
 * on 2019/7/3.
 */
public class SqlSessionFactoryBuilder {

    public static SqlSessionFactory getSqlSessionFactory(Configuration configuration){
        return new DefaultSqlSessionFactory(configuration);
    }
}
