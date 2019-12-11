package com.example.serverdemo.module.userManage.service;

import com.example.serverdemo.base.exception.TopException;
import com.example.serverdemo.module.userManage.po.UsersAndMenus;
import com.example.serverdemo.module.userManage.vo.ManagerUserForm;
import com.example.serverdemo.module.userManage.vo.ManagerUserVo;

/**
 *  用户服务接口
 */
public interface IUserService {

    ManagerUserVo login(String userName, String password, String idCode, String authCode, String ip) throws TopException;

    UsersAndMenus getAllAndMenus();

    void addUser(ManagerUserForm managerUserForm) throws TopException;

    void modifyUser(ManagerUserForm managerUserForm) throws TopException;

    void deleteUser(String id);

     void modifyPwd(String oldPwd, String newPwd, ManagerUserVo userVo) throws TopException;
}
