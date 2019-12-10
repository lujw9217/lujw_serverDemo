package com.example.serverdemo.redis.service;

/**
 * redis 用户失败信息缓存接口
 */
public interface UserManagerRedisService {

    /**
     * @description   : 查询失败信息
     * @method_name   : findLoginFailObj
     * @param         : [account]
     * @return        : java.lang.Long  
     * @throws        : 
     * @date          : 2019/7/30 10:36
     * @version       : v1.00
     * @author        : Lujw
     * @update date   :
     * @update author :
     */
    Long findLoginFailObj(String account);
    
    /**
     * @description   : 更新失败信息
     * @method_name   : updateLoginFailObj
     * @param         : [account]
     * @return        : java.lang.Long  
     * @throws        : 
     * @date          : 2019/7/30 10:36
     * @version       : v1.00
     * @author        : Lujw
     * @update date   :
     * @update author :
     */
    Long updateLoginFailObj(String account);
    
    /**
     * @description   : 删除失败信息
     * @method_name   : deleteLoginFailCount
     * @param         : [account]
     * @return        : void  
     * @throws        : 
     * @date          : 2019/7/30 10:36
     * @version       : v1.00
     * @author        : Lujw
     * @update date   :
     * @update author :
     */
    void deleteLoginFailCount(String account);
}
