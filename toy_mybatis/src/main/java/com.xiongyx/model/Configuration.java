package com.xiongyx.model;

import com.xiongyx.constant.Constant;
import com.xiongyx.helper.MapperParseHelper;
import com.xiongyx.util.XmlUtil;
import org.dom4j.Document;
import org.dom4j.Element;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.*;

/**
 * @author xiongyx
 * @date 2019/6/24
 *
 * mybatis配置
 */
public class Configuration {

    private static Map<String,MappedStatement> mappedStatementMap = new HashMap<>();

    private static Map<String,List<MappedStatement>> mappedStatementFileMap = new HashMap<>();

    static{
        try {
            Enumeration<URL> urls = Configuration.class.getClassLoader().getResources("mapper");

            while(urls.hasMoreElements()){
                URL url = urls.nextElement();
                scanMapperXml(url);
            }

//            mappedStatementMap.values().forEach(System.out::println);
            mappedStatementFileMap.entrySet()
                .forEach(System.out::println);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void scanMapperXml(URL url){
        // file(文件)类型
        String packagePath = url.getPath().replace("%20"," ");

        File[] files = new File(packagePath).listFiles(
            file-> file.isFile() && file.getName().endsWith(".xml")
        );

        if(files == null){
            return;
        }

        for(File file : files){
            List<MappedStatement> mappedStatementList = MapperParseHelper.parseMapperXml(file);
            mappedStatementFileMap.put(file.getName(),mappedStatementList);
            for(MappedStatement mappedStatement : mappedStatementList){
                mappedStatementMap.put(mappedStatement.getSqlId(),mappedStatement);
            }
        }
    }
}
