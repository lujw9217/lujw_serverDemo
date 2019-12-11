package com.example.serverdemo.module.userManage.dao.impl;

import com.example.serverdemo.module.userManage.dao.IUserDao;
import com.example.serverdemo.module.userManage.po.ManagerUserPo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 用户dao层实现类
 */
@Repository("userDao")
public class UserDaoImpl implements IUserDao {

    //用户表
    private static final String COLLECTION_TABLE_USER = "managerUser";
    //注入MongoDBTemplate
    @Autowired
    private MongoTemplate mongoTemplate;


    /**
     * @description   : 保存用户
     * @method_name   : save
     * @param         : [userPo]
     * @return        : void
     * @throws        :
     * @date          : 2019/12/10 17:24
     * @author        : Lujw
     * @update date   :
     * @update author :
     */
    @Override
    public void save(ManagerUserPo userPo) {
        mongoTemplate.save(userPo,COLLECTION_TABLE_USER);
    }

    /**
     * @description   : 修改用户
     * @method_name   : updateLogin
     * @param         : [user]
     * @return        : void
     * @throws        :
     * @date          : 2019/12/10 17:24
     * @author        : Lujw
     * @update date   :
     * @update author :
     */
    @Override
    public void updateLogin(ManagerUserPo user) {
        Query query = new Query(Criteria.where("_id").is(user.get_id()));
        //更新具体参数
        Update update = new Update();
        update.set("lastLogin_time",user.getLastLogin_time());
        update.set("lastLogin_ip",user.getLastLogin_ip());
        update.set("status",user.getStatus());
        //更新
        mongoTemplate.updateFirst(query,update,ManagerUserPo.class,COLLECTION_TABLE_USER);
    }

    /**
     * @description   : 查询用户
     * @method_name   : findByName
     * @param         : [userName]
     * @return        : com.example.serverdemo.userManage.po.ManagerUserPo
     * @throws        :
     * @date          : 2019/12/10 17:24
     * @author        : Lujw
     * @update date   :
     * @update author :
     */
    @Override
    public ManagerUserPo findByName(String userName) {
        //生成查询对象
        Query query = new Query(Criteria.where("account").is(userName));
        //查
        ManagerUserPo user = mongoTemplate.findOne(query, ManagerUserPo.class,COLLECTION_TABLE_USER);
        return user;
    }

    /**
     * @description   : 获取所有普通用户
     * @method_name   : findAllUser
     * @param         : []
     * @return        : java.util.List<com.example.serverdemo.userManage.po.ManagerUserPo>
     * @throws        :
     * @date          : 2019/12/10 17:25
     * @author        : Lujw
     * @update date   :
     * @update author :
     */
    @Override
    public List<ManagerUserPo> findAllUser() {
        //生成查询对象
        Query query = new Query(Criteria.where("level").is("2"));
        //查
       return  mongoTemplate.find(query,ManagerUserPo.class,COLLECTION_TABLE_USER);
    }

    
    /**
     * @description   : 通过id 查找用户
     * @method_name   : findById
     * @param         : [id]
     * @return        : com.example.serverdemo.userManage.po.ManagerUserPo
     * @throws        :
     * @date          : 2019/12/10 17:25
     * @author        : Lujw
     * @update date   :
     * @update author :
     */
    @Override
    public ManagerUserPo findById(String id) {
        return mongoTemplate.findById(id,ManagerUserPo.class,COLLECTION_TABLE_USER);
    }

    /**
     * @description   : 查找所有用户
     * @method_name   : findAll
     * @param         : []
     * @return        : java.util.List<com.example.serverdemo.userManage.po.ManagerUserPo>
     * @throws        :
     * @date          : 2019/12/10 17:25
     * @author        : Lujw
     * @update date   :
     * @update author :
     */
    @Override
    public List<ManagerUserPo> findAll() {
        return mongoTemplate.findAll(ManagerUserPo.class,COLLECTION_TABLE_USER);
    }

    /**
     * @description   : 通过id删除用户
     * @method_name   : deleteUserByPo
     * @param         : [managerUserPo]
     * @return        : void  
     * @throws        : 
     * @date          : 2019/12/10 17:25
     * @author        : Lujw
     * @update date   :
     * @update author :
     */
    @Override
    public void deleteUserByPo(ManagerUserPo managerUserPo) {
        mongoTemplate.remove(managerUserPo,COLLECTION_TABLE_USER);
    }
}
