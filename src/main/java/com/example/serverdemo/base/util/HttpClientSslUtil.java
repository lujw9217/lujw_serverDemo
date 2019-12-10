package com.example.serverdemo.base.util;

import org.apache.http.*;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.Credentials;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContexts;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.net.ssl.SSLContext;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.KeyStore;
import java.util.zip.GZIPInputStream;

/**
 * @author : Lujw
 * @Class Name   : HttpClientSslUtil
 * @Description : httpclient工具ssl方式
 * @Project : serverdemo
 * @Program : com.example.serverdemo.base.util
 * @date : 2019/12/10 16:09
 * @ModificationHistory Who       When        What
 * -----------------------------------------
 * Lujw   2019/12/10      创建
 */
public class HttpClientSslUtil {

    private static Logger logger = LoggerFactory.getLogger(HttpClientSslUtil.class);

    /**链接超时时间(单位：ms)*/
    private static int connectionTimeOut = 60000;
    /**请求超时时间(单位：ms)*/
    private static int requestTimeOut = 120000;

   /**
    * @description   : 带账号 密码访问
    * @method_name   : getOrPost
    * @param         : [userName, pwds, method, charsetName, proxyIp, proxyPort]
    * @return        : java.lang.String
    * @throws        :
    * @date          : 2019/12/10 16:12
    * @author        : Lujw
    * @update date   :
    * @update author :
    */
    public String getOrPost(String userName, String pwds, HttpRequestBase method, String charsetName, String proxyIp, String proxyPort) throws Exception{
        // 初始化HttpClient的实例
        HttpClient httpClient = initHttpClient(userName, pwds, proxyIp, proxyPort);
        return excuteMethod(httpClient, method, charsetName);
    }

   /**
    * @description   : 带证书访问https
    * @method_name   : getOrPost
    * @param         : [userName, pwds, method, charsetName, proxyIp, proxyPort, instream, certificateKeyStore]
    * @return        : java.lang.String
    * @throws        :
    * @date          : 2019/12/10 16:12
    * @author        : Lujw
    * @update date   :
    * @update author :
    */
    public String getOrPost(String userName, String pwds, HttpRequestBase method, String charsetName,
                            String proxyIp, String proxyPort, InputStream instream, String certificateKeyStore) throws Exception{
        // 初始化HttpClient的实例
        HttpClient httpClient = initHttpClient(userName, pwds, proxyIp, proxyPort, instream, certificateKeyStore);
        return excuteMethod(httpClient, method, charsetName);
    }

 /**
  * @description   : 不带账号密码访问
  * @method_name   : getOrPost
  * @param         : [method, charsetName, proxyIp, proxyPort]
  * @return        : java.lang.String
  * @throws        :
  * @date          : 2019/12/10 16:12
  * @author        : Lujw
  * @update date   :
  * @update author :
  */
    public String getOrPost(HttpRequestBase method, String charsetName, String proxyIp, String proxyPort) throws Exception{
        // 初始化HttpClient的实例
        HttpClient httpClient = initHttpClient(null, null, proxyIp, proxyPort);
        return excuteMethod(httpClient, method, charsetName);
    }

   /**
    * @description   : 初始化httpclient对象
    * @method_name   : initHttpClient
    * @param         : [userName, pwds, proxyIp, proxyPort]
    * @return        : HttpClient
    * @throws        :
    * @date          : 2019/12/10 16:12
    * @author        : Lujw
    * @update date   :
    * @update author :
    */
    private HttpClient initHttpClient(String userName, String pwds, String proxyIp, String proxyPort){
        //初始化获取HttpClientBuilder
        HttpClientBuilder hb = initHttpClientBuilder(userName, pwds, proxyIp, proxyPort);
        return hb.build();
    }

   /**
    * @description   :  初始化httpclient对象,带证书
    * @method_name   : initHttpClient
    * @param         : [userName, pwds, proxyIp, proxyPort, instream, certificateKeyStore]
    * @return        : HttpClient
    * @throws        :
    * @date          : 2019/12/10 16:13
    * @author        : Lujw
    * @update date   :
    * @update author :
    */
    private HttpClient initHttpClient(String userName, String pwds, String proxyIp, String proxyPort,
                                      InputStream instream, String certificateKeyStore) throws Exception{

        SSLContext sslcontext;
        try {
            //指定读取证书格式为PKCS12
            KeyStore keyStore = KeyStore.getInstance("PKCS12");
            //指定PKCS12的密码(商户ID)
            keyStore.load(instream, certificateKeyStore.toCharArray());

            sslcontext = SSLContexts.custom().
                    loadKeyMaterial(keyStore, certificateKeyStore.toCharArray()).build();
        } catch (Exception e) {
            logger.error("SSL证书方式调用错误，PKCS12密码加载错误或SSLcontext内容构建错误:" + BaseUtil.toJSON(e));
            throw new Exception("SSL证书方式调用错误，PKCS12密码加载错误或SSLcontext内容构建错误:"  + e.getMessage());
        } finally{
            instream.close();
        }
        logger.info("HTTPS会话SSLContext对象:" + BaseUtil.toJSON(sslcontext));
        //指定TLS版本
        SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslcontext, new String[] { "TLSv1"}, null, SSLConnectionSocketFactory.getDefaultHostnameVerifier());

        //初始化获取HttpClientBuilder
        HttpClientBuilder hb = initHttpClientBuilder(userName, pwds, proxyIp, proxyPort);
        //设置SSL
        hb.setSSLSocketFactory(sslsf);

