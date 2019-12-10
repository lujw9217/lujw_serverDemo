package com.example.serverdemo.base.entity;

/**
 * @author : Lujw
 * @Class Name   : ResultObject
 * @Description : 公共的信息类
 * @Project : serverdemo
 * @Program : com.example.serverdemo.base.entity
 * @date : 2019/12/10 16:56
 * @ModificationHistory Who       When        What
 * -----------------------------------------
 * Lujw   2019/12/10      创建
 */
public class ResultObject {

    //成功消息码
    public static final String SUCCESS="success";
    //错误消息码
    public static final String ERROR="error";
    //消息表示码
    private String resCode;
    //消息
    private Object resMsg;
    /**
     *  @BareFieldName : resCode
     *  @return  the resCode
     */

    public String getResCode() {
        return resCode;
    }
    /**
     * @param resCode the resCode to set
     */
    public void setResCode(String resCode) {
        this.resCode = resCode;
    }
    /**
     *  @BareFieldName : resMsg
     *  @return  the resMsg
     */

    public Object getResMsg() {
        return resMsg;
    }
    /**
     * @param resMsg the resMsg to set
     */
    public void setResMsg(Object resMsg) {
        this.resMsg = resMsg;
    }

}