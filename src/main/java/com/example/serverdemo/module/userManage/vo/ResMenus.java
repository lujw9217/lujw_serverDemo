package com.example.serverdemo.module.userManage.vo;

import java.util.List;

/**
 * 菜单响应对象
 */
public class ResMenus {
    private String userName;

    //菜单集合
    private List<MenuVo> menuVos ;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public List<MenuVo> getMenuVos() {
        return menuVos;
    }

    public void setMenuVos(List<MenuVo> menuVos) {
        this.menuVos = menuVos;
    }
}
