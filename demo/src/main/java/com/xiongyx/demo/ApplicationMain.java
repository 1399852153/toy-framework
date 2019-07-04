package com.xiongyx.demo;

import com.xiongyx.demo.model.User;
import com.xiongyx.model.Configuration;
import com.xiongyx.session.SqlSession;
import com.xiongyx.session.SqlSessionFactory;
import com.xiongyx.session.SqlSessionFactoryBuilder;

import java.util.List;

/**
 * @author xiongyx
 * on 2019/6/12.
 */
public class ApplicationMain {

    public static void main(String[] args) {
        // 初始化框架
//        HelperLoader.init();
//
//        Map<Class<?>,Object> beanMap = BeanFactory.getBeanMap();

//        String path = "/test/251656777/bcd";
//        String pattern = "/test/{abc}/bcd";
//
//        System.out.println(path.startsWith("/"));
//        System.out.println(pattern.startsWith("/"));


//        DataBaseHelper.getConnection();
//
//        File file = new File("D:\\github\\toy-framework\\demo\\src\\main\\resources\\mapper\\UserMapper.xml");
//        XmlUtil.readXml(file);

        mybatisDemo();
    }

    private static void mybatisDemo(){
        // todo 实现sql的对象传参

        Configuration configuration = new Configuration();

        SqlSessionFactory sqlSessionFactory = SqlSessionFactoryBuilder.getSqlSessionFactory(configuration);
        SqlSession sqlSession = sqlSessionFactory.getSession();

        User user = new User();
        List<Object> list = sqlSession.selectList("test.dao.UserMapper.getAll",user);
        System.out.println(list);
    }
}
