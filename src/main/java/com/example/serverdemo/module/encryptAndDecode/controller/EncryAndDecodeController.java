package com.example.serverdemo.module.encryptAndDecode.controller;

import com.example.serverdemo.base.entity.CommonResObject;
import com.example.serverdemo.base.exception.TopException;
import com.example.serverdemo.base.security.WhichMenu;
import com.example.serverdemo.module.encryptAndDecode.from.EncryForm;
import com.example.serverdemo.module.encryptAndDecode.from.SecretKeyForm;
import com.example.serverdemo.module.encryptAndDecode.service.EncryptAndDecodeService;
import com.example.serverdemo.module.userManage.constant.Menu;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


/**
 * @author : Lujw
 * @Class Name   : EncryAndDecodeController
 * @Description : TODO
 * @date : 2019/7/4 13:37
 * @ModificationHistory Who       When        What
 * -----------------------------------------
 * Lujw   2019/7/4      创建
 */
@Controller
@RequestMapping("/web")
public class EncryAndDecodeController {

    @Autowired
    private EncryptAndDecodeService encryptAndDecodeService;

    /**
     * @description   : 解密
     * @method_name   : query
     * @param         : [encryForm]
     * @throws        :
     * @date          :
     * @version       : v1.00
     * @author        : Lujw
     * @update date   :
     * @update author :
     */
    @ResponseBody
    @RequestMapping(value = "/decode", method = RequestMethod.POST)
    @WhichMenu(Menu.ENCRY_AND_DECRY)
    public CommonResObject decode(@RequestBody EncryForm encryForm) throws TopException {
        CommonResObject commonResObject=new CommonResObject();
        commonResObject.setResObj(encryptAndDecodeService.getDecode(encryForm));
        return commonResObject;
    }

    /**
     * @description   : 加密
     * @method_name   : encrypt
     * @param         : [encryForm]
     * @throws        :
     * @date          :
     * @version       : v1.00
     * @author        : Lujw
     * @update date   :
     * @update author :
     */
    @ResponseBody
    @RequestMapping(value = "/encrypt", method = RequestMethod.POST)
    @WhichMenu(Menu.ENCRY_AND_DECRY)
    public CommonResObject encrypt(@RequestBody EncryForm encryForm) throws TopException {
        CommonResObject commonResObject=new CommonResObject();
        commonResObject.setResObj(encryptAndDecodeService.getEncrypt(encryForm));
        return commonResObject;
    }

    /**
     * @description   : 添加秘钥
     * @method_name   : addSecretKey
     * @param         : [secretKeyForm]
     * @throws        :
     * @date          :
     * @version       : v1.00
     * @author        : Lujw
     * @update date   :
     * @update author :
     */
    @ResponseBody
    @RequestMapping(value = "/addSecretKey", method = RequestMethod.POST)
    @WhichMenu(Menu.ENCRY_AND_DECRY)
    public CommonResObject addSecretKey(@RequestBody SecretKeyForm secretKeyForm) throws TopException {
        encryptAndDecodeService.addSecretKey(secretKeyForm);
        CommonResObject commonResObject=new CommonResObject();
        commonResObject.setResMsg("秘钥成功添加");
        return commonResObject;
    }

    /**
     * @description   : 删除秘钥
     * @method_name   : deleteSecretKey
     * @param         : [secretKeyForm]
     * @throws        :
     * @date          :
     * @version       : v1.00
     * @author        : Lujw
     * @update date   :
     * @update author :
     */
    @ResponseBody
    @RequestMapping(value = "/deleteSecretKey", method = RequestMethod.POST)
    @WhichMenu(Menu.ENCRY_AND_DECRY)
    public CommonResObject deleteSecretKey(@RequestBody SecretKeyForm secretKeyForm) throws TopException {
        encryptAndDecodeService.deleteSecretKey(secretKeyForm);
        CommonResObject commonResObject=new CommonResObject();
        commonResObject.setResMsg("秘钥成功删除");
        return commonResObject;
    }

