package com.example.serverdemo.base.util;

import org.apache.http.HttpHost;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.List;
import java.util.zip.GZIPOutputStream;

/**
 * @author : Lujw
 * @Class Name   : HttpClientUtil
 * @Description : httpclient 封装的工具类
 * @Project : serverdemo
 * @Program : com.example.serverdemo.base.util
 * @date : 2019/12/10 16:09
 * @ModificationHistory Who       When        What
 * -----------------------------------------
 * Lujw   2019/12/10      创建
 */
public class HttpClientUtil {
   /**
    * @description   :  post方式调用,提交一个数据,设置数据编码
    * @method_name   : post
    * @param         : [url, content, userName, pwds, contentCode, charsetCode, proxyIp, proxyPort]
    * @return        : java.lang.String
    * @throws        :
    * @date          : 2019/12/10 16:18
    * @author        : Lujw
    * @update date   :
    * @update author :
    */
    public static String post(String url, String content, String userName, String pwds, String contentCode, String charsetCode, String proxyIp, String proxyPort) throws Exception{
        HttpClientSslUtil hcsu = new HttpClientSslUtil();
        HttpPost post = new HttpPost(url);

        StringEntity reqEntity = new StringEntity(content, contentCode);
        post.setEntity(reqEntity);

        return hcsu.getOrPost(userName, pwds, post, charsetCode, proxyIp, proxyPort);
    }

    /**
     * @description   : post方式调用,提交一个数据
     * @method_name   : post
     * @param         : [url, content, userName, pwds, charsetCode, proxyIp, proxyPort]
     * @return        : java.lang.String
     * @throws        :
     * @date          : 2019/12/10 16:18
     * @author        : Lujw
     * @update date   :
     * @update author :
     */
    public static String post(String url, String content, String userName, String pwds, String charsetCode, String proxyIp, String proxyPort) throws Exception{
        HttpClientSslUtil hcsu = new HttpClientSslUtil();
        HttpPost post = new HttpPost(url);

        StringEntity reqEntity = new StringEntity(content);
        post.setEntity(reqEntity);

        return hcsu.getOrPost(userName, pwds, post, charsetCode, proxyIp, proxyPort);
    }

    /**
     * @description   : https带证书
     * @method_name   : post
     * @param         : [url, content, userName, pwds, charsetCode, proxyIp, proxyPort, inputStream, certificateKeyStore]
     * @return        : java.lang.String
     * @throws        :
     * @date          : 2019/12/10 16:18
     * @author        : Lujw
     * @update date   :
     * @update author :
     */
    public static String post(String url, String content, String userName, String pwds, String charsetCode,
                              String proxyIp, String proxyPort, InputStream inputStream, String certificateKeyStore) throws Exception{
        HttpClientSslUtil hcsu = new HttpClientSslUtil();
        HttpPost post = new HttpPost(url);
        StringEntity reqEntity = new StringEntity(content);
        RequestConfig config = null;
        if(BaseUtil.stringNotNull(proxyIp) && BaseUtil.stringNotNull(proxyPort)) {
            config = RequestConfig.custom()
                    .setProxy(new HttpHost(proxyIp, Integer.parseInt(proxyPort), "http"))
                    .setConnectTimeout(60000).build();
        } else {
            config = RequestConfig.custom().setConnectTimeout(60000).build();
        }
        post.setConfig(config);
        post.setEntity(reqEntity);

        return hcsu.getOrPost(userName, pwds, post, charsetCode, proxyIp, proxyPort, inputStream, certificateKeyStore);
    }

    /**
     * @description   : post方式调用，提交一组数据
     * @method_name   : post
     * @param         : [url, params, userName, pwds, charsetCode, proxyIp, proxyPort]
     * @return        : java.lang.String
     * @throws        :
     * @date          : 2019/12/10 16:19
     * @author        : Lujw
     * @update date   :
     * @update author :
     */
    public static String post(String url, List<NameValuePair> params, String userName, String pwds, String charsetCode, String proxyIp, String proxyPort) throws Exception{
        HttpClientSslUtil hcsu = new HttpClientSslUtil();
        HttpPost post = new HttpPost(url);

        post.setEntity(new UrlEncodedFormEntity(params, charsetCode));
        return hcsu.getOrPost(userName, pwds, post, charsetCode, proxyIp, proxyPort);
    }

    /**
     * @description   :使用post方式，并且gzip压缩数据
     * @method_name   : postGzip
     * @param         : [url, content, userName, pwds, charsetCode, proxyIp, proxyPort]
     * @return        : java.lang.String
     * @throws        :
     * @date          : 2019/12/10 16:19
     * @author        : Lujw
     * @update date   :
     * @update author :
     */
    public static String postGzip(String url, String content, String userName, String pwds, String charsetCode, String proxyIp, String proxyPort) throws Exception{

        HttpClientSslUtil hcsu = new HttpClientSslUtil();
        HttpPost post = new HttpPost(url);

        post.setHeader("Content-Encoding", "gzip");
        post.setHeader("Accept-Encoding", "gzip, deflate");

        ByteArrayOutputStream originalContent = new ByteArrayOutputStream();
        originalContent.write(content.getBytes(Charset.forName(charsetCode)));

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        GZIPOutputStream gzipOut = new GZIPOutputStream(baos);
        originalContent.writeTo(gzipOut);
        gzipOut.finish();

        ByteArrayEntity by = new ByteArrayEntity(baos.toByteArray(), ContentType.create("text/plain", charsetCode));

        post.setEntity(by);

        return hcsu.getOrPost(userName, pwds, post, charsetCode, proxyIp, proxyPort);
    }

   /**
    * @description   : 使用get方式调用
    * @method_name   : get
    * @param         : [url, charsetCode, proxyIp, proxyPort]
    * @return        : java.lang.String
    * @throws        :
    * @date          : 2019/12/10 16:19
    * @author        : Lujw
    * @update date   :
    * @update author :
    */
    public static String get(String url, String charsetCode, String proxyIp, String proxyPort) throws Exception{
        HttpClientSslUtil hcu = new HttpClientSslUtil();

        HttpGet httpget = new HttpGet(url);

        return hcu.getOrPost(httpget, charsetCode, proxyIp, proxyPort);
    }

}