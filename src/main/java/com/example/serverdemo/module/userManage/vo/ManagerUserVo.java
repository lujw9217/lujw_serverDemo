package com.example.serverdemo.module.userManage.vo;

import com.example.serverdemo.base.vo.BaseVO;
import com.example.serverdemo.module.userManage.constant.Menu;
import com.example.serverdemo.module.userManage.po.ManagerUserPo;
import com.example.serverdemo.module.userManage.po.MenuPo;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * 普通用户
 */
public class ManagerUserVo extends BaseVO {

    //当前的登录用户PO
    private ManagerUserPo managerUserPo = new ManagerUserPo();

    //当前用户使用的账号
    private String userName;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setManagerUserPo(ManagerUserPo managerUserPo) {
        this.managerUserPo = managerUserPo;
    }

   /**
    * @description   : 获取主键
    * @method_name   : get_id
    * @param         : []
    * @return        : java.lang.String
    * @throws        :
    * @date          : 2019/12/10 17:12
    * @author        : Lujw
    * @update date   :
    * @update author :
    */
    public String get_id() {
        return managerUserPo.get_id();
    }

    /**
     * @description   : 设置菜单集合
     * @method_name   : setMenuPos
     * @param         : [menuPos]
     * @return        : void
     * @throws        :
     * @date          : 2019/12/10 17:13
     * @author        : Lujw
     * @update date   :
     * @update author :
     */
    public void setMenuPos(Set<MenuPo> menuPos) {
        managerUserPo.setMenuPos(menuPos);
    }

    /**
     * @description   : 获得用户级别
     * @method_name   : getLevel
     * @param         : []
     * @return        : java.lang.String
     * @throws        :
     * @date          : 2019/12/10 17:13
     * @author        : Lujw
     * @update date   :
     * @update author :
     */
    public String getLevel() {
        return managerUserPo.getLevel();
    }

   /**
    * @description   : 设置用户权限
    * @method_name   : setRole
    * @param         : [role]
    * @return        : void
    * @throws        :
    * @date          : 2019/12/10 17:13
    * @author        : Lujw
    * @update date   :
    * @update author :
    */
    public void setRole(String role) {
        managerUserPo.setRole(role);
    }


   /**
    * @description   : 获得用户权限
    * @method_name   : getRole
    * @param         : []
    * @return        : java.lang.String
    * @throws        :
    * @date          : 2019/12/10 17:13
    * @author        : Lujw
    * @update date   :
    * @update author :
    */
    public String getRole() {
        return managerUserPo.getRole();
    }

    /**
     * @description   : 获取用户所拥有的菜单权限
     * @method_name   : getMenuVos
     * @param         : []
     * @return        : java.util.List<com.example.serverdemo.userManage.vo.MenuVo>
     * @throws        :
     * @date          : 2019/12/10 17:13
     * @author        : Lujw
     * @update date   :
     * @update author :
     */
    public List<MenuVo> getMenuVos() {
        //获取用户所用户menu集合
        Set<MenuPo> menuPos = managerUserPo.getMenuPos();
        List<MenuVo> menuVos = new ArrayList<>();
        //循环遍历将po转为vo
        for (MenuPo menuPo : menuPos
        ) {
            MenuVo menuVo = new MenuVo();
            menuVo.setMenuCode(menuPo.getMenuCode());
            //设置菜单名
            menuVo.setMenuName(Menu.getNameByCode(menuPo.getMenuCode()));
            //设置菜单路径
            menuVo.setPath(Menu.getPathByCode(menuPo.getMenuCode()));
            //添加到菜单Vo集合
            menuVos.add(menuVo);
        }
        return menuVos;
    }

    /**
     * @description   : 查询菜单PO对象
     * @method_name   : getMenuPos
     * @param         : []
     * @return        : java.util.Set<com.example.serverdemo.userManage.po.MenuPo>
     * @throws        :
     * @date          : 2019/12/10 17:13
     * @author        : Lujw
     * @update date   :
     * @update author :
     */
    public Set<MenuPo> getMenuPos() {
        return managerUserPo.getMenuPos();
    }

    /**
     * @description   : 得到用户状态
     * @method_name   : getStatus
     * @param         : []
     * @return        : java.lang.String
     * @throws        :
     * @date          : 2019/12/10 17:13
     * @author        : Lujw
     * @update date   :
     * @update author :
     */
    public String getStatus() {
        return managerUserPo.getStatus();
    }

    /**
     * @description   : 获取用户创建时间
     * @method_name   : getCreate_time
     * @param         : []
     * @return        : java.lang.String
     * @throws        :
     * @date          : 2019/12/10 17:14
     * @author        : Lujw
     * @update date   :
     * @update author :
     */
    public String getCreate_time() {
        return managerUserPo.getCreate_time();
    }

   /**
    * @description   : 获得用户最后登录时间
    * @method_name   : getLastLogin_time
    * @param         : []
    * @return        : java.lang.String
    * @throws        :
    * @date          : 2019/12/10 17:15
    * @author        : Lujw
    * @update date   :
    * @update author :
    */
    public String getLastLogin_time() {
        return managerUserPo.getLastLogin_time();
    }

    /**
     * @description   : 获得用户最后更新时间
     * @method_name   : getLastUpdate_time
     * @param         : []
     * @return        : java.lang.String
     * @throws        :
     * @date          : 2019/12/10 17:15
     * @author        : Lujw
     * @update date   :
     * @update author :
     */
    public String getLastUpdate_time() {
        return managerUserPo.getLastUpdate_time();
    }

    /**
     * @description   : 获得用户最后一次登录IP
     * @method_name   : getLastLogin_ip
     * @param         : []
     * @return        : java.lang.String
     * @throws        :
     * @date          : 2019/12/10 17:15
     * @author        : Lujw
     * @update date   :
     * @update author :
     */
    public String getLastLogin_ip() {
        return managerUserPo.getLastLogin_ip();
    }

    /**
     * @description   : 设置最后一次登录时间
     * @method_name   : setLastLogin_time
     * @param         : [lastLogin_time]
     * @return        : void
     * @throws        :
     * @date          : 2019/12/10 17:15
     * @author        : Lujw
     * @update date   :
     * @update author :
     */
    public void setLastLogin_time(String lastLogin_time) {
        managerUserPo.setLastLogin_time(lastLogin_time);
    }

   /**
    * @description   : 设置最后一次IP
    * @method_name   : setLastLogin_ip
    * @param         : [lastLogin_ip]
    * @return        : void
    * @throws        :
    * @date          : 2019/12/10 17:15
    * @author        : Lujw
    * @update date   :
    * @update author :
    */
    public void setLastLogin_ip(String lastLogin_ip) {
        managerUserPo.setLastLogin_ip(lastLogin_ip);
    }

    @Override
    public String toString() {
        return "ManagerUserVo{" +
                "managerUserPo=" + managerUserPo +
                ", userName='" + userName + '\'' +
                '}';
    }
}
