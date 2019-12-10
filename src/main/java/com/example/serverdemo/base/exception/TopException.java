package com.example.serverdemo.base.exception;

/**
 * @author : Lujw
 * @Class Name   : TopException
 * @Description : 最外层系统异常
 * @Project : serverdemo
 * @Program : com.example.serverdemo.exception
 * @date : 2019/12/10 15:07
 * @ModificationHistory Who       When        What
 * -----------------------------------------
 * Lujw   2019/12/10      创建
 */
public class TopException extends Exception{

    private static final long serialVersionUID = -5157056222647009184L;
    //异常所属块码
    protected String headCode;
    //实际异常信息码
    protected String msgCode;
    //自定义异常信息
    protected String[] params;

    public TopException(String headCode, String msgCode, String... params){
        this.headCode = headCode;
        this.msgCode = msgCode;
        this.params = params;
    }

    public String getHeadCode() {
        return headCode;
    }

    public void setHeadCode(String headCode) {
        this.headCode = headCode;
    }

    public String getMsgCode() {
        return msgCode;
    }

    public void setMsgCode(String msgCode) {
        this.msgCode = msgCode;
    }

    public String[] getParams() {
        return params;
    }

    public void setParams(String[] params) {
        this.params = params;
    }

}