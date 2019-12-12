package com.example.serverdemo.module.userManage.service.impl;

import com.example.serverdemo.base.entity.ResultObject;
import com.example.serverdemo.base.exception.TopException;
import com.example.serverdemo.base.security.UserRole;
import com.example.serverdemo.base.util.BaseUtil;
import com.example.serverdemo.base.util.DateUtil;
import com.example.serverdemo.base.util.MD5Util;
import com.example.serverdemo.redis.service.UserManagerRedisService;
import com.example.serverdemo.module.userManage.constant.Menu;
import com.example.serverdemo.module.userManage.dao.IMenuDao;
import com.example.serverdemo.module.userManage.dao.IUserDao;
import com.example.serverdemo.module.userManage.po.ManagerUserPo;
import com.example.serverdemo.module.userManage.po.MenuPo;
import com.example.serverdemo.module.userManage.po.UsersAndMenus;
import com.example.serverdemo.module.userManage.service.IUserService;
import com.example.serverdemo.module.userManage.util.ManagerAccountFormValidateUtil;
import com.example.serverdemo.module.userManage.util.MenuUtil;
import com.example.serverdemo.module.userManage.vo.ManagerUserForm;
import com.example.serverdemo.module.userManage.vo.ManagerUserVo;
import com.example.serverdemo.module.userManage.vo.MenuVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;


/**
 * 用户服务接口 实现类
 */
