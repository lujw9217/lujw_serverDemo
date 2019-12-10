package com.example.serverdemo.base.monitor.handlers;

import com.example.serverdemo.base.monitor.anotation.Monitor;
import org.aspectj.lang.JoinPoint;
public class DefaultMonitorHandler implements MonitorHandler{

	@Override
	public String parseMethodName(JoinPoint jp, Monitor monitor) {
		// TODO Auto-generated method stub
		return monitor.methodName();
	}

}
