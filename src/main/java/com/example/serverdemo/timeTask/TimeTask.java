package com.example.serverdemo.timeTask;

import com.example.serverdemo.base.util.AesEncryptUtils;
import com.example.serverdemo.module.encryptAndDecode.constant.AesKeyConstant;
import com.example.serverdemo.module.encryptAndDecode.dao.IEncryRepository;
import com.example.serverdemo.module.encryptAndDecode.po.EncryPo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.File;

/**
 * @author : Lujw
 * @Class Name   : TimeTask
 * @Description : TODO
 * @Project : serverdemo
 * @Program : com.example.serverdemo.timeTask
 * @date : 2019/12/10 20:48
 * @ModificationHistory Who       When        What
 * -----------------------------------------
 * Lujw   2019/12/10      创建
 */
@Component
public class TimeTask {

    private static final Logger LOGGER = LoggerFactory.getLogger(TimeTask.class);

    @Value("${spring.profiles.active}")
    private String active;

    //配置文件中的秘钥
    @Value("${AseKey}")
    private String key;

    @Resource
    private IEncryRepository encryDao;

    public void initAesKey(){
        /**
         * 根据环境自行去数据库中匹配秘钥
         */
        Query query;
        if("pro60".equals(active)){
            query = new Query(Criteria.where("secretKeyType").is("prod"));
        }else {
            query = new Query(Criteria.where("secretKeyType").is("dev "));
        }
        EncryPo encryPo = encryDao.findSecretKey(query);

        try {
            AesKeyConstant.setAesKey(AesEncryptUtils.aesDecrypt(key, encryPo.getSecretKey()));
        }catch (Exception e){
            LOGGER.error("初次初始化秘钥失败！");
        }
    }

    /**
     * @description   : 递归删除文件夹
     * @method_name   : deleteFile
     * @param         : [file]
     * @return        : void
     * @throws        :
     * @date          : 2019/11/1 9:32
     * @author        : Lujw
     * @update date   :
     * @update author :
     */
    public void deleteFile(File file) {
        if("pro60".equals(active)){
            LOGGER.info("检测运行环境为生产环境，启动定时清理线程，执行清理操作!");
            if (file.isDirectory()) {
                String[] children = file.list();//获取文件夹下所有子文件夹
                //递归删除目录中的子目录下
                for (int i = 0; i < children.length; i++) {
                    deleteFile(new File(file, children[i]));
                }
            }
            // 目录空了，进行删除
            file.delete();
            LOGGER.info("删除指定位置所有文件成功！");
        }
    }
}