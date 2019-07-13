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

    private Map<String,MappedStatement> mappedStatementMap = new HashMap<>();

    public Environment getEnvironment() {
        return environment;
    }

    public MappedStatement getMappedStatement(String statementID){
        return mappedStatementMap.get(statementID);
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

        public Builder mappedStatementMap(Map<String,MappedStatement> mappedStatementMap){
            target.mappedStatementMap = mappedStatementMap;

            // 打印扫描出的MappedStatement信息
            mappedStatementMap.values().forEach(logger::info);
            return this;
        }

        public Configuration build(){
            return target;
        }
    }
}
