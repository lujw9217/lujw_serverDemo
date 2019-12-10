package com.example.serverdemo.base.util;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

/**
 * @author : Lujw
 * @Class Name   : MD5Util
 * @Description : MD5加密算法工具类
 * @Project : serverdemo
 * @Program : com.example.serverdemo.base.util
 * @date : 2019/12/10 16:10
 * @ModificationHistory Who       When        What
 * -----------------------------------------
 * Lujw   2019/12/10      创建
 */
public class MD5Util {
    //混淆字符
    private static final String hexDigits[] = {"0", "1", "2", "3", "4", "5",
            "6", "7", "8", "9", "a", "b", "c", "d", "e", "f"};
    private static MessageDigest md5 = null;
    public final static String KEY_AES = "AES";
    private final static String defaultCharset = "UTF-8";

    private static String byteArrayToHexString(byte b[]) {
        StringBuffer resultSb = new StringBuffer();
        for (int i = 0; i < b.length; i++)
            resultSb.append(byteToHexString(b[i]));

        return resultSb.toString();
    }

    private static String byteToHexString(byte b) {
        int n = b;
        if (n < 0)
            n += 256;
        int d1 = n / 16;
        int d2 = n % 16;
        return hexDigits[d1] + hexDigits[d2];
    }

    public static String MD5Encode(String origin, String charsetname) {
        String resultString = null;
        try {
            resultString = new String(origin);
            MessageDigest md = MessageDigest.getInstance("MD5");
            if (charsetname == null || "".equals(charsetname))
                resultString = byteArrayToHexString(md.digest(resultString
                        .getBytes()));
            else
                resultString = byteArrayToHexString(md.digest(resultString
                        .getBytes(charsetname)));
        } catch (Exception exception) {
            resultString = "MD5加密错误";
        }
        return resultString;
    }

    /**
     * md5加密
     *
     * @param bytes
     * @return
     */
    public static String md5(byte[] bytes) {
        //用于储存加密后的字符串
        StringBuffer sb = new StringBuffer();
        try {
            md5 = MessageDigest.getInstance("md5");
            byte[] digest = md5.digest(bytes);
            //处理为16进制的字符串
            for (byte bb : digest) {
                //15的8进制是0000,1111
                sb.append(hexDigits[(bb >> 4) & 15]);
                sb.append(hexDigits[bb & 15]);
            }
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return sb.toString();
    }

    /**
     * @param : [data, key, mode]
     * @return : java.lang.String
     * @throws :
     * @description : 加解密
     * @method_name : doAES
     * @date : 2019/12/10 18:05
     * @author : Lujw
     * @update date   :
     * @update author :
     */
    private static String doAES(String data, String key, int mode) {
        try {
            if (data == null || data.equals("") || key == null || key.equals("")) {
                return null;
            }
            //判断是加密还是解密
            boolean encrypt = mode == Cipher.ENCRYPT_MODE;
            byte[] content;
            //true 加密内容 false 解密内容
            if (encrypt) {
                content = data.getBytes(defaultCharset);
            } else {
                content = parseHexStr2Byte(data);
            }
            //1.构造密钥生成器，指定为AES算法,不区分大小写
            KeyGenerator kgen = KeyGenerator.getInstance(KEY_AES);
            //2.根据ecnodeRules规则初始化密钥生成器
            //生成一个128位的随机源,根据传入的字节数组
            kgen.init(128, new SecureRandom(key.getBytes()));
            //3.产生原始对称密钥
            SecretKey secretKey = kgen.generateKey();
            //4.获得原始对称密钥的字节数组
            byte[] enCodeFormat = secretKey.getEncoded();
            //5.根据字节数组生成AES密钥
            SecretKeySpec keySpec = new SecretKeySpec(enCodeFormat, KEY_AES);
            //6.根据指定算法AES自成密码器
            Cipher cipher = Cipher.getInstance(KEY_AES);// 创建密码器
            //7.初始化密码器，第一个参数为加密(Encrypt_mode)或者解密解密(Decrypt_mode)操作，第二个参数为使用的KEY
            cipher.init(mode, keySpec);// 初始化
            byte[] result = cipher.doFinal(content);
            if (encrypt) {
                //将二进制转换成16进制
                return parseByte2HexStr(result);
            } else {
                return new String(result, defaultCharset);
            }
        } catch (Exception e) {
            // logger.error("AES 密文处理异常", e);
        }
        return null;
    }

    /**
     * @param buf
     * @return
     * @description 将二进制转换成16进制
     */
    public static String parseByte2HexStr(byte buf[]) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < buf.length; i++) {
            String hex = Integer.toHexString(buf[i] & 0xFF);
            if (hex.length() == 1) {
                hex = '0' + hex;
            }
            sb.append(hex.toUpperCase());
        }
        return sb.toString();
    }

    /**
     * @param hexStr
     * @return
     * @description 将16进制转换为二进制
     */
    public static byte[] parseHexStr2Byte(String hexStr) {
        if (hexStr.length() < 1)
            return null;
        byte[] result = new byte[hexStr.length() / 2];
        for (int i = 0; i < hexStr.length() / 2; i++) {
            int high = Integer.parseInt(hexStr.substring(i * 2, i * 2 + 1), 16);
            int low = Integer.parseInt(hexStr.substring(i * 2 + 1, i * 2 + 2), 16);
            result[i] = (byte) (high * 16 + low);
        }
        return result;
    }


}