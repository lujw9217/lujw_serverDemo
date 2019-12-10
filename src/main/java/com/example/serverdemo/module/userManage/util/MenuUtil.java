package com.example.serverdemo.module.userManage.util;

import com.example.serverdemo.module.userManage.constant.Menu;
import com.example.serverdemo.module.userManage.po.MenuPo;
import com.example.serverdemo.module.userManage.vo.MenuVo;

/**
 * 菜单工具类
 */
public class MenuUtil {

   /**
    * @description   : 获取所有菜单
    * @method_name   : menuPoToMenuVo
    * @param         : [menuPo]
    * @return        : com.example.serverdemo.userManage.vo.MenuVo
    * @throws        :
    * @date          : 2019/12/10 17:54
    * @author        : Lujw
    * @update date   :
    * @update author :
    */
    public static MenuVo menuPoToMenuVo(MenuPo menuPo){
        MenuVo menuVo = new MenuVo();
        //菜单号
        menuVo.setMenuCode(menuPo.getMenuCode());
        //通过菜单号 获取菜单名
        menuVo.setMenuName(Menu.getNameByCode(menuPo.getMenuCode()));
        //通过菜单号 获取路径
        menuVo.setPath(Menu.getPathByCode(menuPo.getMenuCode()));
        return menuVo;
    }

}
