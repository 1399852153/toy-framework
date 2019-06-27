package com.xiongyx.demo;

import com.xiongyx.model.Configuration;
import com.xiongyx.session.SqlSessionFactory;
import com.xiongyx.session.defaults.DefaultSqlSession;
import com.xiongyx.session.defaults.DefaultSqlSessionFactory;
import com.xiongyx.util.ClassUtil;
import com.xiongyx.util.XmlUtil;

import java.io.File;
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

        Configuration configuration = new Configuration();
        List<Object> list = new DefaultSqlSession(configuration).selectList("test.dao.UserMapper.getAll",new Object());
        System.out.println(list);
    }
}
