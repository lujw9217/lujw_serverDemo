package com.example.serverdemo.module.userManage.vo;


import com.example.serverdemo.base.vo.BaseVO;

/**
 * 菜单VO 对象
 */
public class MenuVo extends BaseVO {

    //菜单名称
    private String menuName;

    //菜单代码
    private Integer menuCode;

    //菜单路径
    private String path;

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result
                + ((menuCode == null) ? 0 : menuCode.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (!super.equals(obj))
            return false;
        if (getClass() != obj.getClass())
            return false;
        MenuVo other = (MenuVo) obj;
        if (menuCode == null) {
            if (other.menuCode != null)
                return false;
        } else if (!menuCode.equals(other.menuCode))
            return false;
        return true;
    }

    public String getMenuName() {
        return menuName;
    }

    public void setMenuName(String menuName) {
        this.menuName = menuName;
    }

    public Integer getMenuCode() {
        return menuCode;
    }

    public void setMenuCode(Integer menuCode) {
        this.menuCode = menuCode;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}