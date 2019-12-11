package com.example.serverdemo.module.userManage.service;

import com.example.serverdemo.base.entity.CommonResObject;
import com.example.serverdemo.module.userManage.vo.ManagerUserVo;
import com.example.serverdemo.module.userManage.vo.ResMenus;

/**
 * 菜单 服务接口
 */
public interface IMenuService {

    ResMenus getMenuByUser(ManagerUserVo managerUserVo);
}
