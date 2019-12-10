package com.example.serverdemo.base.entity;


import com.example.serverdemo.module.userManage.vo.ManagerUserVo;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @description   : session管理器 用于管理session
 * @method_name   :
 * @param         :
 * @return        :
 * @throws        :
 * @date          : 2019/12/10 20:41
 * @author        : Lujw
 * @update date   :
 * @update author :
 */
public class SessionManage {
    //用于存入当前的sesssion
    public static Map<String, HttpSession> sessionMap = new HashMap<>();

   /**
    * @description   : 添加一个session
    * @method_name   : addSession
    * @param         : [session]
    * @return        : void
    * @throws        :
    * @date          : 2019/12/10 20:41
    * @author        : Lujw
    * @update date   :
    * @update author :
    */
    public  static synchronized   void  addSession(HttpSession session){
        sessionMap.put(session.getId(),session);
    }

   /**
    * @description   : 通过账号名 查找用户
    * @method_name   : getSessionByAccount
    * @param         : [account]
    * @return        : javax.servlet.http.HttpSession
    * @throws        :
    * @date          : 2019/12/10 20:41
    * @author        : Lujw
    * @update date   :
    * @update author :
    */
    public  static synchronized HttpSession getSessionByAccount(String account){
        Set<String> keys = sessionMap.keySet();
        for (String key:keys
             ) {
            HttpSession session = sessionMap.get(key);
            ManagerUserVo user = (ManagerUserVo) session.getAttribute("user");
            if(user!=null && user.getUserName().equals(account)){
                return session;
            }
        }
        return null;
    }
    
  /**
   * @description   : 删除一个session
   * @method_name   : delSession
   * @param         : [session]
   * @return        : void
   * @throws        :
   * @date          : 2019/12/10 20:42
   * @author        : Lujw
   * @update date   :
   * @update author :
   */
    public static synchronized void delSession(HttpSession session){
        if(session!=null) {
            HttpSession session1 = sessionMap.remove(session.getId());
            if(session1!=null){
                session1.invalidate();
            }
        }
    }
}
