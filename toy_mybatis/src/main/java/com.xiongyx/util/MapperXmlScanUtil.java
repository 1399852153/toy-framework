package com.xiongyx.util;

import com.xiongyx.helper.MapperXmlParseHelper;
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
    public static List<MappedStatement> scanMapperXml(URL url){
        // file(文件)类型
        String packagePath = url.getPath().replace("%20"," ");

        // 解析当前目录下所有mapper-xml
        return parseMapperXml(new File(packagePath));
    }

    /**
     * 扫描并解析当前mapper-xml文件
     * */
    public static List<MappedStatement> parseMapperXmlInFile(File xmlFile){
        // 解析当前目录下所有mapper-xml
        return MapperXmlParseHelper.parseMapperXml(xmlFile);
    }

    //======================================私有方法==============================================

    private static List<MappedStatement> parseMapperXml(File file){
        List<MappedStatement> collector = new ArrayList<>();

        // 递归扫描 mapper文件
        parseMapperXml(file,collector);

        return collector;
    }

    /**
     * 递归解析
     * */
    private static void parseMapperXml(File file,List<MappedStatement> mappedStatementList){
        File[] files = file.listFiles(
                item-> item.isFile() && item.getName().endsWith(".xml") || item.isDirectory()
        );

        if(files == null){
            return;
        }

        for(File fileItem : files){
            if(fileItem.isFile()){
                // 从文件中解析出 mapper映射数据
                List<MappedStatement> mappedStatementInFile = MapperXmlParseHelper.parseMapperXml(fileItem);

                // 加入mappedStatementList
                mappedStatementList.addAll(mappedStatementInFile);
            }else{
                parseMapperXml(fileItem,mappedStatementList);
            }
        }
    }
}
