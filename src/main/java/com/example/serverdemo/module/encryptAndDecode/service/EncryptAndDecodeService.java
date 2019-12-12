package com.example.serverdemo.module.encryptAndDecode.service;

import com.example.serverdemo.base.exception.TopException;
import com.example.serverdemo.base.util.AesEncryptUtils;
import com.example.serverdemo.base.util.BaseUtil;
import com.example.serverdemo.base.util.DateUtil;
import com.example.serverdemo.module.encryptAndDecode.constant.AesKeyConstant;
import com.example.serverdemo.module.encryptAndDecode.dao.IEncryRepository;
import com.example.serverdemo.module.encryptAndDecode.from.EncryForm;
import com.example.serverdemo.module.encryptAndDecode.from.SecretKeyForm;
import com.example.serverdemo.module.encryptAndDecode.po.EncryPo;
import com.example.serverdemo.module.encryptAndDecode.utils.PasswordRandomUtils;
import com.example.serverdemo.module.encryptAndDecode.vo.EncryVo;
import com.example.serverdemo.module.encryptAndDecode.vo.SecretKeyVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author : Lujw
 * @Class Name   : EncryAndDecodeService
 * @Description : TODO
 * @date : 2019/7/4 13:35
 * @ModificationHistory Who       When        What
 * -----------------------------------------
 * Lujw   2019/7/4      创建
 */
@Service("encryptAndDecodeService")
public class EncryptAndDecodeService {
    private static final Logger LOGGER = LoggerFactory.getLogger(EncryptAndDecodeService.class);

    //配置文件中的秘钥
    @Value("${AseKey}")
    private String key;

    @Value("${spring.profiles.active}")
    private String active;

    @Autowired
    private IEncryRepository encryDao;
    /**
     * @param : [encryForm]
     * @throws :
     * @description : 解密
     * @method_name : getDecode
     * @date :
     * @version : v1.00
     * @author : Lujw
     * @update date   :
     * @update author :
     */
    public EncryVo getDecode(EncryForm encryForm) throws TopException {
        EncryPo encryPo;
        //查询数据库，通过秘钥类型，获取秘钥
        encryPo = getEncryPo(encryForm.getSecretKeyType());
        //解密数据
        EncryVo encryVo = new EncryVo();
        try {
            String decryText = AesEncryptUtils.aesDecrypt(buildDecodeSecretKey(encryPo.getSecretKey()), encryForm.getEncryText());
            encryVo.setDecodeText(decryText);
        } catch (Exception e) {
            LOGGER.error("解密内容出错", "秘钥不正确", e);
            throw new TopException("解密内容出错", "秘钥不正确");
        }
        return encryVo;
    }

    /**
     * @param : [encryForm]
     * @throws :
     * @description : 加密
     * @method_name : getEncrypt
     * @date :
     * @version : v1.00
     * @author : Lujw
     * @update date   :
     * @update author :
     */
    public EncryVo getEncrypt(EncryForm encryForm) throws TopException {
        EncryPo encryPo;
        //查询数据库，通过秘钥类型，获取秘钥
        encryPo = getEncryPo(encryForm.getSecretKeyType());
        //加密数据
        EncryVo encryVo = new EncryVo();
        try {
            String decryText = AesEncryptUtils.aesEncrypt(buildDecodeSecretKey(encryPo.getSecretKey()), encryForm.getEncryText());
            encryVo.setDecodeText(decryText);
        } catch (Exception e) {
            LOGGER.error("加密内容出错", "秘钥不正确", e);
            throw new TopException("加密内容出错", "秘钥不正确");
        }
        return encryVo;
    }

