package com.example.serverdemo.module.userManage.po;

import com.example.serverdemo.base.vo.BaseVO;
import com.example.serverdemo.module.userManage.vo.ManagerUserVo;
import com.example.serverdemo.module.userManage.vo.MenuVo;

import java.util.List;

/**
 * 用户列表，菜单列表数据 返回对象
 */
public class UsersAndMenus extends BaseVO {

    //用户集合
    private List<ManagerUserVo> userVos ;

    //菜单集合
    private List<MenuVo> menuVos;

    public List<ManagerUserVo> getUserVos() {
        return userVos;
    }

    public void setUserVos(List<ManagerUserVo> userVos) {
        this.userVos = userVos;
    }

    public List<MenuVo> getMenuVos() {
        return menuVos;
    }

    public void setMenuVos(List<MenuVo> menuVos) {
        this.menuVos = menuVos;
    }
}
