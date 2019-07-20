package com.xiongyx.session;

import com.xiongyx.builder.XMLConfigBuilder;
import com.xiongyx.model.Configuration;
import com.xiongyx.session.defaults.DefaultSqlSessionFactory;

import java.io.Reader;

/**
 * @author xiongyx
 * on 2019/7/3.
 */
public class SqlSessionFactoryBuilder {

    public static SqlSessionFactory build(Reader reader) {
        XMLConfigBuilder xmlConfigBuilder = new XMLConfigBuilder(reader);
        Configuration configuration = xmlConfigBuilder.parse();
        return build(configuration);
    }

    public static SqlSessionFactory build(Configuration configuration) {
        return new DefaultSqlSessionFactory(configuration);
    }

    public SqlSessionFactory privateBuild(Configuration configuration) {
        return new DefaultSqlSessionFactory(configuration);
    }
}
