package com.example.serverdemo.base.exception;

/**
 * @author : Lujw
 * @Class Name   : DebugParamException
 * @Description : 调试参数异常类
 * @Project : serverdemo
 * @Program : com.example.serverdemo.base.exception
 * @date : 2019/12/10 17:04
 * @ModificationHistory Who       When        What
 * -----------------------------------------
 * Lujw   2019/12/10      创建
 */
public class DebugParamException extends Exception{
    private static final long serialVersionUID = 1L;

    public DebugParamException(String msg){
        super(msg);
    }
}