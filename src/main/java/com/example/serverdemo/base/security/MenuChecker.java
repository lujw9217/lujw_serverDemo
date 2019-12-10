package com.example.serverdemo.base.security;

import com.example.serverdemo.base.exception.TopException;
import com.example.serverdemo.base.util.BaseUtil;
import com.example.serverdemo.module.userManage.po.MenuPo;
import com.example.serverdemo.module.userManage.vo.ManagerUserVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.method.HandlerMethod;

import java.util.HashSet;
import java.util.Set;

/**
 * 用于验证菜单权限
 */
public class MenuChecker {
    //logger
    private static final Logger LOGGER = LoggerFactory.getLogger(MenuChecker.class);

   /**
    * @description   : 验证用户用户操作这个方法的 菜单权限
    * @method_name   : check
    * @param         : [managerUserVo, handlerMethod]
    * @return        : void
    * @throws        :
    * @date          : 2019/12/10 17:17
    * @author        : Lujw
    * @update date   :
    * @update author :
    */
    public static void check(ManagerUserVo managerUserVo, HandlerMethod handlerMethod)
            throws TopException {
        if(BaseUtil.objectNotNull(managerUserVo)){
            if(UserRole.ROLE_USER.toString().equals(managerUserVo.getRole())){
                WhichMenu  whichMenu = handlerMethod.getMethodAnnotation(WhichMenu.class);
                if(whichMenu==null){
                    whichMenu = handlerMethod.getMethod().getDeclaringClass().getAnnotation(WhichMenu.class);
                }
                if(whichMenu!=null){
                    Set<MenuPo> MenuPos = managerUserVo.getMenuPos();
                    //Set<MenuRightPo>解析成set<Integer>做比较
                    Set<Integer> menuRight = new HashSet<Integer>();
                    for (MenuPo menuPo : MenuPos) {
                        menuRight.add(menuPo.getMenuCode());
                    }
                    if(!menuRight.contains(whichMenu.value().getCode())){
                        LOGGER.error("<!!--菜单权限检验： 无权限-->");
                        throw new TopException("菜单权限检验", "无权限");
                    }
                }
            }
        }
    }
}
