package com.example.serverdemo.module.encryptAndDecode.from;


public class EncryForm {

    //需加解密内容
    private String encryText;

    //秘钥类型
    private String secretKeyType;

    public String getEncryText() {
        return encryText;
    }

    public void setEncryText(String encryText) {
        this.encryText = encryText;
    }

    public String getSecretKeyType() {
        return secretKeyType;
    }

    public void setSecretKeyType(String secretKeyType) {
        this.secretKeyType = secretKeyType;
    }
}