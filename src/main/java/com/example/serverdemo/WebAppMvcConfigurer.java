package com.example.serverdemo;

import com.example.serverdemo.interceptor.UserSecurityInterceptor;
import com.example.serverdemo.timeTask.TimeTask;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import javax.annotation.Resource;
import java.io.File;
import java.util.concurrent.ScheduledThreadPoolExecutor;

/**
 * @author : Lujw
 * @Class Name   : WebAppMvcConfigurer
 * @Description : TODO
 * @Project : serverdemo
 * @Program : com.example.serverdemo
 * @date : 2019/12/10 21:04
 * @ModificationHistory Who       When        What
 * -----------------------------------------
 * Lujw   2019/12/10      创建
 */
public class WebAppMvcConfigurer extends WebMvcConfigurerAdapter {

    private static final Logger LOGGER = LoggerFactory.getLogger(WebAppMvcConfigurer.class);
    //分钟
    private static final long MILLSECONDS_MIN = 60 * 1000 ;

    //小时
    private static final long MILLSECONDS_HOUR = 60 * 60 * 1000 ;

    //年
    private static final long MILLSECONDS_YEAR = 60 * 60 * 1000*24*30*12;

    //秒
    private static final long MILLSECONDS =1000 ;

    @Resource(name = "userSecurityInterceptor")
    private UserSecurityInterceptor userSecurityInterceptor;

    @Resource
    private TimeTask timeTask;


    /**
     * 配置拦截器
     * @author lance
     * @param registry
     */
    public void addInterceptors(InterceptorRegistry registry){
        registry.addInterceptor(userSecurityInterceptor);
    }


    /**
     * @description   : 设置定时任务执行线程数
     * @method_name   : scheduledExecutorService
     * @param         : []
     * @return        : java.util.concurrent.ScheduledThreadPoolExecutor
     * @throws        :
     * @date          : 2019/12/10 21:05
     * @author        : Lujw
     * @update date   :
     * @update author :
     */
    @Bean
    public ScheduledThreadPoolExecutor scheduledExecutorService() {
        ScheduledThreadPoolExecutor executor = new ScheduledThreadPoolExecutor(5);
        return executor;
    }

    /**
     * 定时任务参数说明：
     * fixedDelay：表示 当前任务执行完后，间隔指定时间后载执行下一次任务
     * fixedRate ：表示无论前一次任务是否执行完毕，一到定时任务的执行时间就开始执行下一次
     * initialDelay：表示定时任务[第一次]执行时所延迟执行的时间
     * 以上时间单位为：毫秒
     */


    /**
     *
     * @description   : 服务启动时初始化秘钥
     * @method_name   : initAesKey
     * @param         : []
     * @return        : void
     * @throws        :
     * @date          :
     * @version       : v1.00
     * @author        : Lujw
     * @update date   :
     * @update author :
     */
    @Scheduled(fixedDelay=100*MILLSECONDS_YEAR)
    public void initAesKey(){
        LOGGER.info("<--------- 定时任务启动，开始初始化秘钥 -------->");
        timeTask.initAesKey();
    }

    /**
     * @description   : 每天凌晨2点定时清空指定位置文件
     * @method_name   : deleteFileTimeTask
     * @param         : []
     * @return        : void
     * @throws        :
     * @date          : 2019/12/10 21:06
     * @author        : Lujw
     * @update date   :
     * @update author :
     */
    @Scheduled(cron = "0 0 2 * * ?")
    public void deleteFileTimeTask(){
        LOGGER.info("<--------- 定时任务启动，开始清空指定位置文件 -------->");
        timeTask.deleteFile(new File(""));
        timeTask.deleteFile(new File(""));
    }
}