    /**
     * @description   : 修改秘钥
     * @method_name   : updateSecretKey
     * @param         : [secretKeyForm]
     * @throws        :
     * @date          :
     * @version       : v1.00
     * @author        : Lujw
     * @update date   :
     * @update author :
     */
    @ResponseBody
    @RequestMapping(value = "/updateSecretKey", method = RequestMethod.POST)
    @WhichMenu(Menu.ENCRY_AND_DECRY)
    public CommonResObject updateSecretKey(@RequestBody SecretKeyForm secretKeyForm) throws TopException {
        encryptAndDecodeService.updateSecretKey(secretKeyForm);
        CommonResObject commonResObject=new CommonResObject();
        commonResObject.setResMsg("秘钥成功修改");
        return commonResObject;
    }

    /**
     * 查询所有秘钥
     * @description   : TODO
     * @method_name   : findSecretKeyAll
     * @param         : []
     * @throws        :
     * @date          :
     * @version       : v1.00
     * @author        : Lujw
     * @update date   :
     * @update author :
     */
    @ResponseBody
    @RequestMapping(value = "/findSecretKeyAll", method = RequestMethod.POST)
    @WhichMenu(Menu.ENCRY_AND_DECRY)
    public CommonResObject findSecretKeyAll() throws TopException {
        CommonResObject commonResObject=new CommonResObject();
        commonResObject.setResObj(encryptAndDecodeService.secretKeyListPoChangVo(encryptAndDecodeService.findSecretKeyAll()));
        return commonResObject;
    }

    /**
     * 生成随机秘钥
     * @description   : TODO
     * @method_name   : createSecretKey
     * @param         : []
     * @throws        :
     * @date          :
     * @version       : v1.00
     * @author        : Lujw
     * @update date   :
     * @update author :
     */
    @ResponseBody
    @RequestMapping(value = "/createSecretKey", method = RequestMethod.POST)
    @WhichMenu(Menu.ENCRY_AND_DECRY)
    public CommonResObject createSecretKey() throws TopException{
        CommonResObject commonResObject=new CommonResObject();
        commonResObject.setResObj(encryptAndDecodeService.createSecretKey());
        return commonResObject;
    }

    /**
     * 同步秘钥
     * *********************************
     * 将数据库中所有秘钥取出，用新秘钥加密后重新入库。
     * @description   : TODO
     * @method_name   : syncSecretKey
     * @param         : []
     * @throws        :
     * @date          :
     * @version       : v1.00
     * @author        : Lujw
     * @update date   :
     * @update author :
     */
    @ResponseBody
    @RequestMapping(value = "/syncSecretKey", method = RequestMethod.POST)
    @WhichMenu(Menu.ENCRY_AND_DECRY)
    public CommonResObject syncSecretKey(@RequestBody SecretKeyForm secretKeyForm) throws TopException{
        encryptAndDecodeService.syncSecretKey(secretKeyForm);
        CommonResObject commonResObject=new CommonResObject();
        commonResObject.setResMsg("秘钥同步成功");
        return commonResObject;
    }

    /**
     * @description   : 更新单条秘钥
     * @method_name   : renewalSecretKey
     * @param         : [secretKeyForm]
     * @throws        :
     * @date          :
     * @version       : v1.00
     * @author        : Lujw
     * @update date   :
     * @update author :
     */
    @ResponseBody
    @RequestMapping(value = "/renewalSecretKey", method = RequestMethod.POST)
    @WhichMenu(Menu.ENCRY_AND_DECRY)
    public CommonResObject renewalSecretKey(@RequestBody SecretKeyForm secretKeyForm) throws TopException{
        encryptAndDecodeService.renewalSecretKey(secretKeyForm);
        CommonResObject commonResObject=new CommonResObject();
        commonResObject.setResMsg("秘钥更新成功");
        return commonResObject;
    }

}