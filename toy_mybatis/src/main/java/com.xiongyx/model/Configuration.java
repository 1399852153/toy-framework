package com.xiongyx.model;

import com.xiongyx.mapping.Environment;
import com.xiongyx.mapping.ResultMap;
import org.apache.log4j.Logger;

import java.util.*;

/**
 * @author xiongyx
 * @date 2019/6/24
 *
 * mybatis配置
 */
public class Configuration {

    private static final Logger logger = Logger.getLogger(Configuration.class);

    private static final Configuration CONFIGURATION_HOLDER = new Configuration();

    private Environment environment;

    /**
     * mappedStatement map集合
     * */
    private Map<String,MappedStatement> mappedStatementMap = new HashMap<>();

    /**
     * resultMap map集合
     * */
    protected final Map<String, ResultMap> resultMaps = new HashMap<>();

    public MappedStatement addMappedStatement(String mapperStatementKey, MappedStatement mappedStatement){
        logger.info("mappedStatement=" + mappedStatement);
        return mappedStatementMap.put(mapperStatementKey,mappedStatement);
    }

    public ResultMap addResultMap(String resultMapKey, ResultMap resultMap){
        logger.info("resultMap=" + resultMap);
        return resultMaps.put(resultMapKey,resultMap);
    }

    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }

    public Environment getEnvironment() {
        return environment;
    }

    public MappedStatement getMappedStatement(String statementID, boolean mustExist){
        MappedStatement mappedStatement = mappedStatementMap.get(statementID);

        if(mappedStatement == null && mustExist){
            throw new RuntimeException("mappedStatement not exist statementID=" + statementID);
        }

        return mappedStatement;
    }

    public ResultMap getResultMap(String resultMapID, boolean mustExist){
        ResultMap resultMap = resultMaps.get(resultMapID);

        if(resultMap == null && mustExist){
            throw new RuntimeException("resultMap not exist resultMapID=" + resultMapID);
        }

        return resultMap;
    }

    public static Configuration getInstance(){
        return CONFIGURATION_HOLDER;
    }
}
