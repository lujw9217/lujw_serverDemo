package com.example.serverdemo.module.userManage.dao;


import com.example.serverdemo.module.userManage.po.ManagerUserPo;

import java.util.List;

/**
 *  用户 dao操作
 */
public interface IUserDao {

    void save(ManagerUserPo userPo);

    void updateLogin(ManagerUserPo user);

    ManagerUserPo findByName(String userName);

    List<ManagerUserPo> findAllUser();

    ManagerUserPo findById(String id);

    List<ManagerUserPo> findAll();

    void deleteUserByPo(ManagerUserPo managerUserPo);
}
