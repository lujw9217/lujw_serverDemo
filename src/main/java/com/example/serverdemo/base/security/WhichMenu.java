package com.example.serverdemo.base.security;

import com.example.serverdemo.module.userManage.constant.Menu;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 菜单注解，标注该方法属于前端哪个菜单，作用于controller方法上，方法优先
 */
@Target({ElementType.METHOD,ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface WhichMenu
{
    Menu value();
}
