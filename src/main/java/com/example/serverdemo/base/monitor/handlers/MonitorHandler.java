package com.example.serverdemo.base.monitor.handlers;

import com.example.serverdemo.base.monitor.anotation.Monitor;
import org.aspectj.lang.JoinPoint;

public interface MonitorHandler {

	String parseMethodName(JoinPoint pjd, Monitor monitor);
}
