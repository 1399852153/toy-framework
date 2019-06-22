/**
 * 
 */
package com.xiongyx.util;


import com.xiongyx.constants.Constant;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.File;
import java.util.Iterator;


/**
 * XmlUtil.java
 */
public final class XmlUtil {

    /**
     * readMapperXml
     * 
     * @param fileName
     * @see
     */
    @SuppressWarnings("rawtypes")
    public static void readMapperXml(File fileName) {
        try {
            // 创建一个读取器
            SAXReader saxReader = new SAXReader();
            saxReader.setEncoding(Constant.CHARSET_UTF8);

            // 读取文件内容
            Document document = saxReader.read(fileName);

            // 获取xml中的根元素
            Element rootElement = document.getRootElement();

            // 不是beans根元素的，文件不对
            if (!Constant.XML_ROOT_LABEL.equals(rootElement.getName())) {
                System.err.println("mapper xml文件根元素不是mapper");
                return;
            }

            String namespace = rootElement.attributeValue(Constant.XML_SELECT_NAMESPACE);

            for (Iterator iterator = rootElement.elementIterator(); iterator.hasNext(); ) {
                Element element = (Element) iterator.next();
                String eleName = element.getName();
            }
        } catch (DocumentException e) {
            e.printStackTrace();
        }

    }
}
