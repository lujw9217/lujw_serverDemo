package com.example.serverdemo.base.util;

import java.util.HashMap;
import java.util.Map;

/**
 * @author : Lujw
 * @Class Name   : PropertiesFactory
 * @Description : 配置文件工厂类
 * @Project : serverdemo
 * @Program : com.example.serverdemo.base.util
 * @date : 2019/12/10 16:10
 * @ModificationHistory Who       When        What
 * -----------------------------------------
 * Lujw   2019/12/10      创建
 */
public class PropertiesFactory {
    private Map<String, PropertiesUtil> propMap;

    private PropertiesFactory() {
        propMap = new HashMap<String, PropertiesUtil>();
    }

    private static class SingletonHolder {
        /**实例化一个 对象**/
        public static final PropertiesFactory INSTANCE = new PropertiesFactory();
    }

    public static PropertiesFactory getInstance() {
        return SingletonHolder.INSTANCE;
    }

    public PropertiesUtil createProperties(String fileName) throws Exception {
        PropertiesUtil config = (PropertiesUtil) propMap.get(fileName);
        if (config == null) {
            config = new PropertiesUtil(fileName);
            propMap.put(fileName, config);
        }
        return config;
    }
}