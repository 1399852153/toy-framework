package com.xiongyx.util;

import com.xiongyx.parsing.MapperXmlParseHelper;
import com.xiongyx.model.MappedStatement;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * @author xiongyx
 * on 2019/7/13.
 *
 * Mapper-xml 扫描工具类
 */
public class MapperXmlScanUtil {

    /**
     * 扫描并解析当前URL下所有的 mapper-xml文件
     * @param url 一般是文件夹
     * */
    public static void scanMapperXml(URL url){
        // file(文件)类型
        String packagePath = url.getPath().replace("%20"," ");

        // 解析当前目录下所有mapper-xml
        parseMapperXml(new File(packagePath));
    }

    /**
     * 扫描并解析当前mapper-xml文件
     * */
    public static void parseMapperXmlInFile(URL xmlFileUrl){
        // file(文件)类型
        String xmlFileUrlPath = xmlFileUrl.getPath().replace("%20"," ");

        // 解析当前mapper-xml
        MapperXmlParseHelper.parseMapperXml(new File(xmlFileUrlPath));
    }

    //======================================私有方法==============================================

    private static void parseMapperXml(File file){
        File[] files = file.listFiles(
                item-> item.isFile() && item.getName().endsWith(".xml") || item.isDirectory()
        );

        if(files == null){
            return;
        }

        for(File fileItem : files){
            if(fileItem.isFile()){
                // 从文件中解析出 mapper映射数据
                MapperXmlParseHelper.parseMapperXml(fileItem);
            }else{
                // 递归解析
                parseMapperXml(fileItem);
            }
        }
    }
}
