package com.example.serverdemo.module.userManage.po;

import com.example.serverdemo.base.po.BasePO;
import org.springframework.data.annotation.Id;

/**
 * 菜单权限PO
 */
public class MenuPo extends BasePO {

    @Id
    private String _id;

    private Integer menuCode;

    public Integer getMenuCode() {
        return menuCode;
    }

    public void setMenuCode(Integer menuCode) {
        this.menuCode = menuCode;
    }

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
        MenuPo other = (MenuPo) obj;
        if (menuCode == null) {
            if (other.menuCode != null)
                return false;
        } else if (!menuCode.equals(other.menuCode))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "MenuPo{" +
                "menuCode=" + menuCode +
                '}';
    }
}
