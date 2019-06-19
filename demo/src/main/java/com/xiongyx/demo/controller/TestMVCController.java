//package com.xiongyx.demo.controller;
//
//import com.alibaba.fastjson.JSONObject;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import javax.servlet.http.HttpServletRequest;
//import java.io.BufferedReader;
//import java.io.IOException;
//import java.io.InputStreamReader;
//
///**
// * @author xiongyx
// * @date 2019/6/19
// */
//@Slf4j
//@RestController
//public class TestMVCController {
//
//    @RequestMapping("/testMVC")
//    public String testMVC(HttpServletRequest request) throws IOException {
//
//        BufferedReader streamReader = new BufferedReader( new InputStreamReader(request.getInputStream(), "UTF-8"));
//        StringBuilder responseStrBuilder = new StringBuilder();
//        String inputStr;
//        while ((inputStr = streamReader.readLine()) != null){
//            responseStrBuilder.append(inputStr);
//        }
//        JSONObject jsonObject = JSONObject.parseObject(responseStrBuilder.toString());
//        String param = jsonObject.toJSONString();
//
////        log.info("depositUserPassword={}, depositTransactionRecord={}",depositUserPassword);
////        log.info("depositTransactionRecord={}",depositTransactionRecord);
//        return "ok";
//    }
//}
