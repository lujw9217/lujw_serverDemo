package com.example.serverdemo.module.fileToBase64.util;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

/**
 * @author : Lujw
 * @Class Name   : FileToBase64Util
 * @Description : TODO
 * @date : 2019/10/28 11:23
 * @ModificationHistory Who       When        What
 * -----------------------------------------
 * Lujw   2019/10/28      创建
 */
public class FileToBase64Util {

    /**
     * @param : [file, filePath, fileName]
     * @return : void
     * @throws :
     * @description : 将文件流输出至指定目录
     * @method_name : uploadFile
     * @date : 2019/10/28 11:23
     * @author : Lujw
     * @update date   :
     * @update author :
     */
    public static void uploadFile(byte[] file, String filePath, String fileName) throws Exception {

        File targetFile = new File(filePath);
        if (!targetFile.exists()) {
            targetFile.mkdirs();
        }
        FileOutputStream out = new FileOutputStream(filePath + fileName);
        out.write(file);
        out.flush();
        out.close();
    }

    /**
     * @param : [file]
     * @return : java.lang.String
     * @throws :
     * @description : 获取文件头，将文件流的首位开始，读4个字节，将其转为ascll
     * @method_name : getFileHeader
     * @date : 2019/10/28 11:23
     * @author : Lujw
     * @update date   :
     * @update author :
     */
    public static String getFileHeader(MultipartFile file) throws Exception {
        InputStream is ;
        String value ;
        is = file.getInputStream();
        byte[] b = new byte[4];
        is.read(b, 0, b.length);
        value = bytesToHexString(b);
        is.close();
        return value;
    }

    /**
     * @param : [src]
     * @return : java.lang.String
     * @throws :
     * @description : 将byte转换为ascll
     * @method_name : bytesToHexString
     * @date : 2019/10/28 11:23
     * @author : Lujw
     * @update date   :
     * @update author :
     */
    public  static String bytesToHexString(byte[] src) {
        StringBuilder builder = new StringBuilder();
        if (src == null || src.length <= 0) {
            return null;
        }
        String hv;
        for (int i = 0; i < src.length; i++) {
            hv = Integer.toHexString(src[i] & 0xFF).toUpperCase();
            if (hv.length() < 2) {
                builder.append(0);
            }
            builder.append(hv);
        }
        return builder.toString();
    }
}