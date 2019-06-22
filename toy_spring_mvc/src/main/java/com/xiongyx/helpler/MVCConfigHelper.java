package com.xiongyx.helpler;

import com.xiongyx.constants.ConfigConstants;
import com.xiongyx.constant.WebConfigConstants;
import com.xiongyx.util.PropsUtil;

import java.util.Properties;

/**
 * @author xiongyx
 * on 2019/6/16.
 */
public class MVCConfigHelper {

    private static final Properties CONFIG_PROPS = PropsUtil.loadProps(ConfigConstants.CONFIG_FILE);


    /**
     * 获得应用jsp路径
     * */
    public static String getAppJspPath(){
        return PropsUtil.getString(CONFIG_PROPS, WebConfigConstants.APP_JSP_PATH);
    }

    /**
     * 获得应用静态资源路径
     * */
    public static String getAppAssetPath(){
        return PropsUtil.getString(CONFIG_PROPS, WebConfigConstants.APP_ASSET_PATH);
    }
}
