package com.example.serverdemo.module.fileToBase64.controller;

import com.example.serverdemo.base.entity.CommonResObject;
import com.example.serverdemo.base.exception.TopException;
import com.example.serverdemo.base.security.WhichMenu;
import com.example.serverdemo.module.encryptAndDecode.service.EncryptAndDecodeService;
import com.example.serverdemo.module.fileToBase64.service.FileToBase64Service;
import com.example.serverdemo.module.fileToBase64.util.FileToBase64Util;
import com.example.serverdemo.module.userManage.constant.Menu;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

/**
 * @author : Lujw
 * @Class Name   : FileUploadRestController
 * @Description : TODO
 * @date : 2019/10/28 11:00
 * @ModificationHistory Who       When        What
 * -----------------------------------------
 * Lujw   2019/10/28      创建
 */
@RestController
@RequestMapping("/web")
public class FileUploadRestController {
    private static final Logger LOGGER = LoggerFactory.getLogger(EncryptAndDecodeService.class);

    @Value("${file.upload-path}")
    private String fileUploadPath;

    @Value("${file.download-path}")
    private String fileDownloadPath;

    @Resource(name = "fileToBase64Service")
    private FileToBase64Service fileToBase64Service;

    /**
     * @description   : 文件转Base64
     * @method_name   : saleImageUpload
     * @param         : [request, file, appid]
     * @throws        :
     * @date          : 2019/10/17 10:08
     * @author        : Lujw
     * @update date   :
     * @update author :
     */
    @WhichMenu(Menu.FILE_TO_BASE64)
    @PostMapping(value ="/fileToBase64")
    public CommonResObject fileToBase64(@RequestParam("file") MultipartFile file)throws TopException {
        //文件类型
        String contentType = file.getContentType();
        // 文件头
        String fileHeader;
        try {
            fileHeader = FileToBase64Util.getFileHeader(file);
        } catch (Exception e) {
            LOGGER.error("文件上传失败，格式不对");
            throw new TopException("文件格式不对", "文件上传失败");
        }
        // 文件大小
        long fileSize = file.getSize();
        // 文件名
        String fileName = file.getOriginalFilename();
        LOGGER.info("文件名:" + fileName + " 文件类型:" + contentType + " 文件header:" + fileHeader + " 大小:" + fileSize);
        LOGGER.info("开始上传···");
        try {
            FileToBase64Util.uploadFile(file.getBytes(),fileUploadPath,fileName);
        } catch (Exception e){
            LOGGER.error("文件上传失败,错误详情:" , e);
            throw new TopException("文件上传失败", "文件上传失败");
        }
        LOGGER.info("上传完成【100%】");

        String base64Text;
        try{
            LOGGER.info("开始将文件转成Base64");
            base64Text=fileToBase64Service.fileToBase64(fileUploadPath+fileName);
            LOGGER.info("文件转Base64字符串成功");
        }catch(Exception e){
            LOGGER.error("文件转Base64失败，详情：",e);
            throw new TopException("文件转Base64失败","文件转Base64失败");
        }
        CommonResObject commonResObject=new CommonResObject();
        commonResObject.setResObj(base64Text);
        return commonResObject;
    }

    /**
     * @description   : Base64转文件
     * @method_name   : base64ToFile
     * @param         : [request, file]
     * @throws        :
     * @date          : 2019/10/28 11:59
     * @author        : Lujw
     * @update date   :
     * @update author :
     */
    @WhichMenu(Menu.FILE_TO_BASE64)
    @PostMapping(value ="/base64ToFile")
    public CommonResObject  base64ToFile(@RequestParam("base64Text") String base64Text, @RequestParam("fileName") String fileName, HttpServletResponse response)throws TopException {
        CommonResObject resObject=new CommonResObject();
        LOGGER.info("<------- 开始将base64编码转成数据流 --------->");
        resObject.setResObj(fileToBase64Service.base64ToFile(base64Text,fileDownloadPath,fileName));
        LOGGER.info("<------- base64编码转成数据流成功 --------->");
        return resObject;

//        response.setContentType("text/plain;");
//        //设置响应头
//        response.setHeader("Content-Disposition", "attachment; filename="+fileName);

        //通过网络输出流，将数据流传给前端
//        try{
//            response.getOutputStream().write(base64Bytes,0,base64Bytes.length);
//        } catch (Exception e) {
//            LOGGER.error("<!!!!---文件转输出流出错,详情：{}----!!!!>",e);
//            throw new TopException("文件转输出流出错","解码失败");
//        }
    }
}