package com.xiongyx.model;

import com.xiongyx.environment.Environment;
import com.xiongyx.helper.MapperXmlParseHelper;
import com.xiongyx.util.MapperXmlScanUtil;
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

    private Environment environment;

    private static final Map<String,MappedStatement> MAPPED_STATEMENT_MAP = new HashMap<>();

    static{
        try {
            // 扫描mapper文件夹下所有mapper文件
            Enumeration<URL> urls = Configuration.class.getClassLoader().getResources("mapper");

            while(urls.hasMoreElements()){
                URL url = urls.nextElement();
                List<MappedStatement> mappedStatementList = MapperXmlScanUtil.scanMapperXml(url);

                for(MappedStatement mappedStatement : mappedStatementList){
                    MappedStatement old = MAPPED_STATEMENT_MAP.put(mappedStatement.getSqlId(),mappedStatement);
                    if(old != null){
                        // 存在相同的sqlId相同的mappedStatement
                        throw new RuntimeException("has same sqlId =>" + old.getSqlId());
                    }
                }
            }

            // 打印扫描出来的sql单元
            MAPPED_STATEMENT_MAP.values().forEach(System.out::println);

        } catch (IOException e) {
            logger.info("scan mapper-xml error",e);
            throw new RuntimeException(e);
        }
    }

    public Environment getEnvironment() {
        return environment;
    }

    public MappedStatement getMappedStatement(String statementID){
        return MAPPED_STATEMENT_MAP.get(statementID);
    }

    //==================================builder==============================

    public static class Builder{
        private Configuration target;

        public Builder() {
            target = new Configuration();
        }

        public Builder environment(Environment environment){
            target.environment = environment;
            return this;
        }

        public Configuration build(){
            return target;
        }
    }
}
