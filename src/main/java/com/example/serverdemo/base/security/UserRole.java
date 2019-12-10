package com.example.serverdemo.base.security;

/**
 * 用户权限 枚举类型
 */
public enum  UserRole {

    /*
        any 无需任何权限
        admin 超级管理员
        user 普通用户
    */

    ROLE_ANY(), ROLE_ADMIN(), ROLE_USER(),;
}
