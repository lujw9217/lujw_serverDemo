package com.example.serverdemo;

import com.example.serverdemo.base.listener.SessionListener;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;

import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;

/**
 * spring boot 启动 类
 */
@Configuration//配置控制
@ComponentScan   //组件扫描
@EnableAutoConfiguration //启用自动配置
//微服务
//@EnableFeignClients
//开启断路器功能
//@EnableCircuitBreaker
//注册服务到eureka
//@EnableDiscoveryClient
//@SpringBootApplication
public class ServerdemoApplication extends SpringBootServletInitializer {
    private static Class<ServerdemoApplication> applicationClass = ServerdemoApplication.class;

    /**
     * spring boot 标准启动入口
     */
    public static void main(String[] args) {
//        SpringApplication.run(ServerdemoApplication.class, args);
        SpringApplication springApplication = new SpringApplication(applicationClass);
        //此处可以进行初始化相关操作
//    	springApplication.addListeners(new InitSysCoifig());
        springApplication.run(args);
    }

    /**
     * spring boot 在第三方容器启动，打成war包，在tomcat中单独使用服务
     * 使用eclipse+tomcat 方式，时，将web-inf下web.xml，将项目add到tomcat7以上中，启动即可
     */
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        //初始化调试服务基本配置
//    	application.application().addListeners(new InitSysCoifig());
        return application.sources(applicationClass);
    }

    /**
     * @param : [servletContext]
     * @return : void
     * @throws :
     * @description : session监听器
     * @method_name : onStartup
     * @date : 2019/12/10 20:59
     * @author : Lujw
     * @update date   :
     * @update author :
     */
    @Override
    public void onStartup(ServletContext servletContext)
            throws ServletException {
        // TODO Auto-generated method stub
        super.onStartup(servletContext);
        //    	servletContext.addListener(new InitSysCoifig());
        //应用启动时添加session监听器对session进行监听
        servletContext.addListener(new SessionListener());
    }
}
