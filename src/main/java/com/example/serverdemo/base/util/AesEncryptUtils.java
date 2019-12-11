package com.example.serverdemo.base.util;

import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.util.Base64;

/**
 * @author : Lujw
 * @Class Name   : AesEncryptUtils
 * @Description : AES 对称加密
 * @Project : serverdemo
 * @Program : com.example.serverdemo.base.util
 * @date : 2019/12/10 15:57
 * @ModificationHistory Who       When        What
 * -----------------------------------------
 * Lujw   2019/12/10      创建
 */
public class AesEncryptUtils {

   /**
    * @description   : 对称加密
    * @method_name   : aesEncrypt
    * @param         : [key, content]
    * @return        : java.lang.String
    * @throws        :
    * @date          : 2019/12/10 15:57
    * @author        : Lujw
    * @update date   :
    * @update author :
    */
    public static String aesEncrypt(String key, String content) throws Exception {

        if(!BaseUtil.stringNotNull(key) || key.length() != 32){
            throw new Exception("key不能为空且必须为32位！");
        }

        if(!BaseUtil.stringNotNull(content)){
            throw new Exception("被加密数据不能为空！");
        }

        //将key进行base64位转码
        key = Base64.getEncoder().encodeToString(key.getBytes());

        // 取密钥和偏转向量
        byte[] privateKey = new byte[16];
        byte[] iv = new byte[16];
        getKeyIV(key, privateKey, iv);

        // 两个参数，第一个为私钥字节数组，
        // 第二个为加密方式 AES或者DES
        Key keySpec = new SecretKeySpec(privateKey, "AES");
        //偏移向量
        IvParameterSpec ivSpec = new IvParameterSpec(iv);

        // 使用Cipher的实例
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        // 初始化加密操作,传递加密的钥匙
        cipher.init(Cipher.ENCRYPT_MODE, keySpec, ivSpec);

        // 返回加密串，进行64位转码
        return Base64.getEncoder().encodeToString(cipher.doFinal(content
                .getBytes("UTF-8")));
    }

    public static void main(String[] args) throws Exception {
        System.out.println(aesEncrypt("CptLOOh9ijGZyo8uJppot4ojH35cTTPc","CptLOOh9ijGZyo8uJppot4ojH35cTTPc"));
    }

    /**
     * @description   : 对称解密
     * @method_name   : aesDecrypt
     * @param         : [key, encryptStr]
     * @return        : java.lang.String
     * @throws        :
     * @date          : 2019/12/10 15:57
     * @author        : Lujw
     * @update date   :
     * @update author :
     */
    public static String aesDecrypt(String key, String encryptStr) throws Exception, NoSuchPaddingException {

        if(!BaseUtil.stringNotNull(key) || key.length() != 32){
            throw new Exception("key不能为空且必须为32位！");
        }

        if(!BaseUtil.stringNotNull(encryptStr)){
            throw new Exception("被解密数据不能为空！");
        }

        //将key进行base64位转码
        key = Base64.getEncoder().encodeToString(key.getBytes());

        // base64解码
        byte[] encBuf = Base64.getDecoder().decode(encryptStr);

        // 取密钥和偏转向量
        byte[] privateKey = new byte[16];
        byte[] iv = new byte[16];
        getKeyIV(key, privateKey, iv);

        // 两个参数，第一个为私钥字节数组，
        // 第二个为加密方式 AES或者DES
        Key keySpec = new SecretKeySpec(privateKey, "AES");
        //偏移向量
        IvParameterSpec ivSpec = new IvParameterSpec(iv);

        // 使用Cipher的实例
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        // 初始化加密操作,传递加密的钥匙
        cipher.init(Cipher.DECRYPT_MODE, keySpec, ivSpec);

        // 返回加密串
        return new String(cipher.doFinal(encBuf), "UTF-8");
    }

   /**
    * @description   : 获取加密，需要的私钥、偏移向量
    * @method_name   : getKeyIV
    * @param         : [encryptKey, key, iv]
    * @return        : void
    * @throws        :
    * @date          : 2019/12/10 15:58
    * @author        : Lujw
    * @update date   :
    * @update author :
    */
    public static void getKeyIV(String encryptKey, byte[] key, byte[] iv) throws Exception {
        // 密钥Base64解密
        byte[] buf = Base64.getDecoder().decode(encryptKey);

        // 前8位为key
        int i;
        for (i = 0; i < key.length; i++) {
            key[i] = buf[i];
        }
        // 后8位为iv向量
        for (i = 0; i < iv.length; i++) {
            iv[i] = buf[i + key.length];
        }
    }

    /**
     * @description   :  aes url 加密
     * @method_name   : aesUrlEncrypt
     * @param         : [key, content]
     * @return        : java.lang.String
     * @throws        :
     * @date          : 2019/12/10 15:58
     * @author        : Lujw
     * @update date   :
     * @update author :
     */
    public static String aesUrlEncrypt(String key, String content) throws Exception {

        if(!BaseUtil.stringNotNull(key) || key.length() != 32){
            throw new Exception("key不能为空且必须为32位！");
        }

        if(!BaseUtil.stringNotNull(content)){
            throw new Exception("被加密数据不能为空！");
        }

        //将key进行base64位转码
        key = Base64.getUrlEncoder().encodeToString(key.getBytes());

        // 取密钥和偏转向量
        byte[] privateKey = new byte[16];
        byte[] iv = new byte[16];
        getKeyIV(key, privateKey, iv);

        // 两个参数，第一个为私钥字节数组，
        // 第二个为加密方式 AES或者DES
        Key keySpec = new SecretKeySpec(privateKey, "AES");
        //偏移向量
        IvParameterSpec ivSpec = new IvParameterSpec(iv);

        // 使用Cipher的实例
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        // 初始化加密操作,传递加密的钥匙
        cipher.init(Cipher.ENCRYPT_MODE, keySpec, ivSpec);

        // 返回加密串，进行64位转码
        return Base64.getUrlEncoder().encodeToString(cipher.doFinal(content
                .getBytes("UTF-8")));
    }

    /**
     * @description   : aes url 解密
     * @method_name   : aesUrlDecrypt
     * @param         : [key, encryptStr]
     * @return        : java.lang.String
     * @throws        :
     * @date          : 2019/12/10 15:58
     * @author        : Lujw
     * @update date   :
     * @update author :
     */
    public static String aesUrlDecrypt(String key, String encryptStr) throws Exception, NoSuchPaddingException{

        if(!BaseUtil.stringNotNull(key) || key.length() != 32){
            throw new Exception("key不能为空且必须为32位！");
        }

        if(!BaseUtil.stringNotNull(encryptStr)){
            throw new Exception("被解密数据不能为空！");
        }

        //将key进行base64位转码
        key = Base64.getUrlEncoder().encodeToString(key.getBytes());

        // base64解码
        byte[] encBuf = Base64.getUrlDecoder().decode(encryptStr);

        // 取密钥和偏转向量
        byte[] privateKey = new byte[16];
        byte[] iv = new byte[16];
        getKeyIV(key, privateKey, iv);

        // 两个参数，第一个为私钥字节数组，
        // 第二个为加密方式 AES或者DES
        Key keySpec = new SecretKeySpec(privateKey, "AES");
        //偏移向量
        IvParameterSpec ivSpec = new IvParameterSpec(iv);

        // 使用Cipher的实例
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        // 初始化加密操作,传递加密的钥匙
        cipher.init(Cipher.DECRYPT_MODE, keySpec, ivSpec);

        // 返回加密串
        return new String(cipher.doFinal(encBuf), "UTF-8");
    }

}