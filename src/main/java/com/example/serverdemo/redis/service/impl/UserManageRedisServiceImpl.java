package com.example.serverdemo.redis.service.impl;

import com.example.serverdemo.redis.dao.ICaCheRepository;
import com.example.serverdemo.redis.service.UserManagerRedisService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;


/**
 * @author : Lujw
 * @Class Name   : UserManageRedisServiceImpl
 * @Description : redis 用户失败信息缓存服务
 * @date : 2019/7/30 10:38
 * @ModificationHistory Who       When        What
 * -----------------------------------------
 * Lujw   2019/7/30      创建
 */
@Service("userManagerRedisService")
public class UserManageRedisServiceImpl implements UserManagerRedisService {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserManageRedisServiceImpl.class);

    @Resource(name = "redisCaCheRepository")
    private ICaCheRepository redisCaCheRepository;

    //登录失败信息在redis中key值的前缀
    private static final String LOGIN_FAIL_PRE = "login_fail";

    //key过期分钟数
    private static final Integer TIMEOUT = 24*60;

    /**
     * @description   : 查询失败信息
     * @method_name   : findLoginFailObj
     * @param         : [account]
     * @return        : java.lang.Integer
     * @throws        : 
     * @date          : 2019/7/30 10:39
     * @version       : v1.00
     * @author        : Lujw
     * @update date   :
     * @update author :
     */
    @Override
    public Long findLoginFailObj(String account) {
        return redisCaCheRepository.getLong(LOGIN_FAIL_PRE+":"+account);
    }

    /**
     * @description   : 更新失败信息
     * @method_name   : updateLoginFailObj
     * @param         : [account]
     * @return        : java.lang.Long  
     * @throws        : 
     * @date          : 2019/7/30 10:51
     * @version       : v1.00
     * @author        : Lujw
     * @update date   :
     * @update author :
     */
    @Override
    public Long updateLoginFailObj(String account) {
      return redisCaCheRepository.incr(LOGIN_FAIL_PRE+":"+account,TIMEOUT,TimeUnit.MINUTES);
    }

    /**
     * @description   : 删除失败信息
     * @method_name   : deleteLoginFailCount
     * @param         : [account]
     * @return        : void  
     * @throws        : 
     * @date          : 2019/7/30 10:39
     * @version       : v1.00
     * @author        : Lujw
     * @update date   :
     * @update author :
     */
    @Override
    public void deleteLoginFailCount(String account) {
        redisCaCheRepository.removeStrValue(LOGIN_FAIL_PRE+":"+account);
    }
}