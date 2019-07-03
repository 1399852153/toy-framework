package com.xiongyx;

import com.xiongyx.model.Configuration;
import com.xiongyx.session.SqlSession;
import com.xiongyx.session.SqlSessionFactory;
import com.xiongyx.session.SqlSessionFactoryBuilder;

import java.util.List;

/**
 * @author xiongyx
 * @date 2019/6/21
 */
public class Demo {

    public static void main(String[] args) {
        Configuration configuration = new Configuration();

        SqlSessionFactory sqlSessionFactory = SqlSessionFactoryBuilder.getSqlSessionFactory(configuration);
        SqlSession sqlSession = sqlSessionFactory.getSession();
        List<Object> list = sqlSession.selectList("test.dao.UserMapper.getAll",new Object());
        System.out.println(list);
    }
}
