package com.example.serverdemo.module.fileToBase64.service.impl;

import com.example.serverdemo.base.exception.TopException;
import com.example.serverdemo.base.util.BaseUtil;
import com.example.serverdemo.module.fileToBase64.service.FileToBase64Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Base64;
import java.util.Random;

/**
 * @author : Lujw
 * @Class Name   : FileToBase64Service
 * @Description : TODO
 * @date : 2019/10/28 11:30
 * @ModificationHistory Who       When        What
 * -----------------------------------------
 * Lujw   2019/10/28      创建
 */
@Service("fileToBase64Service")
public class FileToBase64Serviceimpl implements FileToBase64Service {
    private static final Logger LOGGER = LoggerFactory.getLogger(FileToBase64Serviceimpl.class);
    
    /**
     * @description   : 文件转Base64
     * @method_name   : fileToBase64
     * @param         : [filePath]
     * @return        : java.lang.String  
     * @throws        : 
     * @date          : 2019/10/28 11:34
     * @author        : Lujw
     * @update date   :
     * @update author :
     */
    @Override
    public String fileToBase64(String filePath) throws TopException {
        if (!BaseUtil.stringNotNull(filePath)) {
            LOGGER.error("文件转Base64失败，文件路径为空");
           throw new TopException("文件路径为空","文件转Base64失败");
        }
        try {
            byte[] b = Files.readAllBytes(Paths.get(filePath));
            return Base64.getEncoder().encodeToString(b);
        } catch (IOException e) {
            LOGGER.error("文件转Base64失败",e);
            throw new TopException("文件转Base64失败","文件转Base64失败");
        }
    }

    /**
     * @description   : Base64转文件
     * @method_name   : base64ToFile
     * @param         : [base64, filePath]
     * @return        : void
     * @throws        : 
     * @date          : 2019/10/28 11:40
     * @author        : Lujw
     * @update date   :
     * @update author :
     */
    @Override
    public String base64ToFile(String base64Text, String fileDownloadPath,String fileName)throws TopException{
        if (!BaseUtil.stringNotNull(base64Text)) {
            LOGGER.error("Base64转文件失败，Base64字符串为空");
            throw new TopException("Base64字符串为空","文件解码失败");
        }
        File file=new File(fileDownloadPath);
        if(!file.exists()){
            file.mkdirs();
        }

        //将base64转成文件
        Path path;
        try {
            path= Files.write(Paths.get(fileDownloadPath+fileName), Base64.getDecoder().decode(base64Text.replaceAll("\r|\n| ","")),StandardOpenOption.CREATE);
        } catch (IOException e) {
            LOGGER.error("Base64转文件失败,详情：{}",e);
            throw new TopException("Base64转文件失败","文件解码失败");
        }
        String str=path.toString();

        //dev
        String fileDownloadUrl="C:\\Users\\fei\\Desktop\\download\\"+ str.substring(str.lastIndexOf("\\")+1,str.length());

        //pro
//        String fileDownloadUrl= "lujw.xyz:9000/prod/download/1576049727946..png"+ str.substring(str.lastIndexOf("\\")+1,str.length());


        LOGGER.info("《------------------文件下载地址，fileDownloadUrl:{}",fileDownloadUrl);

        //将url返给前台，交给前台去下载。不使用后台下载
        return fileDownloadUrl;
        //将服务器中文件下载本地到指定目录（后台下载）
        //downloadFile(fileDownloadUrl,localPath);
    }
    
    /**
     * @description   : 下载文件(网络下载)
     * @method_name   : downloadFile
     * @param         : [urlPath, targetDirectory]
     * @return        : void  
     * @throws        : 
     * @date          : 2019/10/28 13:45
     * @author        : Lujw
     * @update date   :
     * @update author :
     */
    private void downloadFile(String fileDownloadUrl , String localPath) throws TopException {
        LOGGER.info("开始下载文件。。。。。");
        // 解决url中可能有中文情况
        OutputStream out = null;
        InputStream inputStream=null;
        HttpURLConnection http=null;
        URL url;
        try{
            url = new URL(fileDownloadUrl);
            LOGGER.info("url:{}",url);
        }catch (Exception e){
            LOGGER.error("url不正确，请确认url为网络url。详情：{}",e);
            throw new TopException("url不正确，请确认url为网络url","文件解码失败");
        }
        try{

        http = (HttpURLConnection)url.openConnection();
        http.setConnectTimeout(3000);
        // 设置 User-Agent 避免被拦截
        http.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 9.0; Windows NT 6.1; Trident/5.0)");
        String contentType = http.getContentType();
        LOGGER.info("contentType:{}",contentType);

        // 获取文件大小
        long length = http.getContentLengthLong();
        LOGGER.info("文件大小:{}KB",(length / 1024));
        // 获取文件名
        String fileName = getFileName(http , fileDownloadUrl);
        inputStream = http.getInputStream();
        byte[] buff = new byte[1024*10];
        out = new FileOutputStream(new File(localPath,fileName));
        int len ;
            while((len = inputStream.read(buff)) != -1) {
            out.write(buff, 0, len);
            out.flush();
            }
        }catch (Exception e){
            LOGGER.error("文件下载失败，详情：{}",e);
            throw new TopException("文件下载失败","文件解码失败");
        }finally {
            try{
                // 关闭资源
                out.close();
                inputStream.close();
                http.disconnect();
            }catch (Exception e){
                LOGGER.error("文件下载失败,关闭文件流出错");
                throw new TopException("关闭文件流出错","文件解码失败");
            }
        }
    }

    /**
     * @description   : 获取文件名
     * @method_name   : getFileName
     * @param         : [http, urlPath]
     * @return        : java.lang.String  
     * @throws        : 
     * @date          : 2019/10/28 13:44
     * @author        : Lujw
     * @update date   :
     * @update author :
     */
    private String getFileName(HttpURLConnection http, String urlPath) throws TopException {
        String headerField = http.getHeaderField("Content-Disposition");
        String fileName = null ;
        try{
            if(null != headerField) {
                String decode = URLDecoder.decode(headerField, "UTF-8");
                fileName = decode.split(";")[1].split("=")[1].replaceAll("\"", "");
                LOGGER.info("文件名是:{}",fileName);
            }
        }catch (Exception e){
            LOGGER.error("获取文件名出错");
            throw new TopException("获取文件名出错","文件下载失败");
        }

        if(null == fileName) {
            // 尝试从url中获取文件名
            String[] arr  = urlPath.split("/");
            fileName = arr[arr.length - 1];
            LOGGER.info("url中获取文件名:{}",fileName);
        }
        return fileName;
    }
}