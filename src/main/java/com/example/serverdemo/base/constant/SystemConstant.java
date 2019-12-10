package com.example.serverdemo.base.constant;

/**
 * @author : Lujw
 * @Class Name   : SystemConstant
 * @Description : 系统常量
 * @Project : serverdemo
 * @Program : com.example.serverdemo.constant
 * @date : 2019/12/10 15:08
 * @ModificationHistory Who       When        What
 * -----------------------------------------
 * Lujw   2019/12/10      创建
 */
public class SystemConstant {

    /**
     * 发生错误时，给页面提示的信息变量名
     */
    public static final String PAGE_ERROR_MESSAGE = "PAGE_ERROR_MESSAGE";

    /**
     * 运行时异常 RuntimeException
     */
    public static final String RUN_TIME_EXCEPTION = "数据存储异常，请联系技术人员!";

    /**
     * baseException异常
     */
    public static final String SYS_ERROR_MESSAGE = "未知错误！";

    /**
     * 数据库错误
     */
    public static final String DATASOURCE_ERROR = "数据库错误，请联系管理员！";

    /**
     *数据库连接超时
     */
    public static final String DATA_CONNECT_TIMEOUT = "数据库连接超时，请联系管理员！";

    /**
     * ServiceException 异常
     */
    public static final String BUSINESS_ERROR_MESSAGE = "业务流程错误";

    /**
     * 未登录
     */
    public static final String NO_LOGIN = "未登录，请登录!";

    /**
     * 无权操作，请先登录！
     */
    public static final String NO_AUTHORITY = "无权操作，请先登录！";

    /*** 异常 编码  */
    public static final String SYS_ENCODING = "UTF-8";

    /**http 正文 编码格式**/
    public static final String CONTENT_TYPE_UTF8 = "text/html; charset=UTF-8";
}