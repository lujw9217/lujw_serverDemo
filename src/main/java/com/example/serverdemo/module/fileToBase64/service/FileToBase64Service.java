package com.example.serverdemo.module.fileToBase64.service;


import com.example.serverdemo.base.exception.TopException;

/**
 * @author : Lujw
 * @Class Name   : FileToBase64Service
 * @Description : TODO
 * @date : 2019/10/28 11:44
 * @ModificationHistory Who       When        What
 * -----------------------------------------
 * Lujw   2019/10/28      创建
 */
public interface FileToBase64Service {
    /**
     * @description   : 文件转Base64
     * @method_name   : fileToBase64
     * @param         : [filePath]
     * @return        : java.lang.String  
     * @throws        : 
     * @date          : 2019/10/28 11:48
     * @author        : Lujw
     * @update date   :
     * @update author :
     */
    String fileToBase64(String filePath) throws TopException;

    /**
     * @description   : Base64转文件
     * @method_name   : base64ToFile
     * @param         : [base64, filePath]
     * @return        : void
     * @throws        : 
     * @date          : 2019/10/28 11:48
     * @author        : Lujw
     * @update date   :
     * @update author :
     */
    String base64ToFile(String base64Text, String fileDownloadPath, String fileName)throws TopException;

}