package com.xiongyx.util;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.net.JarURLConnection;
import java.net.URL;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Set;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * @author xiongyx
 * @date 2019/6/11
 */
public class ClassUtil {

    private static Logger logger = Logger.getLogger(ClassUtil.class);

    public static ClassLoader getClassLoader(){
        return Thread.currentThread().getContextClassLoader();
    }

    public static Class<?> loadClass(String className, boolean isInitialized){
        try {
            return Class.forName(className,isInitialized,getClassLoader());
        } catch (ClassNotFoundException e) {
            logger.info("loadClass error",e);
            throw new RuntimeException(e);
        }
    }

    /**
     * 扫描基础包下的所有类文件
     * */
    public static Set<Class<?>> scanAllClasses(String basePackageName){
        try {
            Set<Class<?>> classSet = new HashSet<>();
            Enumeration<URL> urls = getClassLoader().getResources(basePackageName.replaceAll("\\.","/"));

            while(urls.hasMoreElements()){
                URL url = urls.nextElement();
                if(url != null){
                    // 获得资源的类型
                    String protocol = url.getProtocol();
                    if(protocol.equals("file")){
                        // file(文件)类型
                        String packagePath = url.getPath().replace("%20"," ");
                        // 进行扫描
                        addClass(classSet,packagePath,basePackageName);
                    }else if(protocol.equals("jar")){
                        // jar类型
                        JarURLConnection jarURLConnection = (JarURLConnection)url.openConnection();
                        if(jarURLConnection != null){
                            JarFile jarFile = jarURLConnection.getJarFile();
                            if(jarFile != null){
                                Enumeration<JarEntry> jarEntryEnumeration = jarFile.entries();
                                while(jarEntryEnumeration.hasMoreElements()){
                                    JarEntry jarEntry = jarEntryEnumeration.nextElement();
                                    String jarEntryName = jarEntry.getName();
                                    // 如果是class结尾的，认为是class文件
                                    if(jarEntryName.endsWith(".class")){
                                        // 转化class名称
                                        String className = jarEntryName.substring(0,jarEntryName.lastIndexOf("."))
                                            .replace("/",".");
                                        // 获取class文件对应的class类
                                        doAddClass(classSet,className);
                                    }
                                }
                            }
                        }
                    }
                }
            }
            return classSet;
        } catch (IOException e) {
            logger.info("get classes error",e);
            throw new RuntimeException(e);
        }
    }

    private static void addClass(Set<Class<?>> classSet, String packagePath, String packageName){
        // 过滤出所有的class文件
        File[] files = new File(packagePath).listFiles(
            file-> file.isFile() && file.getName().endsWith(".class") || file.isDirectory()
        );

        if(files == null){
            return;
        }

        Arrays.stream(files).forEach(file->{
            String fileName = file.getName();
            if(file.isFile()){
                // 是文件
                String className = fileName.substring(0,fileName.lastIndexOf("."));
                if(StringUtils.isNotEmpty(packageName)){
                    className = packageName + "." + className;
                }
                // 获取class文件对应的class类
                doAddClass(classSet,className);
            }else{
                // 是目录

                // 转化子包路径
                String subPackagePath = fileName;
                if(StringUtils.isNotEmpty(packagePath)){
                    subPackagePath = packagePath + "/" + subPackagePath;
                }

                // 转化子包名称
                String subPackageName = fileName;
                if(StringUtils.isNotEmpty(packageName)){
                    subPackageName = packageName + "." + subPackageName;
                }

                // 递归扫描当前目录
                addClass(classSet,subPackagePath,subPackageName);
            }
        });
    }

    /**
     * 读取，并加载类
     * */
    private static void doAddClass(Set<Class<?>> classSet, String className){
        Class<?> cls = loadClass(className,false);
        classSet.add(cls);
    }
}
