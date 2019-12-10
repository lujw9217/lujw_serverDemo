package com.example.serverdemo.module.userManage.dao.impl;

import com.example.serverdemo.module.userManage.dao.IMenuDao;
import com.example.serverdemo.module.userManage.po.MenuPo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * 菜单 dao 实现类
 */
@Repository
public class MenuDaoImpl implements IMenuDao {

    //注入MongoDB操作模板
    @Autowired
    private MongoTemplate mongoTemplate;
    
    /**
     * @description   : 获得所有菜单
     * @method_name   : findAll
     * @param         : []
     * @return        : List<MenuPo>
     * @throws        :
     * @date          : 2019/12/10 17:23
     * @author        : Lujw
     * @update date   :
     * @update author :
     */
    @Override
    public List<MenuPo> findAll() {
        return mongoTemplate.findAll(MenuPo.class);
    }

   /**
    * @description   : 添加一个菜单
    * @method_name   : addMenu
    * @param         : [menuPo]
    * @return        : void
    * @throws        :
    * @date          : 2019/12/10 17:23
    * @author        : Lujw
    * @update date   :
    * @update author :
    */
    @Override
    public void addMenu(MenuPo menuPo) {
        mongoTemplate.save(menuPo);
    }
}
