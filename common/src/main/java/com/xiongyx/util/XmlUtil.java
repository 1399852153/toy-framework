package com.xiongyx.util;

import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.io.SAXReader;

import java.io.File;
import java.net.URL;

/**
 * Xml工具类
 */
public final class XmlUtil {

    private static Logger logger = Logger.getLogger(XmlUtil.class);

    /**
     * readXml
     */
    public static Document readXmlByFile(File file) {
        try {
            // 创建一个读取器
            SAXReader saxReader = new SAXReader();
            saxReader.setEncoding("UTF-8");

            // 读取文件内容
            return saxReader.read(file);
        } catch (DocumentException e) {
            logger.info("read file xml error",e);
            throw new RuntimeException(e);
        }
    }

    /**
     * readXml
     */
    public static Document readXmlByURL(URL url) {
        try {
            // 创建一个读取器
            SAXReader saxReader = new SAXReader();
            saxReader.setEncoding("UTF-8");

            // 读取文件内容
            return saxReader.read(url);
        } catch (DocumentException e) {
            logger.info("read url xml error",e);
            throw new RuntimeException(e);
        }
    }
}
