package com.example.serverdemo.interceptor;

import com.example.serverdemo.base.exception.DebugParamException;
import com.example.serverdemo.base.exception.TopException;
import com.example.serverdemo.base.security.MenuChecker;
import com.example.serverdemo.base.util.BaseUtil;
import com.example.serverdemo.module.userManage.vo.ManagerUserVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * @author : Lujw
 * @Class Name   : UserSecurityInterceptor
 * @Description : 拦截器
 * @Project : serverdemo
 * @Program : com.example.serverdemo.interceptor
 * @date : 2019/12/10 17:02
 * @ModificationHistory Who       When        What
 * -----------------------------------------
 * Lujw   2019/12/10      创建
 */
@Component("userSecurityInterceptor")
public class UserSecurityInterceptor implements HandlerInterceptor {
    private Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    // 免验证拦截
    private static final List<String> userPermissionPaths;

    static {

        // 免验证路径列表
        userPermissionPaths = new ArrayList<>();
        userPermissionPaths.add("");
        userPermissionPaths.add("/");
        // 登录验证
        userPermissionPaths.add("/web/check");
        //获取验证码
        userPermissionPaths.add("/web/authCode");
    }

    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response, Object handler) throws Exception {
        // TODO Auto-generated method stub
        // 当前请求的IP地址
        String requestIp = BaseUtil.getIpAddr(request);
        LOGGER.info("请求Ip:"+requestIp);
        // 获取请求url
        String servletPath = request.getServletPath();
        LOGGER.info("请求url:"+servletPath);
        // 通过session来验证用户是否合法
        ManagerUserVo user = (ManagerUserVo) request.getSession().getAttribute(
                "user");
        // 若是需要验证用户信息的url则进行验证
        if (!isInUserPermissionPaths(servletPath)) {
            if (BaseUtil.objectNotNull(user)) {
                //登录验证
                if(user==null){
                    //用户未登录不能访问
                    LOGGER.error("未登录! " + requestIp + "进入系统，请求链接为：" + request.getRequestURI());
                    throw  new TopException("","用户未登录或者会话已过期！");
                }
            } else {
                LOGGER.error("<!!--拦截器： 会话失效，请重新登录-->");
                throw new DebugParamException("会话失效，请重新登录！");
            }
        }
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        // 方法上是否有授权注解
//		if (handler.getClass().isAssignableFrom(HandlerMethod.class)) {
        //检测当前用户时候有权限访问指定菜单
        MenuChecker.check(user, handlerMethod);
//		}
        return true;
    }

 /**
  * @description   : 判断请求url是否是免验证路径
  * @method_name   : isInUserPermissionPaths
  * @param         : [url]
  * @return        : boolean
  * @throws        :
  * @date          : 2019/12/10 17:03
  * @author        : Lujw
  * @update date   :
  * @update author :
  */
    private static boolean isInUserPermissionPaths(String url) {
        for (String uri : userPermissionPaths) {
            if (uri.equals(url)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void postHandle(HttpServletRequest request,
                           HttpServletResponse response, Object handler,
                           ModelAndView modelAndView) throws Exception {
        // TODO Auto-generated method stub

    }

    @Override
    public void afterCompletion(HttpServletRequest request,
                                HttpServletResponse response, Object handler, Exception ex)
            throws Exception {
        // TODO Auto-generated method stub

    }
}