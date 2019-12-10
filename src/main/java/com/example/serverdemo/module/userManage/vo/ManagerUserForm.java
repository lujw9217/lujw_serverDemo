package com.example.serverdemo.module.userManage.vo;

public class ManagerUserForm {
    //用户id
    private String _id;
    //用户名
    private String userName;
    //用户密码
    private String password;
    //用户角色
    private String role;
    //用户级别
    private String level;
    //拥有菜单权限
    private Integer[] menuPos;


    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public Integer[] getMenuPos() {
        return menuPos;
    }

    public void setMenuPos(Integer[] menuPos) {
        this.menuPos = menuPos;
    }
}
