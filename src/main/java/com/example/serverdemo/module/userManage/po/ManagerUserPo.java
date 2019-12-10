package com.example.serverdemo.module.userManage.po;

import com.example.serverdemo.base.po.BasePO;
import org.springframework.data.annotation.Id;

import java.util.HashSet;
import java.util.Set;

/**
 *  持久化 用户 PO 对象
 */
public class ManagerUserPo extends BasePO {

    //主键
    @Id
    private String _id;
    //用户账号
    private String account;
    //用户密码
    private String password;

    //用户权限
    private String role;

    //用户所拥有的菜单权限
    private Set<MenuPo> menuPos = new HashSet<>();

    //用户级别
    private String level;

    //状态（有值表示被锁定）
    private String status;

    //创建时间  yyyy-MM-dd HH:mm:ss
    private String create_time;

    //最后一次修改时间 yyyy-MM-dd HH:mm:ss
    private String lastUpdate_time;

    //最后一次登录时间 yyyy-MM-dd HH:mm:ss
    private String lastLogin_time;

    //最后一次登录IP
    private String lastLogin_ip;

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public Set<MenuPo> getMenuPos() {
        return menuPos;
    }

    public void setMenuPos(Set<MenuPo> menuPos) {
        this.menuPos = menuPos;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }

    public String getLastUpdate_time() {
        return lastUpdate_time;
    }

    public void setLastUpdate_time(String lastUpdate_time) {
        this.lastUpdate_time = lastUpdate_time;
    }

    public String getLastLogin_time() {
        return lastLogin_time;
    }

    public void setLastLogin_time(String lastLogin_time) {
        this.lastLogin_time = lastLogin_time;
    }

    public String getLastLogin_ip() {
        return lastLogin_ip;
    }

    public void setLastLogin_ip(String lastLogin_ip) {
        this.lastLogin_ip = lastLogin_ip;
    }
}
