package com.example.serverdemo.module.encryptAndDecode.from;

/**
 * @author : Lujw
 * @Class Name   : SecretKeyForm
 * @Description : TODO
 * @date : 2019/7/5 10:32
 * @ModificationHistory Who       When        What
 * -----------------------------------------
 * Lujw   2019/7/5      创建
 */
public class SecretKeyForm {
//  秘钥类型
    private String  secretKeyType;

//  秘钥
    private String  secretKey;

    public String getSecretKeyType() {
        return secretKeyType;
    }

    public void setSecretKeyType(String secretKeyType) {
        this.secretKeyType = secretKeyType;
    }

    public String getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }
}