    /**
     * @param : [secretKey]
     * @return : java.lang.String
     * @throws :
     * @description : 解密待解密秘钥
     * @method_name : buildDecodeSecretKey
     * @date :
     * @version : v1.00
     * @author : Lujw
     * @update date   :
     * @update author :
     */
    private String buildDecodeSecretKey(String secretKey) throws TopException {
        String decodeSecretKey;
        if (!BaseUtil.stringNotNull(secretKey)) {
            LOGGER.error("待解密秘钥为空", "待解密秘钥为空");
            throw new TopException("待解密秘钥为空", "待解密秘钥为空");
        }
        try {
            decodeSecretKey = AesEncryptUtils.aesDecrypt(key, secretKey);
        } catch (Exception e) {
            LOGGER.error("解密待解密秘钥出错", "解密秘钥不正确");
            throw new TopException("解密待解密秘钥出错", "解密秘钥不正确");
        }
        if (!BaseUtil.stringNotNull(decodeSecretKey)) {
            LOGGER.error("待解密秘钥解密后为空", "待解密秘钥解密后为空");
            throw new TopException("待解密秘钥解密后为空", "待解密秘钥解密后为空");
        }
        return decodeSecretKey;
    }

    /**
     * @param : [encryForm]
     * @throws :
     * @description : 查询数据库，获取秘钥
     * @method_name : getEncryPo
     * @date :
     * @version : v1.00
     * @author : Lujw
     * @update date   :
     * @update author :
     */
    private EncryPo getEncryPo(String secretKeyType) throws TopException {
        EncryPo encryPo;
        Query query = new Query(Criteria.where("secretKeyType").is(secretKeyType));
        try {
            encryPo = encryDao.findSecretKey(query);
        } catch (Exception e) {
            LOGGER.error("查询数据库出错", "查询秘钥失败！", e);
            throw new TopException("查询数据库出错", "查询秘钥失败！");
        }
        return encryPo;
    }

    /**
     * @param : [secretKeyForm]
     * @return : void
     * @throws :
     * @description : 添加秘钥
     * @method_name : addSecretKey
     * @date :
     * @version : v1.00
     * @author : Lujw
     * @update date   :
     * @update author :
     */
    public void addSecretKey(SecretKeyForm secretKeyForm) throws TopException {
        EncryPo encryPo;
        Query query = new Query(Criteria.where("secretKeyType").is(secretKeyForm.getSecretKeyType()));
        try {
            encryPo = encryDao.findSecretKey(query);
        } catch (Exception e) {
            LOGGER.error("查询数据库出错", "查询秘钥失败！", e);
            throw new TopException("查询数据库出错", "查询秘钥失败！");
        }

        if (BaseUtil.objectNotNull(encryPo)) {
            LOGGER.error("秘钥添加失败", "秘钥已存在！");
            throw new TopException("秘钥添加失败", "秘钥已存在！");
        }

        try {
            //组装秘钥PO对象，进行入库操作
            encryDao.addSecretKey(buildSecretKeyPo(secretKeyForm));
        } catch (Exception e) {
            LOGGER.error("数据库操作出错", "添加秘钥失败！");
            throw new TopException("数据库操作出错", "添加秘钥失败！");
        }
    }

    /**
     * @param : [secretKeyForm]
     * @throws :
     * @description : 组装SecretKeyPo
     * @method_name : buildSecretKeyPo
     * @date :
     * @version : v1.00
     * @author : Lujw
     * @update date   :
     * @update author :
     */
    private EncryPo buildSecretKeyPo(SecretKeyForm secretKeyForm) {
        EncryPo encryPo = new EncryPo();
        encryPo.setSecretKeyType(secretKeyForm.getSecretKeyType().trim());
        encryPo.setSecretKey(secretKeyForm.getSecretKey().trim());
        return encryPo;
    }

    /**
     * @param : [secretKeyForm]
     * @return : void
     * @throws :
     * @description : 删除秘钥
     * @method_name : deleteSecretKey
     * @date :
     * @version : v1.00
     * @author : Lujw
     * @update date   :
     * @update author :
     */
    public void deleteSecretKey(SecretKeyForm secretKeyForm) throws TopException {
        Query query = new Query(Criteria.where("secretKeyType").is(secretKeyForm.getSecretKeyType()));
        try {
            encryDao.deleteSecretKey(query);
        } catch (Exception e) {
            LOGGER.error("数据库操作出错", "删除秘钥失败！");
            throw new TopException("数据库操作出错", "删除秘钥失败！");
        }
    }

