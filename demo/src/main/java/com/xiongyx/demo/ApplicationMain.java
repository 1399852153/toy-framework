package com.xiongyx.demo;

import com.xiongyx.model.Configuration;
import com.xiongyx.session.SqlSession;
import com.xiongyx.session.SqlSessionFactory;
import com.xiongyx.session.SqlSessionFactoryBuilder;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
//        Configuration configuration = new Configuration();
        InputStream inputStream = ApplicationMain.class.getClassLoader().getResourceAsStream("mybatis-config.xml");
        Reader reader = new InputStreamReader(inputStream);
        SqlSessionFactory sqlSessionFactory = SqlSessionFactoryBuilder.build(reader);
        SqlSession sqlSession = sqlSessionFactory.getSession();

//        User user = new User();
//        user.setId("123");
//        user.setAge(12);
//        user.setMoney(32141);
//        List<Object> list = sqlSession.selectList("test.dao.UserMapper.getUser",user);
//        System.out.println(list);

        Map<String,Object> param = new HashMap<>();
        param.put("id","123");
        param.put("age",12);
        param.put("money",32141);
        List<Object> list = sqlSession.selectList("test.dao.UserMapper.getUser",param);
        System.out.println(list);
    }
}
