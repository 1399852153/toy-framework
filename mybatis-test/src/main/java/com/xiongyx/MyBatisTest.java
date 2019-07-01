package com.xiongyx;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.Reader;

/**
 * @author xiongyx
 * on 2019/7/1.
 */
public class MyBatisTest {
    public static void main(String[] args) throws IOException {
        Reader reader = Resources.getResourceAsReader("mybatis-config.xml");
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);
        SqlSession session = sqlSessionFactory.openSession();
        try {
            User user = session.selectOne("getUser", "123");
            session.commit();
            System.out.println(user.getUserName());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
    }
}
