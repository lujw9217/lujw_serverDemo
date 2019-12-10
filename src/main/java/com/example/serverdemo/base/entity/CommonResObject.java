package com.example.serverdemo.base.entity;

/**
 * @author : Lujw
 * @Class Name   : CommonResObject
 * @Description : 系统正常响应前台对象
 * @Project : serverdemo
 * @Program : com.example.serverdemo.base.entity
 * @date : 2019/12/10 17:28
 * @ModificationHistory Who       When        What
 * -----------------------------------------
 * Lujw   2019/12/10      创建
 */
public class CommonResObject {

    private String resCode = "1000";

    private String resMsg;

    private Object resObj;

    public String getResCode() {
        return resCode;
    }

    public void setResCode(String resCode) {
        this.resCode = resCode;
    }

    public String getResMsg() {
        return resMsg;
    }

    public void setResMsg(String resMsg) {
        this.resMsg = resMsg;
    }

    public Object getResObj() {
        return resObj;
    }

    public void setResObj(Object resObj) {
        this.resObj = resObj;
    }
}