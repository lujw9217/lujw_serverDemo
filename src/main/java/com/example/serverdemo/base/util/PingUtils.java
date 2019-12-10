package com.example.serverdemo.base.util;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * @author : Lujw
 * @Class Name   : PingUtils
 * @Description : MD5加密算法工具类
 * @Project : serverdemo
 * @Program : com.example.serverdemo.base.util
 * @date : 2019/12/10 16:10
 * @ModificationHistory Who       When        What
 * -----------------------------------------
 * Lujw   2019/12/10      创建
 */
public class PingUtils {
    public static Domain ping(String domian) {

        long start = System.currentTimeMillis();

        Domain result = new Domain();

        try {

            InetAddress address = InetAddress.getByName(domian);

            result.ip = address.getHostAddress();

            result.host = address.getHostName();

            long end = System.currentTimeMillis();

            result.time = (end - start);

        } catch (UnknownHostException e) {

            result.ip = "0.0.0.0";

            result.host = "UNKONW";
        }

        return result;
    }


    public static class Domain {

        String ip;

        String host;
        long time;

        @Override
        public String toString() {
            return String.format("host=%s, ip=%s, time=%s", host, ip, time);
        }
    }
}