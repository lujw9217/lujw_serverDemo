package com.example.serverdemo.initLoad;

import com.example.serverdemo.base.exception.TopException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * @author : Lujw
 * @Class Name   : InitSysCoifig
 * @Description : 容器启动时初始化
 * @Project : serverdemo
 * @Program : com.example.serverdemo.initLoad
 * @date : 2019/12/10 16:37
 * @ModificationHistory Who       When        What
 * -----------------------------------------
 * Lujw   2019/12/10      创建
 */
@Order(1)
@Component
public class InitSysCoifig implements CommandLineRunner {
    private static Logger LOGGER = LoggerFactory.getLogger(InitSysCoifig.class);

    //系统启动加载的配置
    @Value("${spring.profiles.active}")
    private String active;

    @Override
    public void run(String... args) throws TopException {

        LOGGER.info("系统配置开始加载" + active);
        /**
         * 初始化系统配置留空
         */
        LOGGER.info("系统配置加载成功");
    }
}