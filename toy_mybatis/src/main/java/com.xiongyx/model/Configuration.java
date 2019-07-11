package com.xiongyx.model;

import com.xiongyx.environment.Environment;
import com.xiongyx.helper.MapperXmlParseHelper;
import org.apache.log4j.Logger;

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

    private static final Logger logger = Logger.getLogger(Configuration.class);

    private static Environment environment;

    private static final Map<String,MappedStatement> MAPPED_STATEMENT_MAP = new HashMap<>();

    private static final Map<String,List<MappedStatement>> MAPPED_STATEMENT_FILE_MAP = new HashMap<>();

    static{
        try {
            // 扫描mapper文件夹下所有mapper文件
            Enumeration<URL> urls = Configuration.class.getClassLoader().getResources("mapper");

            while(urls.hasMoreElements()){
                URL url = urls.nextElement();
                scanMapperXml(url);
            }

            MAPPED_STATEMENT_MAP.values().forEach(System.out::println);
            MAPPED_STATEMENT_FILE_MAP.entrySet().forEach(System.out::println);

        } catch (IOException e) {
            logger.info("scan mapper-xml error",e);
            throw new RuntimeException(e);
        }
    }

    public MappedStatement getMappedStatement(String statementID){
        return MAPPED_STATEMENT_MAP.get(statementID);
    }

    private static void scanMapperXml(URL url){
        // file(文件)类型
        String packagePath = url.getPath().replace("%20"," ");

        // 解析当前目录下所有mapper-xml
        parseMapperXml(new File(packagePath));
    }

    private static void parseMapperXml(File file){
        File[] files = file.listFiles(
            item-> item.isFile() && item.getName().endsWith(".xml") || item.isDirectory()
        );

        if(files == null){
            return;
        }

        for(File fileItem : files){
            if(fileItem.isFile()){
                doParseMapperXml(fileItem);
            }else{
                parseMapperXml(fileItem);
            }
        }
    }

    private static void doParseMapperXml(File file){
        List<MappedStatement> mappedStatementList = MapperXmlParseHelper.parseMapperXml(file);
        MAPPED_STATEMENT_FILE_MAP.put(file.getName(),mappedStatementList);
        for(MappedStatement mappedStatement : mappedStatementList){
            MAPPED_STATEMENT_MAP.put(mappedStatement.getSqlId(),mappedStatement);
        }
    }
}
