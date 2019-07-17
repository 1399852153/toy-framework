package com.xiongyx.parsing;

import com.xiongyx.constants.ConfigConstants;
import com.xiongyx.util.PropsUtil;

import java.util.Properties;

/**
 * @Author xiongyx
 * on 2018/6/7.
 */
public class ConfigHelper {
    private static final Properties CONFIG_PROPS = PropsUtil.loadProps(ConfigConstants.CONFIG_FILE);

    /**
     * 获得app基础包名
     * */
    public static String getAppBasePackage(){
        return PropsUtil.getString(CONFIG_PROPS,ConfigConstants.APP_BASE_PACKAGE);
    }
}
