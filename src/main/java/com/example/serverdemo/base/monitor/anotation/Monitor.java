package com.example.serverdemo.base.monitor.anotation;

import com.example.serverdemo.base.monitor.handlers.DefaultMonitorHandler;
import com.example.serverdemo.base.monitor.handlers.MonitorHandler;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 *  日志监控开关注解，注有次注解的方法才会被监控
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Monitor {

    // 具体调用的接口方法
	String methodName() default "";
	
	//调用类型类型的 如接口用interface表示,默认类型为接口类型
	String typeName() default "Interface";
	
    // 接口类型前缀，用于标识所属接口的提供方，最终组成typeName = prefix+typeName
	String prefix() default "";

	//接口方法名处理类，用于处理多个不同接口使用同一方式调用的情况
	Class<? extends MonitorHandler> handlerClass() default DefaultMonitorHandler.class;
}