    /**
     * @param : [secretKeyForm]
     * @return : void
     * @throws :
     * @description : 更新秘钥
     * @method_name : updateSecretKey
     * @date :
     * @version : v1.00
     * @author : Lujw
     * @update date   :
     * @update author :
     */
    public void updateSecretKey(SecretKeyForm secretKeyForm) throws TopException {
        try {
            encryDao.updateSecretKey(buildNewEncryPo(secretKeyForm));
        } catch (Exception e) {
            LOGGER.error("数据库操作出错", "更新秘钥失败");
            throw new TopException("数据库操作出错", "更新秘钥失败");
        }
    }

    /**
     * @param : [secretKeyForm]
     * @throws :
     * @description : 组装新的秘钥实体类
     * @method_name : buildNewEncryPo
     * @date :
     * @version : v1.00
     * @author : Lujw
     * @update date   :
     * @update author :
     */
    private EncryPo buildNewEncryPo(SecretKeyForm secretKeyForm) throws TopException {
        EncryPo encryPo = getEncryPo(secretKeyForm.getSecretKeyType());
        if (!BaseUtil.objectNotNull(encryPo)) {
            LOGGER.error("修改秘钥出错", "没有找到此秘钥！");
            throw new TopException("修改秘钥出错", "没有找到此秘钥！");
        }

        EncryPo encryPoTemp = new EncryPo();
        encryPoTemp.set_id(encryPo.get_id());
        encryPoTemp.setSecretKeyType(secretKeyForm.getSecretKeyType().trim());
        encryPoTemp.setSecretKey(secretKeyForm.getSecretKey().trim());
        return encryPoTemp;
    }

    /**
     * @param : []
     * @throws :
     * @description : 查询所有秘钥
     * @method_name : findSecretKeyAll
     * @date :
     * @version : v1.00
     * @author : Lujw
     * @update date   :
     * @update author :
     */
    public List<EncryPo> findSecretKeyAll() throws TopException {
        List<EncryPo> secretKeyListPo;
        try {
            secretKeyListPo = encryDao.findSecretKeyAll();
        } catch (Exception e) {
            LOGGER.error("查询数据库出错", "获取秘钥列表失败");
            throw new TopException("查询数据库出错", "获取秘钥列表失败");
        }
        return secretKeyListPo;
    }


    /**
     * @param : [secretKeyListPo]
     * @throws :
     * @description : secretKeyPo集合转secretKeyVo集合
     * @method_name : secretKeyListPoChangVo
     * @date :
     * @version : v1.00
     * @author : Lujw
     * @update date   :
     * @update author :
     */
    public List<SecretKeyVo> secretKeyListPoChangVo(List<EncryPo> secretKeyListPo) {
        List<SecretKeyVo> secretKeyListVo = new ArrayList<>();
        for (int i = 0; i < secretKeyListPo.size(); i++) {
            SecretKeyVo secretKeyVo = new SecretKeyVo();
            secretKeyVo.setSecretKeyType(secretKeyListPo.get(i).getSecretKeyType().trim());
            secretKeyVo.setSecretKey(secretKeyListPo.get(i).getSecretKey().trim());
            secretKeyListVo.add(secretKeyVo);
        }
        return secretKeyListVo;
    }

    /**
     * @param : []
     * @return : java.lang.String
     * @throws :
     * @description : 生成随机秘钥
     * @method_name : createSecretKey
     * @date :
     * @version : v1.00
     * @author : Lujw
     * @update date   :
     * @update author :
     */
    public String createSecretKey() throws TopException {
        String randomSecretKey;
        try {
            randomSecretKey = PasswordRandomUtils.getRandom(32, PasswordRandomUtils.TYPE.LETTER_CAPITAL_NUMBER);
        } catch (Exception e) {
            LOGGER.error("生成随机秘钥出错", "随机秘钥生成失败");
            throw new TopException("生成随机秘钥出错", "随机秘钥生成失败");
        }
        return randomSecretKey;
    }


