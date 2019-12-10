package com.example.serverdemo.module.userManage.constant;

import java.util.ArrayList;
import java.util.List;

/**
 * 菜单列表
 */
public enum  Menu {
    // 用户管理
    CUSTOMER_USER_MANAGE(1,"用户管理","userManage"),
    // 加解密
    ENCRY_AND_DECRY(2,"加密/解密","encryAndDecry"),
    //文件转Base64
    FILE_TO_BASE64(3,"文件编/解码","fileToBase64");

    private int code;

    private String name;

    private String path;

    Menu(int code, String name,String path) {
        this.code = code;
        this.name = name;
        this.path = path;
    }

    //get set
    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

  /**
   * @description   : 通过菜单名 查找菜单对应代码
   * @method_name   : getNameByCode
   * @param         : [code]
   * @return        : java.lang.String
   * @throws        :
   * @date          : 2019/12/10 17:21
   * @author        : Lujw
   * @update date   :
   * @update author :
   */
    public static String getNameByCode(int code) {
        for (Menu me : Menu.values()) {
            if(me.getCode() == code){
                return me.getName();
            }
        }
        return "不存在";
    }

   /**
    * @description   : 通过菜单名 查找菜单对应路径
    * @method_name   : getPathByCode
    * @param         : [code]
    * @return        : java.lang.String
    * @throws        :
    * @date          : 2019/12/10 17:21
    * @author        : Lujw
    * @update date   :
    * @update author :
    */
    public static String getPathByCode(int code) {
        for (Menu me : Menu.values()) {
            if(me.getCode() == code){
                return me.getPath();
            }
        }
        return "不存在";
    }

    /**
     * @description   : 获得所有菜单代码
     * @method_name   : getAllCodes
     * @param         : []
     * @return        : java.util.List<java.lang.Integer>
     * @throws        :
     * @date          : 2019/12/10 17:21
     * @author        : Lujw
     * @update date   :
     * @update author :
     */
    public static List<Integer> getAllCodes(){
        List<Integer> list = new ArrayList<Integer>();
        for (Menu me : Menu.values()) {
            list.add(me.getCode());
        }
        return list;
    }
}
