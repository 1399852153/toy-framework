package com.xiongyx;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.Reader;
import java.util.List;

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
            User param = new User();
//            param.setId("123");
            param.setAge(12);
//            param.setMoney(32141);
            List<User> userList = session.selectList("getUser", param);
            session.commit();
            System.out.println(userList);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
    }
}
