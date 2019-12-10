package com.example.serverdemo.base.util;

import java.io.InputStreamReader;

/**
 * @author : Lujw
 * @Class Name   : PropertiesUtil
 * @Description : 配置文件 操作工具类
 * @Project : serverdemo
 * @Program : com.example.serverdemo.base.util
 * @date : 2019/12/10 16:11
 * @ModificationHistory Who       When        What
 * -----------------------------------------
 * Lujw   2019/12/10      创建
 */
public class PropertiesUtil {
    private java.util.Properties config;

    PropertiesUtil(String fileName) throws Exception {
        config = new java.util.Properties();
        //将配置文件以 字符流的形式加载，解决中文乱码问题。字节流无法读取中文，需把字节流转成字符流Read
        InputStreamReader inFileRead = new InputStreamReader(PropertiesUtil.class.getClassLoader().getResourceAsStream(fileName), "UTF-8");
        config.load(inFileRead);
        inFileRead.close();
    }

    public String getValue(String key) {
        String value = null;
        if (config != null) {
            value = config.getProperty(key);
            if (value != null)
                value = value.trim();
        }
        return value;
    }

    /**
     * 取得config
     * @return the config
     */
    public java.util.Properties getConfig() {
        return config;
    }

    /**
     * 设置config
     * @param config the config to set
     */
    public void setConfig(java.util.Properties config) {
        this.config = config;
    }
}