@Service("userServiceImpl")
public class UserServiceImpl implements IUserService {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);

    //注入用户dao
    @Resource
    private IUserDao userDao;

    //注入菜单dao
    @Resource
    private IMenuDao menuDao;

    //注入用户缓存服务
    @Resource
    private UserManagerRedisService userManagerRedisService;

    //用户被锁定标记，存在此标记。则视为永久被锁定。
    private static final String LOCK_USER_MANAGER="LOCAKING";

  /**
   * @description   : 用户登录操作
   * @method_name   : login
   * @param         : [userName, password, idCode, authCode, ip]
   * @return        : ManagerUserVo
   * @throws        :
   * @date          : 2019/12/10 17:30
   * @author        : Lujw
   * @update date   :
   * @update author :
   */
    @Override
    public ManagerUserVo login(String userName, String password, String idCode, String authCode, String ip) throws TopException {
        //用户名不能为空
        if(!BaseUtil.stringNotNull(userName)){
            LOGGER.error("用户名为空");
            throw  new TopException("登录失败","用户名不能为空");
        }
        //密码不能为空
        if(!BaseUtil.stringNotNull(password)){
            LOGGER.error("密码为空");
            throw  new TopException("登录失败","密码不能为空");
        }

        //验证码校验时的当前时间戳
        Date date = new Date();
        Long idCodeUseTime = date.getTime();
        //验证码校验
        if(BaseUtil.stringNotNull(authCode)){
            if(BaseUtil.stringNotNull(idCode) && idCode.contains("_")){
                String [] idCodes = idCode.split("_");
                Long idCodeCreateTime;
                try {
                    idCodeCreateTime = BaseUtil.stringToLong(idCodes[1]);
                } catch (Exception e) {
                    LOGGER.error("验证码错误");
                    throw new TopException("登录失败", "验证码错误");
                }

                //验证码已过期，时间为30秒
                if(idCodeUseTime > idCodeCreateTime){
                    LOGGER.error("验证码已过期");
                    throw new TopException("登录失败", "验证码已过期");
                }

                //session里的验证码与前端用户输入的验证码进行比较
                if(!authCode.equalsIgnoreCase(idCodes[0])){
                    LOGGER.error("验证码错误");
                    throw new TopException("登录失败", "验证码错误");
                }
            }else{
                LOGGER.error("验证码错误");
                throw new TopException("登录失败", "验证码错误");
            }
        }else{
            LOGGER.error("验证码错误");
            throw new TopException("登录失败", "验证码错误");
        }

        ManagerUserPo userPo;
        try{
            //获取用户 通过用户名
             userPo = userDao.findByName(userName);
        }catch (Exception e){
            LOGGER.error("<--------  查询数据库获取用户出错！！！！  ------->");
            throw new TopException("查询数据库出错","登陆失败");
        }
        String passwordMd5;
        try{
            //MD5加密密码
             passwordMd5 = MD5Util.md5((userName+
                    password).getBytes());
        }catch (Exception e){
            LOGGER.error("MD5加密密码出错！！！");
            throw new TopException("MD5加密密码出错","登陆失败");
        }

        //用户不存在
        if(!BaseUtil.objectNotNull(userPo)){
            LOGGER.error("通过用户名查询数据库，没有查到该用户，用户不存在");
            throw  new TopException("登录失败","用户名或密码错误");
        }

        if(BaseUtil.stringNotNull(userPo.getStatus())){
            LOGGER.error("密码连续错误5次，账号已被永久锁定");
            throw  new TopException("登录失败","您的账号已被锁定，请联系管理员");
        }

        //密码不对
        if(!userPo.getPassword().equals(passwordMd5)){
            Long finalCount;
            try{
                //密码错误次数自增
                finalCount=userManagerRedisService.updateLoginFailObj(userName);
            }catch (Exception e){
                LOGGER.error("调用redis服务，更新用户密码错误次数出错");
                throw new TopException("系统错误","缓存异常");
            }

            if(finalCount>=5){
                //设置标记，标记不为空，则表示该用户被永久锁定。
                userPo.setStatus(LOCK_USER_MANAGER);
                try{
                    //更新用户
                    userDao.updateLogin(userPo);
                }catch (Exception e){
                    LOGGER.error("调用mongo服务，更新用户信息出错");
                    throw new TopException("系统错误","数据异常");
                }
                //用户已被永久标记失效，删除reids中用户错误次数信息。方便管理员修改标记后，使用户立即生效
                userManagerRedisService.deleteLoginFailCount(userName);

                LOGGER.error("密码连续错误5次，账号已被永久锁定");
                throw  new TopException("登录失败","您的账号已被锁定，请联系管理员");
            }

            LOGGER.error("用户密码输入错误，已经连续错误了{}次",finalCount);
            throw  new TopException("登录失败","密码错误，还可以重试"
                    +BaseUtil.longToString(5-finalCount)+"次");
        }

        try{
            //登录成功，删除密码错误次数，重新计数
            userManagerRedisService.deleteLoginFailCount(userPo.getAccount());
        }catch (Exception e){
            LOGGER.error("调用redis服务，删除密码错误次数缓存出错");
            throw new TopException("系统错误","缓存异常");
        }

        //更新用户最后登陆时间
        userPo.setLastLogin_time(DateUtil.dateFormat(new Date(),
                DateUtil.DATE_FORMAT_LONG));
        //用户最后登陆的IP
        userPo.setLastLogin_ip(ip);
        try{
            //更新用户
            userDao.updateLogin(userPo);
        }catch (Exception e){
            LOGGER.error("调用mongo服务，更新用户信息出错");
            throw new TopException("系统错误","数据异常");
        }
        //将userPo转化为userVo
        ManagerUserVo managerUserVo = new ManagerUserVo();
        managerUserVo.setManagerUserPo(userPo);
        if ("1".equals(userPo.getLevel())) {
            //超级管理员
            managerUserVo.setRole(UserRole.ROLE_ADMIN.toString());
        } else if ("2".equals(userPo.getLevel())) {
            //普通用户
            managerUserVo.setRole(UserRole.ROLE_USER.toString());
        }else {
            LOGGER.error("用户不存在","登录查找用户角色为空！");
            throw  new TopException("用户不存在","用户不存在");
        }
        managerUserVo.setUserName(userPo.getAccount());
        managerUserVo.setManagerUserPo(userPo);
        return managerUserVo;
    }

 /**
  * @description   : 获取所有用户 和 菜单数据
  * @method_name   : getAllAndMenus
  * @param         : []
  * @return        : com.example.serverdemo.base.entity.ResultObject
  * @throws        :
  * @date          : 2019/12/10 17:50
  * @author        : Lujw
  * @update date   :
  * @update author :
  */
    @Override
    public UsersAndMenus getAllAndMenus() {
        //返回对象
        UsersAndMenus usersAndMenus = new UsersAndMenus();
        //调用dao层查找所有用户
        List<ManagerUserPo> userPos = userDao.findAllUser();
        //用户VO集合
        List<ManagerUserVo> userVos = new ArrayList<>();
        //转化为vo对象
        for (ManagerUserPo managerUserPo : userPos){
            ManagerUserVo userVo = new ManagerUserVo();
            userVo.setUserName(managerUserPo.getAccount());
            userVo.setManagerUserPo(managerUserPo);
            userVos.add(userVo);
        }
        usersAndMenus.setUserVos(userVos);
        //查找所有菜单
        List<MenuPo> menuPos = menuDao.findAll();
        //移除用户管理/加解密功能
        MenuPo m = new MenuPo();
        m.setMenuCode(Menu.CUSTOMER_USER_MANAGE.getCode());
        menuPos.remove(m);
//        m.setMenuCode(Menu.ENCRY_AND_DECRY.getCode());
//        menuPos.remove(m);

        List<MenuVo> menuVos = new ArrayList<>();
        //转化成Vo
        for (MenuPo menuPo : menuPos){
            menuVos.add(MenuUtil.menuPoToMenuVo(menuPo));
        }
        usersAndMenus.setMenuVos(menuVos);
        return usersAndMenus;
    }

    /**
     * @description   : 添加用户
     * @method_name   : addUser
     * @param         : [managerUserForm]
     * @return        : com.example.serverdemo.base.entity.ResultObject
     * @throws        :
     * @date          : 2019/12/10 17:50
     * @author        : Lujw
     * @update date   :
     * @update author :
     */
    @Override
    public void addUser(ManagerUserForm managerUserForm) throws TopException {
        ResultObject resultObject = new ResultObject();
        //检查表单输入参数 合法性
        checkFormParameter(managerUserForm);
        //查找所有用户
        List<ManagerUserPo> managerUserPos = userDao.findAll();
        //判断用户名 是否已存在
        for (ManagerUserPo userPo:managerUserPos){
            if(userPo.getAccount().equals(managerUserForm.getUserName())){
                LOGGER.error("<!!--添加用户时用户名已存在-->");
                throw new TopException("<!!--用户名已存在-->","用户名已存在");
            }
        }
        //将form 转化为 Po对象
        ManagerUserPo managerUserPo =userFromToUserPo(managerUserForm);
        //md5加密密码
        String pwd = MD5Util.md5((managerUserForm.getUserName()+managerUserForm.getPassword()).getBytes());
        managerUserPo.setPassword(pwd);
        //创建时间
        managerUserPo.setCreate_time(DateUtil.dateFormat(new Date(),DateUtil.DATE_FORMAT_LONG));
        //保存用户
        userDao.save(managerUserPo);
    }

    
  /**
   * @description   : 修改用户
   * @method_name   : modifyUser
   * @param         : [managerUserForm]
   * @return        : com.example.serverdemo.base.entity.ResultObject
   * @throws        :
   * @date          : 2019/12/10 17:50
   * @author        : Lujw
   * @update date   :
   * @update author :
   */
    @Override
    public void modifyUser(ManagerUserForm managerUserForm) throws TopException {
        //检查表单输入参数 是否合法性
        checkFormParameter(managerUserForm);
        //将Vo对象 转化为Po
        ManagerUserPo managerUserPo = userFromToUserPo(managerUserForm);
        //通过id查找userPo 赋值给 managerUserPo
        ManagerUserPo userPo  = userDao.findById(managerUserForm.get_id());
        //查找所有用户
        List<ManagerUserPo> managerUserPos = userDao.findAll();
        //判断用户名 是否已存在
        for (ManagerUserPo up:managerUserPos){
            if(up.getAccount().equals(userPo.getAccount())){
                continue;
            }
            if(up.getAccount().equals(managerUserForm.getUserName())){
                LOGGER.error("<!!--修改用户时用户名已存在-->");
                throw new TopException("<!!--用户名已存在-->","用户名已存在");
            }
        }
        //通过id 更新用户
        managerUserPo.set_id(managerUserForm.get_id());
        //最后登陆ip
        managerUserPo.setLastLogin_ip(userPo.getLastLogin_ip());
        //最后登陆时间
        managerUserPo.setLastLogin_time(userPo.getLastLogin_time());
        //密码
        managerUserPo.setPassword(userPo.getPassword());
        //最后更新时间
        managerUserPo.setLastUpdate_time(DateUtil.dateFormat(new Date(),DateUtil.DATE_FORMAT_LONG));
        //持久化修改用户
        userDao.save(managerUserPo);
    }

 /**
  * @description   : 将前端表单用户对象转化为用户Po对象
  * @method_name   : userFromToUserPo
  * @param         : [managerUserForm]
  * @return        : com.example.serverdemo.userManage.po.ManagerUserPo
  * @throws        :
  * @date          : 2019/12/10 17:51
  * @author        : Lujw
  * @update date   :
  * @update author :
  */
    private ManagerUserPo userFromToUserPo(ManagerUserForm managerUserForm){
        //将Vo对象 转化为Po
        ManagerUserPo managerUserPo = new ManagerUserPo();
        managerUserPo.setAccount(managerUserForm.getUserName());
        //将菜单数组转化为 菜单集合
        Set<Integer> menuCode = new HashSet();
        //将数组 加入set 集合
        Collections.addAll(menuCode,managerUserForm.getMenuPos());
        Set<MenuPo> menuPos = new HashSet<>();
        for (Integer code:menuCode
        ) {
            MenuPo menuPo1 = new MenuPo();
            menuPo1.setMenuCode(code);
            menuPos.add(menuPo1);
        }
        //设置用户po对象 所拥有的菜单权限
        managerUserPo.setMenuPos(menuPos);
        managerUserPo.setLevel(managerUserForm.getLevel());
        //设置角色
        if (managerUserForm.getLevel().equals('2')){
            managerUserPo.setRole(UserRole.ROLE_USER.toString());
        }
//        managerUserPo.setStatus(1);
        managerUserPo.setLastUpdate_time(DateUtil.dateFormat(new Date(),DateUtil.DATE_FORMAT_LONG));
        return managerUserPo;
    }

   /**
    * @description   : 检查表单参数
    * @method_name   : checkFormParameter
    * @param         : [managerUserForm]
    * @return        : void
    * @throws        :
    * @date          : 2019/12/10 17:51
    * @author        : Lujw
    * @update date   :
    * @update author :
    */
    private void checkFormParameter(ManagerUserForm managerUserForm) throws TopException {
        if(!BaseUtil.stringNotNull(managerUserForm.getUserName())){
            LOGGER.error("<!!--添加/修改用户时用户名为空-->");
            throw new TopException("<!!--用户名为空-->","用户名不能为空");
        }
        if(!BaseUtil.stringNotNull(managerUserForm.getLevel())){
            LOGGER.error("<!!--添加/修改用户时用户级别为空-->");
            throw new TopException("<!!--用户级别为空-->","用户级别不能为空");
        }
        //添加操作时，有密码校验
        if(managerUserForm.get_id()==null){
            if(!BaseUtil.stringNotNull(managerUserForm.getPassword())){
                LOGGER.error("<!!--添加/修改用户时用户密码为空-->");
                throw new TopException("<!!--用户密码为空-->","密码不能为空");
            }
            //判断是否为弱密码
            if(!ManagerAccountFormValidateUtil.checkWeakPassword(managerUserForm.getUserName(),managerUserForm.getPassword())){
                LOGGER.error("<!!--添加/修改用户时用户密码为为弱密码-->");
                throw new TopException("<密码为弱密码>","请输入强密码，密码必须包含特殊字符，大小字母，数字三种以上");
            }
        }

    }

   /**
    * @description   : 删除用户
    * @method_name   : deleteUser
    * @param         : [id]
    * @return        : com.example.serverdemo.base.entity.ResultObject
    * @throws        :
    * @date          : 2019/12/10 17:52
    * @author        : Lujw
    * @update date   :
    * @update author :
    */
    @Override
    public void deleteUser(String id) {
        //通过id查找用户
        ManagerUserPo managerUserPo = userDao.findById(id);
        //真实删除
        userDao.deleteUserByPo(managerUserPo);
    }

    /**
     * @description   : 修改密码
     * @method_name   : modifyPwd
     * @param         : [oldPwd, newPwd, userVo]
     * @return        : com.example.serverdemo.base.entity.ResultObject
     * @throws        :
     * @date          : 2019/12/10 17:52
     * @author        : Lujw
     * @update date   :
     * @update author :
     */
    @Override
    public void modifyPwd(String oldPwd,String newPwd, ManagerUserVo userVo) throws TopException {
        //查找po对象
        ManagerUserPo userPo = userDao.findById(userVo.get_id());
        //加密旧密码
        String md5OldPwd = MD5Util.md5((userPo.getAccount()+oldPwd).getBytes());
        //判断输入旧密码是否正确
        if (!md5OldPwd.equals(userPo.getPassword())){
            LOGGER.error("<!!--修改密码时旧密码错误-->");
            throw new TopException("<输入旧密码错误>","旧密码错误");
        }
        //判断密码参数是否合法
        ManagerAccountFormValidateUtil.validatePassWord(newPwd);
        //判断是否为弱密码
        if(!ManagerAccountFormValidateUtil.checkWeakPassword(userVo.getUserName(),newPwd)){
            LOGGER.error("<!!--修改密码时新密码为弱密码-->");
            throw new TopException("<密码为弱密码>","请输入强密码，密码必须包含特殊字符，大小字母，数字三种以上");
        }
        //新密码MD5加密
        String md5NewPwd = MD5Util.md5((userPo.getAccount()+newPwd).getBytes());
        userPo.setPassword(md5NewPwd);
        //更新密码
        userDao.save(userPo);
    }


//    public static void main(String[] args){
//        String userName="ZccSd_RT";
//        String password="H!su10fs@3";
//        //MD5加密密码
//        String passwordMd5 = DigestUtil.md5((userName+
//                password).getBytes());
//        System.out.println(passwordMd5);
//    }
}
