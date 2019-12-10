package com.example.serverdemo.module.userManage.service;

import com.example.serverdemo.base.entity.CommonResObject;
import com.example.serverdemo.module.userManage.vo.ManagerUserVo;

/**
 * 菜单 服务接口
 */
public interface IMenuService {

    CommonResObject getMenuByUser(ManagerUserVo managerUserVo);
}
