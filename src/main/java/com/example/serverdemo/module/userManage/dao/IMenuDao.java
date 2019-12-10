package com.example.serverdemo.module.userManage.dao;


import com.example.serverdemo.module.userManage.po.MenuPo;

import java.util.List;

/**
 * 菜单 持久化 接口
 */
public interface IMenuDao {

    /**
     * @param : []
     * @return : List<MenuPo>
     * @throws :
     * @description : 查找菜单集合
     * @method_name : findAll
     * @date : 2019/12/10 17:26
     * @author : Lujw
     * @update date   :
     * @update author :
     */
    List<MenuPo> findAll();

    /**
     * @param : [menuPo]
     * @return : void
     * @throws :
     * @description : 添加一个菜单
     * @method_name : addMenu
     * @date : 2019/12/10 17:26
     * @author : Lujw
     * @update date   :
     * @update author :
     */
    void addMenu(MenuPo menuPo);
}