    /**
     * 同步秘钥
     * 从redis，mongodb中取出所有加密过的数据，解密后用新的秘钥加密再放回原位置。
     * @param : [secretKeyForm]
     * @return : void
     * @throws :
     * @description : TODO
     * @method_name : syncSecretKey
     * @date :
     * @version : v1.00
     * @author : Lujw
     * @update date   :
     * @update author :
     */
    public void syncSecretKey(SecretKeyForm secretKeyForm) throws TopException {
        List<EncryPo> encryPoList = findSecretKeyAll();
        for (EncryPo encryPo : encryPoList) {
            /**
             * 解密待解密秘钥后，从新加密，再入库。
             */
            try {
                encryPo.setSecretKey(AesEncryptUtils.aesEncrypt(
                        secretKeyForm.getSecretKey().trim(), buildDecodeSecretKey(encryPo.getSecretKey())));
            } catch (Exception e) {
                LOGGER.error("同步秘钥时，加/解密秘钥出错", "同步秘钥失败");
                throw new TopException("同步秘钥时，加/解密秘钥出错", "同步秘钥失败");
            }

            try {
                encryDao.updateSecretKey(encryPo);
            } catch (Exception e) {
                LOGGER.error("同步秘钥时，操作数据库出错", "同步秘钥失败");
                throw new TopException("同步秘钥时，操作数据库出错", "同步秘钥失败");
            }
        }
        /**
         * 将配置中需要更新的秘钥输出到文件，防止错误操作，导致秘钥丢失。
         * ********************注意*********************
         * 配置中成功更新秘钥后，生成的秘钥文件请及时安全删除，以防外泄！！！！！！
         */
        String filePath="";
        if("prod".equals(active)){
            filePath="/usr/app/server/keytemp/key.txt";
        }else if("dev".equals(active)){
            filePath="C:\\Users\\fei\\Desktop\\key.txt";
        }

        try(FileWriter fw = new FileWriter(filePath)){
            fw.write(secretKeyForm.getSecretKey());
        } catch (IOException e) {
            LOGGER.error("<------- 秘钥输出到文件出错 -------->");
            throw new TopException("秘钥输出到文件出错","同步秘钥失败");
        }

        EncryPo encryPo = new EncryPo();
        encryPo.setSecretKeyType(DateUtil.getNowDate());
        try {
            encryPo.setSecretKey(AesEncryptUtils.aesEncrypt(
                    secretKeyForm.getSecretKey().trim(), secretKeyForm.getSecretKey()));
        } catch (Exception e) {
            LOGGER.error("同步秘钥时，加/解密秘钥出错", "同步秘钥失败");
            throw new TopException("同步秘钥时，加/解密秘钥出错", "同步秘钥失败");
        }
        encryDao.addSecretKey(encryPo);
    }


    /**
     * 更新单条秘钥
     * @description   : TODO
     * @method_name   : renewalSecretKey
     * @param         : [secretKeyForm]
     * @return        : void  
     * @throws        : 
     * @date          :  
     * @version       : v1.00
     * @author        : Lujw
     * @update date   :
     * @update author :
     */
    public void renewalSecretKey(SecretKeyForm secretKeyForm) throws TopException {
        String encryptStr;
        EncryPo encryPoTemp=getEncryPo(secretKeyForm.getSecretKeyType());
        String keyTemp=PasswordRandomUtils.getRandom(32,PasswordRandomUtils.TYPE.LETTER_CAPITAL_NUMBER);

        try {
            encryptStr=AesEncryptUtils.aesEncrypt(AesKeyConstant.aesKey, keyTemp);
        }catch (Exception e){
            LOGGER.error("加密/解密秘钥出错","更新秘钥失败");
            throw new TopException("加密/解密秘钥出错","更新秘钥失败");
        }
        EncryPo encryPo=new EncryPo();
        encryPo.set_id(encryPoTemp.get_id());
        encryPo.setSecretKeyType(encryPoTemp.getSecretKeyType());
        encryPo.setSecretKey(encryptStr);

        encryDao.updateSecretKey(encryPo);
    }
}