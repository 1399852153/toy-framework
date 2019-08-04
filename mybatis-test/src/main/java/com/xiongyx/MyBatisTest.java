package com.xiongyx;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.Reader;
import java.util.Arrays;
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

        testUser(session);
//        testBook(session);
    }

    private static void testUser(SqlSession session){
        try {
            User param = new User();
//            param.setId("321312");

            List<User> userList = session.selectList("getUserLinkedQuery", param);
            System.out.println(userList);
//            List<User> userList2 = session.selectList("result_map_test", param);
//            System.out.println(userList2);
            session.commit();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
    }

    private static void testBook(SqlSession session){
        List<Book> bookList = session.selectList("getBook");
        session.commit();
        System.out.println(bookList);
    }
}
