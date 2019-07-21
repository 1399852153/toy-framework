package com.xiongyx.test;


import ognl.Ognl;
import ognl.OgnlException;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author xiongyx
 * on 2019/7/21.
 */
public class OgnlTest {

    private static class User {
        private String id;

        private String userName;

        private int age;

        private double money;

        private List<String> idListQuery;


        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public int getAge() {
            return age;
        }

        public void setAge(int age) {
            this.age = age;
        }

        public double getMoney() {
            return money;
        }

        public void setMoney(double money) {
            this.money = money;
        }

        public List<String> getIdListQuery() {
            return idListQuery;
        }

        public void setIdListQuery(List<String> idListQuery) {
            this.idListQuery = idListQuery;
        }

        @Override
        public String toString() {
            return "User{" +
                    "id='" + id + '\'' +
                    ", userName='" + userName + '\'' +
                    ", age=" + age +
                    ", money=" + money +
                    ", idListQuery=" + idListQuery +
                    '}';
        }
    }

    public static void main(String[] args) throws OgnlException {
        User user = new User();
        user.setId("123");
        user.setAge(12);
        user.setMoney(32141);
        user.setIdListQuery(Arrays.asList("123","321"));

        Map<String,Object> map = new HashMap<>();
        map.put("_parameter",user);
//        map.put("idListQuery",Arrays.asList("123","321"));

        Object value = Ognl.getValue(Ognl.parseExpression("idListQuery"),map);
        System.out.println(value);
        Object value2 = Ognl.getValue(Ognl.parseExpression("idListQuery"),map);
        System.out.println(value2);

        //创建一个Ognl上下文对象
//        OgnlContext context = new OgnlContext(map);
//        context.

        /**
         * 1.OgnlContext放入基本变量数据
         */
//        //放入数据
//        context.put("cn","China");
//        //获取数据（map）
//        String value = (String)context.get("cn");
//
//        System.out.println(value);


//        /**
//         * 2.OgnlContext放入对象数据
//         */
//        //创建对象，设置对象属性
//        User user = new User();
//        user.setId(100);
//        user.setName("Jack");
//        //【往非根元素放入数据，取值的时候表达式要用“#”】
//        context.put("user",user);
//        //获取对象属性
//        //使用这种方式也可以获取
//        Object s = context.get("user");
//        System.out.println(s);

//
//        //使用Ognl表达式来获取
//        //举例：例如标签<s:a value="#user.id">取值，实际上就是运行了下面的代码获取的
//        //先构建一个Ognl表达式，再解析表达式
//        Object ognl = Ognl.parseExpression("#user.id");//构建Ognl表达式
//        Object value1 = Ognl.getValue(ognl, context, context.getRoot());//解析表达式
//        System.out.println(value1);


//        User user1 = new User();
//        user1.setId(100);
//        user1.setName("Jack");
//        context.setRoot(user1);
//        Object ognl1 = Ognl.parseExpression("id");//构建Ognl表达式
//        Object value2 = Ognl.getValue(ognl1, context, context.getRoot());//解析表达式
//        System.out.println(value2);

    }
}
