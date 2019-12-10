package com.example.serverdemo.module.encryptAndDecode.vo;

/**
 * @author : Lujw
 * @Class Name   : secretKeyVo
 * @Description : TODO
 * @date : 2019/7/5 11:53
 * @ModificationHistory Who       When        What
 * -----------------------------------------
 * Lujw   2019/7/5      创建
 */
public class SecretKeyVo {

    private String secretKeyType;

    private String secretKey;

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