        return hb.build();
    }

    /**
     * @description   : 初始化HttpClientBuilder
     * @method_name   : initHttpClientBuilder
     * @param         : [userName, pwds, proxyIp, proxyPort]
     * @return        : HttpClientBuilder
     * @throws        :
     * @date          : 2019/12/10 16:13
     * @author        : Lujw
     * @update date   :
     * @update author :
     */
    private HttpClientBuilder initHttpClientBuilder(String userName, String pwds, String proxyIp, String proxyPort){
        HttpClientBuilder hb = HttpClients.custom();

        //设置到相应参数
        RequestConfig defaultRequestConfig = RequestConfig.custom().
                setConnectTimeout(connectionTimeOut).// 连接时间20s
                setSocketTimeout(requestTimeOut).// 数据传输时间60s
                build();
        hb.setDefaultRequestConfig(defaultRequestConfig);

        // 设置请求用户名和密码
        if (BaseUtil.stringNotNull(userName) && BaseUtil.stringNotNull(pwds)) {
            CredentialsProvider credsProvider = new BasicCredentialsProvider();
            Credentials credentials = new UsernamePasswordCredentials(userName, pwds);
            credsProvider.setCredentials(new AuthScope(AuthScope.ANY), credentials);
            hb.setDefaultCredentialsProvider(credsProvider);
        }

        //设置代理服务器的ip地址和端口
        if(BaseUtil.stringNotNull(proxyIp) && BaseUtil.stringNotNull(proxyPort)){
            //验证代理 ip 端口 格式是否正确
            HttpHost proxy = new HttpHost(proxyIp, Integer.parseInt(proxyPort));
            hb.setProxy(proxy);
        }

        return hb;
    }


    /**
     * @description   : 提交请求
     * @method_name   : excuteMethod
     * @param         : [httpClient, method, charsetName]
     * @return        : java.lang.String
     * @throws        :
     * @date          : 2019/12/10 16:13
     * @author        : Lujw
     * @update date   :
     * @update author :
     */
    private String excuteMethod(HttpClient httpClient, HttpRequestBase method, String charsetName) throws Exception{
        // 使用系统提供的默认的恢复策略
        try {

            HttpResponse response ;

            try{
                response = httpClient.execute(method);
            }catch(ConnectTimeoutException e){
                logger.info("httpclient 连接超时!", e);
                String exMsg = e.getMessage();

                if(exMsg.contains("Connect to ") && exMsg.contains(" timed out")){
                    exMsg = exMsg.replace("Connect to ", "").replace(" timed out", "");

                    if(exMsg.contains(":")){
                        String[] temp = exMsg.split(":");
                        logger.info("防火墙，httpclient连接超时，ping域名信息为：" + PingUtils.ping(temp[0]));
                    }
                }
                throw e;
            }

            HttpEntity entity = response.getEntity();

            int statusCode = response.getStatusLine().getStatusCode();

            // 执行Method
            if (statusCode != HttpStatus.SC_OK) {

                logger.info("-----Invoke http Method Failed, HttpStatus = " + statusCode);
                /** client返回数据错误，请联系管理员！ */
                throw new Exception("接口响应错误！[" + statusCode + "]");
            }


            Header contentEncoding = entity.getContentEncoding();

            String respStr;

            if(contentEncoding != null
                    && contentEncoding.getValue().toLowerCase().indexOf("gzip") > -1){
                //压缩传输
                GZIPInputStream gps = new GZIPInputStream(entity.getContent());
                // 读取内容
                BufferedReader reader = new BufferedReader(new InputStreamReader(gps, charsetName));
                StringBuffer stringBuffer = new StringBuffer();
                String str;
                while((str = reader.readLine())!=null){
                    stringBuffer.append(str);
                }
                respStr = stringBuffer.toString();
                reader.close();
                gps.close();
            }else{
                // 读取内容
                BufferedReader reader = new BufferedReader(new InputStreamReader(entity.getContent(), charsetName));
                StringBuffer stringBuffer = new StringBuffer();
                String str;
                while((str = reader.readLine())!=null){
                    stringBuffer.append(str);
                }
                respStr = stringBuffer.toString();
                reader.close();
            }
            return respStr;
        } finally {
            // 释放连接
            method.releaseConnection();
        }
    }

//    public static void main(String[] args) throws Exception {
//        HttpClientSslUtil hcu = new HttpClientSslUtil();
//
//        //get方式，将查询字符串作为请求地址的一部分
//        //这是一种最简单的传参方式，将查询参数用(&)连接，然后放在请求地址?的后面
//        String url = "http://api.weixin.qq.com?name=xx&code=123";
//        HttpGet httpget = new HttpGet(url);
//        String get = hcu.getOrPost(null, null, httpget, "UTF-8", "172.17.18.80", "8080");
//        System.out.println(get);
//
//
//
//        HttpPost httpPost = new HttpPost(url);
//
//        //post 提交一个字符串参数，可使用以下方式
//        String content = "";
//        StringEntity reqEntity = new StringEntity(content);
//        httpPost.setEntity(reqEntity);
//
//        String postString = hcu.getOrPost(null, null, httpPost, "UTF-8", "172.17.18.80", "8080");
//        System.out.println(postString);
//
//        //post 提交多个参数，使用以下方式
//        List<NameValuePair> params = new ArrayList<NameValuePair>();
//
//        params.add(new BasicNameValuePair("username", "admin"));
//
//        params.add(new BasicNameValuePair("password", "123456"));
//
//        httpPost.setEntity(new UrlEncodedFormEntity(params,"UTF-8"));
//
//        String postParams = hcu.getOrPost(null, null, httpPost, "UTF-8", "172.17.18.80", "8080");
//        System.out.println(postParams);
//
//    }
}