package com.example.serverdemo.module.encryptAndDecode.po;

import org.springframework.data.annotation.Id;

/**
 * @author : Lujw
 * @Class Name   : EncryPo
 * @Description : TODO
 * @Project : serverdemo
 * @date : 2019/12/10 18:20
 * @ModificationHistory Who       When        What
 * -----------------------------------------
 * Lujw   2019/12/10      创建
 */
public class EncryPo {
    //主键
    @Id
    private String _id;

    //秘钥类型
    private String secretKeyType;

    //秘钥
    private String secretKey;

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

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