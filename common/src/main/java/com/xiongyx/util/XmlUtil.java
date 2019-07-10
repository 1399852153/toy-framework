package com.xiongyx.util;

import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.io.SAXReader;

import java.io.File;
import java.io.Reader;
import java.net.URL;

/**
 * Xml工具类
 */
public final class XmlUtil {

    private static Logger logger = Logger.getLogger(XmlUtil.class);

    private static SAXReader saxReader;

    static {
        // 创建一个读取器
        saxReader = new SAXReader();
        saxReader.setEncoding("UTF-8");
    }

    /**
     * readXml
     */
    public static Document readXml(File file) {
        try {
            // 读取文件内容
            return saxReader.read(file);
        } catch (DocumentException e) {
            logger.info("read error",e);
            throw new RuntimeException(e);
        }
    }

    /**
     * readXml
     */
    public static Document readXml(URL url) {
        try {
            // 读取文件内容
            return saxReader.read(url);
        } catch (DocumentException e) {
            logger.info("read error",e);
            throw new RuntimeException(e);
        }
    }

    /**
     * readXml
     */
    public static Document readXml(Reader reader) {
        try {
            // 读取文件内容
            return saxReader.read(reader);
        } catch (DocumentException e) {
            logger.info("read error",e);
            throw new RuntimeException(e);
        }
    }
}
