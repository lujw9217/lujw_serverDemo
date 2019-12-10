package com.example.serverdemo.base.exception;

import com.alibaba.fastjson.JSONArray;
import com.example.serverdemo.base.entity.ResultObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Locale;

/**
 * @author : Lujw
 * @Class Name   : GenericExceptionHandler
 * @Description : 统一异常处理类
 * @Project : serverdemo
 * @Program : com.example.serverdemo.base.exception
 * @date : 2019/12/10 16:52
 * @ModificationHistory Who       When        What
 * -----------------------------------------
 * Lujw   2019/12/10      创建
 */
@Component("exceptionResolver")
public class GenericExceptionHandler implements HandlerExceptionResolver {
    private Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    @Resource
    private MessageSource messageResource;

    /*最终视图 */
    private static final ModelAndView finalMV = new ModelAndView();

    @Override
    public ModelAndView resolveException(HttpServletRequest request,
                                         HttpServletResponse response, Object handler, Exception ex) {
        // TODO Auto-generated method stub
        // 异常所属块
        //String headCode = "";
        // 实际异常信息code
        String msgCode = "";
        //异常附加信息
        String[] params = null;
        StringBuffer errorMsg = new StringBuffer();
        if (ex instanceof TopException) {
            // 本系统异常
            TopException top = TopException.class.cast(ex);
            //headCode = top.getHeadCode();
            msgCode = top.getMsgCode();
            params = top.getParams();

            for(String error : params){
                errorMsg.append(error);
            }
            try
            {
                //具体异常信息国际化
                errorMsg.append( messageResource.getMessage(msgCode, params, Locale.SIMPLIFIED_CHINESE));
            } catch (Exception e)
            {
                errorMsg.append(msgCode);
            }
        }else {
            // 其他异常
            errorMsg.append("未知异常信息：");
            errorMsg.append(ex.getMessage());
            LOGGER.debug("未知异常信息：" + ex.getMessage());
        }
        //将异常信息以json格式返回
        ResultObject resObj = new ResultObject();
        response.setContentType("application/json;charset=UTF-8");
        try {
            resObj.setResCode(ResultObject.ERROR);
            resObj.setResMsg(errorMsg.toString());
            String jsonStr = JSONArray.toJSONString(resObj);
            response.getWriter().write(jsonStr);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return finalMV;

    }
}