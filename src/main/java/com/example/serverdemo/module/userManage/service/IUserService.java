package com.example.serverdemo.module.userManage.service;

import com.example.serverdemo.base.entity.ResultObject;
import com.example.serverdemo.base.exception.TopException;
import com.example.serverdemo.module.userManage.vo.ManagerUserForm;
import com.example.serverdemo.module.userManage.vo.ManagerUserVo;

/**
 *  用户服务接口
 */
public interface IUserService {

    ManagerUserVo login(String userName, String password, String idCode, String authCode, String ip) throws TopException;

    ResultObject getAllAndMenus();

    ResultObject addUser(ManagerUserForm managerUserForm) throws TopException;

    ResultObject modifyUser(ManagerUserForm managerUserForm) throws TopException;

    ResultObject deleteUser(String id);

    ResultObject modifyPwd(String oldPwd, String newPwd, ManagerUserVo userVo) throws TopException;
}
