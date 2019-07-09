package com.xiongyx.builder;

import com.xiongyx.model.Configuration;

import java.io.Reader;
import java.util.Properties;

/**
 * @author xiongyx
 * on 2019/7/9.
 */
public class XMLConfigBuilder {
    private Reader reader;
    private Properties properties;

    public XMLConfigBuilder(Reader reader) {
        this.reader = reader;
    }

    public XMLConfigBuilder(Reader reader, Properties properties) {
        this.reader = reader;
        this.properties = properties;
    }

    public Configuration parse(){
        return null;
    }
}
