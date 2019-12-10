package com.example.serverdemo.module.encryptAndDecode.constant;

/**
 * @author : Lujw
 * @Class Name   : AesKeyConstant
 * @Description : TODO
 * @date : 2019/7/23 10:07
 * @ModificationHistory Who       When        What
 * -----------------------------------------
 * Lujw   2019/7/23      创建
 */
public class AesKeyConstant {

    //秘钥
    public static String aesKey;

    public static void setAesKey(String aesKey) {
        AesKeyConstant.aesKey = aesKey;
    }
}