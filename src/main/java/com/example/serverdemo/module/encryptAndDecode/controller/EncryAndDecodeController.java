package com.example.serverdemo.module.encryptAndDecode.controller;

import com.example.serverdemo.base.entity.ResultObject;
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
     * 解密
     * @description   : TODO
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
    public ResultObject decode(@RequestBody EncryForm encryForm) throws TopException {
        ResultObject resultObject=new ResultObject();
        resultObject.setResMsg(encryptAndDecodeService.getDecode(encryForm));
        resultObject.setResCode(ResultObject.SUCCESS);
        return resultObject;
    }

    /**
     * 加密
     * @description   : TODO
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
    public ResultObject encrypt(@RequestBody EncryForm encryForm) throws TopException {
        ResultObject resultObject=new ResultObject();
        resultObject.setResMsg(encryptAndDecodeService.getEncrypt(encryForm));
        resultObject.setResCode(ResultObject.SUCCESS);
        return resultObject;
    }

    /**
     * 添加秘钥
     * @description   : TODO
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
    public ResultObject addSecretKey(@RequestBody SecretKeyForm secretKeyForm) throws TopException {
        ResultObject resultObject=new ResultObject();
        encryptAndDecodeService.addSecretKey(secretKeyForm);
        resultObject.setResMsg("秘钥成功添加");
        resultObject.setResCode(ResultObject.SUCCESS);
        return resultObject;
    }

    /**
     * 删除秘钥
     * @description   : TODO
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
    public ResultObject deleteSecretKey(@RequestBody SecretKeyForm secretKeyForm) throws TopException {
        ResultObject resultObject=new ResultObject();
        encryptAndDecodeService.deleteSecretKey(secretKeyForm);
        resultObject.setResMsg("秘钥成功删除");
        resultObject.setResCode(ResultObject.SUCCESS);
        return resultObject;
    }

    /**
     * 修改秘钥
     * @description   : TODO
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
    public ResultObject updateSecretKey(@RequestBody SecretKeyForm secretKeyForm) throws TopException {
        ResultObject resultObject=new ResultObject();
        encryptAndDecodeService.updateSecretKey(secretKeyForm);
        resultObject.setResMsg("秘钥成功修改");
        resultObject.setResCode(ResultObject.SUCCESS);
        return resultObject;
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
    public ResultObject findSecretKeyAll() throws TopException {
        ResultObject resultObject=new ResultObject();
        resultObject.setResMsg(encryptAndDecodeService.secretKeyListPoChangVo(encryptAndDecodeService.findSecretKeyAll()));
        resultObject.setResCode(ResultObject.SUCCESS);
        return resultObject;
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
    public ResultObject createSecretKey() throws TopException{
        ResultObject resultObject=new ResultObject();
        resultObject.setResMsg(encryptAndDecodeService.createSecretKey());
        resultObject.setResCode(ResultObject.SUCCESS);
        return resultObject;
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
    public ResultObject syncSecretKey(@RequestBody SecretKeyForm secretKeyForm) throws TopException{
        ResultObject resultObject=new ResultObject();
        encryptAndDecodeService.syncSecretKey(secretKeyForm);
        resultObject.setResMsg("秘钥同步成功");
        resultObject.setResCode(ResultObject.SUCCESS);
        return resultObject;
    }


    /**
     * 更新单条秘钥
     * @description   : TODO
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
    public ResultObject renewalSecretKey(@RequestBody SecretKeyForm secretKeyForm) throws TopException{
        ResultObject resultObject=new ResultObject();
        encryptAndDecodeService.renewalSecretKey(secretKeyForm);
        resultObject.setResMsg("秘钥更新成功");
        resultObject.setResCode(ResultObject.SUCCESS);
        return resultObject;
    }

}