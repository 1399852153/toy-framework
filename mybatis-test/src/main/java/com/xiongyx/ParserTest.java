package com.xiongyx;

import org.apache.ibatis.parsing.GenericTokenParser;
import org.apache.ibatis.parsing.TokenHandler;

import java.util.HashMap;
import java.util.Map;

/**
 * @author xiongyx
 * @date 2019/7/16
 */
public class ParserTest {

    public static void main(String[] args) {
        final Map<String,Object> mapper = new HashMap<>();
        mapper.put("name", "张三");
        mapper.put("pwd", "123456");

        //先初始化一个handler
        TokenHandler handler = content -> {
            System.out.println(content);
            return (String) mapper.get(content);
        };
        GenericTokenParser parser = new GenericTokenParser("${", "}", handler);
        System.out.println("************" + parser.parse("用户：${name}，你的密码是:${pwd}"));
    }
}
