package test.demo;

import test.demo.dao.UserDAO;
import test.demo.model.Book;
import test.demo.model.User;
import com.xiongyx.session.SqlSession;
import com.xiongyx.session.SqlSessionFactory;
import com.xiongyx.session.SqlSessionFactoryBuilder;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.Arrays;
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
//        Configuration configuration = new Configuration();
        InputStream inputStream = ApplicationMain.class.getClassLoader().getResourceAsStream("mybatis-config.xml");
        Reader reader = new InputStreamReader(inputStream);
        SqlSessionFactory sqlSessionFactory = SqlSessionFactoryBuilder.build(reader);
        SqlSession sqlSession = sqlSessionFactory.getSession();

        UserDAO mapper = sqlSession.getMapper(UserDAO.class);
//        Map<String,Object> param = new HashMap<>();
//        param.put("id","123");
//        param.put("age",12);
//        param.put("money",32141);
//        User param = new User();
//        param.setId("123");
//        param.setAge(12);
//        param.setMoney(32141d);
//        param.setIdListQuery(Arrays.asList("aaaaa","bbbbb","ccccc"));
//
//        List<User> userList = sqlSession.selectList("test.demo.dao.UserDAO.getUserList",param);

        // TODO mapper 传实体做参数
//        List<User> userList = mapper.getUserList(param);

//        System.out.println(userList);

        Book param = new Book();
        param.setBookName("小老弟");
        List<Book> bookList = sqlSession.selectList("test.dao.BookMapper.getBook",param);
        System.out.println(bookList);
    }
}
