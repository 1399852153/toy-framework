package com.xiongyx.helper;

import com.xiongyx.constant.DataBaseConfigConstants;
import com.xiongyx.constants.ConfigConstants;
import com.xiongyx.util.PropsUtil;

import java.util.Properties;

/**
 * @author xiongyx
 * on 2019/6/22.
 */
public class DataBaseConfigHelper {

    private static final Properties CONFIG_PROPS = PropsUtil.loadProps(ConfigConstants.CONFIG_FILE);


    /**
     * 获得数据库 jdbc driver
     * */
    public static String getDBDriver(){
        return PropsUtil.getString(CONFIG_PROPS, DataBaseConfigConstants.DRIVER);
    }

    /**
     * 获得数据库 jdbc url
     * */
    public static String getDBUrl(){
        return PropsUtil.getString(CONFIG_PROPS, DataBaseConfigConstants.URL);
    }

    /**
     * 获得数据库 jdbc user_name
     * */
    public static String getDBUserName(){
        return PropsUtil.getString(CONFIG_PROPS, DataBaseConfigConstants.USERNAME);
    }

    /**
     * 获得数据库 jdbc password
     * */
    public static String getDBPassword(){
        return PropsUtil.getString(CONFIG_PROPS, DataBaseConfigConstants.PASSWORD);
    }
}
