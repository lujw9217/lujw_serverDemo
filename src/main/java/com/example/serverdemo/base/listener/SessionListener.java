package com.example.serverdemo.base.listener;

import com.example.serverdemo.base.entity.SessionManage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

/**
 * @author : Lujw
 * @Class Name   : SessionListener
 * @Description : TODO
 * @Project : serverdemo
 * @Program : com.example.serverdemo.base.listener
 * @date : 2019/12/10 21:01
 * @ModificationHistory Who       When        What
 * -----------------------------------------
 * Lujw   2019/12/10      创建
 */
public class SessionListener implements HttpSessionListener {
    private static final Logger LOGGER = LoggerFactory.getLogger(SessionListener.class);

    /**
     * @description   : 创建session时 执行
     * @method_name   : sessionCreated
     * @param         : [event]
     * @return        : void  
     * @throws        : 
     * @date          : 2019/12/10 21:01
     * @author        : Lujw
     * @update date   :
     * @update author :
     */
    @Override
    public synchronized void sessionCreated(HttpSessionEvent event) {
        LOGGER.info("==== Session is created ====");
        //session创建时设置session有效期为30分钟
        event.getSession().setMaxInactiveInterval(30*60);
        //session 用户 +1
        SessionManage.addSession(event.getSession());
    }

    /**
     * @description   : session销毁时执行
     * @method_name   : sessionDestroyed
     * @param         : [event]
     * @return        : void
     * @throws        :
     * @date          : 2019/12/10 21:02
     * @author        : Lujw
     * @update date   :
     * @update author :
     */
    @Override
    public void sessionDestroyed(HttpSessionEvent event) {
        LOGGER.info("==== Session is destroyed ====");
        //session 用户 +1
        SessionManage.delSession(event.getSession());
    }
}