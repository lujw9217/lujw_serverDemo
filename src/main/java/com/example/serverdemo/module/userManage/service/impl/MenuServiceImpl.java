package com.example.serverdemo.module.userManage.service.impl;

import com.example.serverdemo.base.entity.CommonResObject;
import com.example.serverdemo.base.security.MenuChecker;
import com.example.serverdemo.module.userManage.service.IMenuService;
import com.example.serverdemo.module.userManage.vo.ManagerUserVo;
import com.example.serverdemo.module.userManage.vo.ResMenus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * 服务接口实现类
 */
@Service("menuServiceImpl")
public class MenuServiceImpl implements IMenuService {
    //logger
    private static final Logger LOGGER = LoggerFactory.getLogger(MenuChecker.class);
    /**
     * @description   : 获取用户所拥有的权限
     * @method_name   : getMenuByUser
     * @param         : [managerUserVo]
     * @return        : CommonResObject
     * @throws        :
     * @date          : 2019/12/10 17:28
     * @author        : Lujw
     * @update date   :
     * @update author :
     */
    @Override
    public ResMenus getMenuByUser(ManagerUserVo managerUserVo) {
        //设置菜单响应对象
        ResMenus resMenus = new ResMenus();
        resMenus.setUserName(managerUserVo.getUserName());
        resMenus.setMenuVos(managerUserVo.getMenuVos());
        return resMenus;
    }
}
