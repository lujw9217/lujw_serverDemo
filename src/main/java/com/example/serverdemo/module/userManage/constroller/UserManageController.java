package com.example.serverdemo.module.userManage.constroller;

import com.example.serverdemo.base.entity.CommonResObject;
import com.example.serverdemo.base.entity.ResultObject;
import com.example.serverdemo.base.entity.SessionManage;
import com.example.serverdemo.base.exception.TopException;
import com.example.serverdemo.base.security.WhichMenu;
import com.example.serverdemo.module.userManage.constant.Menu;
import com.example.serverdemo.module.userManage.service.IMenuService;
import com.example.serverdemo.module.userManage.service.IUserService;
import com.example.serverdemo.module.userManage.vo.ManagerUserForm;
import com.example.serverdemo.module.userManage.vo.ManagerUserVo;
import com.example.serverdemo.module.userManage.vo.UserPwdForm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


/**
 * 登录服务
 */
@Controller
@RequestMapping("/web")
public class UserManageController {
    private static final Logger LOG = LoggerFactory.getLogger(UserManageController.class);

    //注入菜单服务
    @Resource
    private IMenuService menuService;
    //注入用户服务
    @Resource
    private IUserService userService;

    /**
     * @description   : 登录
     * @method_name   : login
     * @param         : [request, userName, password, authCode]
     * @return        : com.example.serverdemo.base.entity.ResultObject
     * @throws        :
     * @date          : 2019/12/10 20:45
     * @author        : Lujw
     * @update date   :
     * @update author :
     */
    @ResponseBody
    @RequestMapping(value = "/check")
    public ResultObject login(HttpServletRequest request, String userName, String password, String authCode) throws TopException {
        //获取登陆IP
        String ip = request.getRemoteAddr();
        //响应对象
        ResultObject resultObject = new ResultObject();
        //从session中取出 验证码
        String idCode = (String) request.getSession().getAttribute("idcode");
        ManagerUserVo managerUserVo = userService.login(userName,password,idCode,authCode,ip);
        //验证通过后，将当前验证码从session这种擦除
        request.getSession().setAttribute("idcode", "");
        //判断返回code 是否验证通过
        LOG.info("用户：" + managerUserVo.getUserName() + "请求服务");
        //判断当前用户是否在线
        HttpSession session = SessionManage.getSessionByAccount("ss");
        if(session!=null){
            //下线操作
            SessionManage.delSession(session);
        }
        //将用户存入session
        request.getSession().setAttribute("user",managerUserVo);
        resultObject.setResCode(ResultObject.SUCCESS);
        resultObject.setResMsg(managerUserVo);
        return resultObject;
    }


    /**
     * @description   : 用户退出
     * @method_name   : logout
     * @param         : [request, response]
     * @return        : com.example.serverdemo.base.entity.ResultObject
     * @throws        :
     * @date          : 2019/12/10 20:42
     * @author        : Lujw
     * @update date   :
     * @update author :
     */
    @RequestMapping("/logout")
    @ResponseBody
    public ResultObject logout(HttpServletRequest request, HttpServletResponse response){
        ResultObject resultObject = new ResultObject();
        //清空session 内容
        HttpSession session = request.getSession();
        if (session != null) {
            session.invalidate();
        }
        //清空cookies
        Cookie[] cookies = request.getCookies();
        if (cookies != null && cookies.length > 0) {
            for (Cookie cookie : cookies) {
                cookie.setValue("-");
                cookie.setMaxAge(0);
                response.addCookie(cookie);
            }
        }
        resultObject.setResCode(ResultObject.SUCCESS);
        resultObject.setResMsg("用户已退出");
        return resultObject;
    }

    /**
     * @description   : 密码修改
     * @method_name   : modifyPwd
     * @param         : [userPwdForm, request]
     * @return        : com.example.serverdemo.base.entity.ResultObject
     * @throws        :
     * @date          : 2019/12/10 20:42
     * @author        : Lujw
     * @update date   :
     * @update author :
     */
    @RequestMapping("modifyPwd")
    @ResponseBody
    public ResultObject modifyPwd(@RequestBody UserPwdForm userPwdForm, HttpServletRequest request) throws TopException {
        //获取当前登录用户
        ManagerUserVo userVo = (ManagerUserVo) request.getSession().getAttribute("user");
        //修改密码 并返回响应对象
        return  userService.modifyPwd(userPwdForm.getOldPwd(),userPwdForm.getNewPwd(),userVo);
    }

    /**
     * @description   : 获取当前登录用户所拥有的菜单
     * @method_name   : getMenuByPermission
     * @param         : [request]
     * @return        : CommonResObject
     * @throws        :
     * @date          : 2019/12/10 20:43
     * @author        : Lujw
     * @update date   :
     * @update author :
     */
    @RequestMapping("/getMenuByPermission")
    @ResponseBody
    public CommonResObject getMenuByPermission(HttpServletRequest request){
        //获取当前登录用户
        ManagerUserVo managerUserVo =
                (ManagerUserVo) request.getSession().getAttribute("user");
        //返回响应数据
        return menuService.getMenuByUser(managerUserVo);
    }

    /**
     * @description   : 查询所有用户
     * @method_name   : getUsers
     * @param         : [request]
     * @return        : com.example.serverdemo.base.entity.ResultObject
     * @throws        :
     * @date          : 2019/12/10 20:43
     * @author        : Lujw
     * @update date   :
     * @update author :
     */
    @RequestMapping("/getUsers")
    @ResponseBody
    @WhichMenu(Menu.CUSTOMER_USER_MANAGE)
    public ResultObject getUsers(HttpServletRequest request){
        //获得所有用户
        return userService.getAllAndMenus();
    }

 /**
  * @description   : 添加用户
  * @method_name   : addUser
  * @param         : [managerUserForm, request]
  * @return        : com.example.serverdemo.base.entity.ResultObject
  * @throws        :
  * @date          : 2019/12/10 20:44
  * @author        : Lujw
  * @update date   :
  * @update author :
  */
    @RequestMapping("/addUserByManage")
    @ResponseBody
    @WhichMenu(Menu.CUSTOMER_USER_MANAGE)
    public ResultObject addUser(@RequestBody ManagerUserForm managerUserForm, HttpServletRequest request) throws TopException {
        //调用添加用户服务
        return userService.addUser(managerUserForm);
    }

   /**
    * @description   : 修改用户
    * @method_name   : modifyUser
    * @param         : [managerUserForm, request]
    * @return        : com.example.serverdemo.base.entity.ResultObject
    * @throws        :
    * @date          : 2019/12/10 20:44
    * @author        : Lujw
    * @update date   :
    * @update author :
    */
    @RequestMapping("/modifyUserByManage")
    @ResponseBody
    @WhichMenu(Menu.CUSTOMER_USER_MANAGE)
    public ResultObject modifyUser(@RequestBody ManagerUserForm managerUserForm, HttpServletRequest request) throws TopException {
        //调用修改用户服务
        return userService.modifyUser(managerUserForm);
    }

    /**
     * @description   : 删除用户
     * @method_name   : deleteUser
     * @param         : [_id]
     * @return        : com.example.serverdemo.base.entity.ResultObject
     * @throws        :
     * @date          : 2019/12/10 20:44
     * @author        : Lujw
     * @update date   :
     * @update author :
     */
    @RequestMapping("/deleteUser")
    @ResponseBody
    @WhichMenu(Menu.CUSTOMER_USER_MANAGE)
    public ResultObject deleteUser(String _id) throws TopException {
        //调用添加用户服务
        return userService.deleteUser(_id);
    }
}
