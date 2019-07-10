package com.xiongyx.helper;

import com.xiongyx.constant.Constant;
import com.xiongyx.model.Configuration;
import com.xiongyx.util.XmlUtil;
import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.Element;

import java.io.File;
import java.io.Reader;

/**
 * @author xiongyx
 * @date 2019/7/10
 *
 * config配置xml文件 解析器
 */
public class ConfigXmlParseHelper {

    private static final Logger logger = Logger.getLogger(ConfigXmlParseHelper.class);

    private static final String ROOT_ELEMENT = "configuration";


    public static Configuration parseMapperXml(Reader reader){
        Document document = XmlUtil.readXml(reader);
        // 获取xml中的根元素
        Element rootElement = document.getRootElement();
        // 根元素是否匹配
        if (!ROOT_ELEMENT.equals(rootElement.getName())) {
            logger.info("config-xml文件根元素不是" + ROOT_ELEMENT);
        }

        // todo 解析environment 目前只有dataSource配置
        Element environment = rootElement.element("environments");

//        rootElement.

        return null;
    }
}
