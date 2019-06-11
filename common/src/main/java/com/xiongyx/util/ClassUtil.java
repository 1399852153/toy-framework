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
            Class cls = Class.forName(className,isInitialized,getClassLoader());
            return cls;
        } catch (ClassNotFoundException e) {
            logger.info("loadClass error",e);
            throw new RuntimeException(e);
        }
    }


    public static Set<Class<?>> getClasses(String basePackageName){
        try {
            Set<Class<?>> classSet = new HashSet<>();

            Enumeration<URL> urls = getClassLoader().getResources(basePackageName.replaceAll("\\.","/"));

            while(urls.hasMoreElements()){
                URL url = urls.nextElement();
                if(url != null){
                    String protocol = url.getProtocol();
                    if(protocol.equals("file")){
                        String packagePath = url.getPath().replace("%20"," ");
                        addClass(classSet,packagePath,basePackageName);
                    }else if(protocol.equals("jar")){
                        JarURLConnection jarURLConnection = (JarURLConnection)url.openConnection();
                        if(jarURLConnection != null){
                            JarFile jarFile = jarURLConnection.getJarFile();
                            if(jarFile != null){
                                Enumeration<JarEntry> jarEntryEnumeration = jarFile.entries();
                                while(jarEntryEnumeration.hasMoreElements()){
                                    JarEntry jarEntry = jarEntryEnumeration.nextElement();
                                    String jarEntryName = jarEntry.getName();
                                    if(jarEntryName.endsWith(".class")){
                                        String className = jarEntryName.substring(0,jarEntryName.lastIndexOf("."))
                                            .replace("/",".");
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
        File[] files = new File(packagePath).listFiles(
            file-> file.isFile() && file.getName().endsWith(".class") || file.isDirectory()
        );

        if(files == null){
            return;
        }

        Arrays.stream(files).forEach(file->{
            String fileName = file.getName();
            if(file.isFile()){
                String className = fileName.substring(0,fileName.lastIndexOf("."));
                if(StringUtils.isNotEmpty(packageName)){
                    className = packageName + "." + className;
                }

                doAddClass(classSet,className);
            }else{
                String subPackagePath = fileName;
                if(StringUtils.isNotEmpty(packagePath)){
                    subPackagePath = packagePath + "/" + subPackagePath;
                }

                String subPackageName = fileName;
                if(StringUtils.isNotEmpty(packageName)){
                    subPackageName = packageName + "." + subPackageName;
                }

                addClass(classSet,subPackagePath,subPackageName);
            }
        });
    }

    private static void doAddClass(Set<Class<?>> classSet, String className){
        Class<?> cls = loadClass(className,false);
        classSet.add(cls);
    }
}
