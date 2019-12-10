package com.example.serverdemo.module.encryptAndDecode.dao.impl;

import com.example.serverdemo.base.util.BaseUtil;
import com.example.serverdemo.module.encryptAndDecode.dao.IEncryRepository;
import com.example.serverdemo.module.encryptAndDecode.po.EncryPo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * @author : Lujw
 * @Class Name   : EncryRepositoryImpl
 * @Description : TODO
 * @date : 2019/7/4 14:07
 * @ModificationHistory Who       When        What
 * -----------------------------------------
 * Lujw   2019/7/4      创建
 */
@Repository
public class EncryRepositoryImpl implements IEncryRepository {
    private static final String SECRET_KEY = "secret_key";

    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public EncryPo findSecretKey(Query query) {
        return BaseUtil.objToJAVA(mongoTemplate.findOne(query, EncryPo.class, SECRET_KEY), EncryPo.class);
    }

    @Override
    public void addSecretKey(EncryPo encryPo) {
        mongoTemplate.insert(encryPo,SECRET_KEY);
    }

    @Override
    public void updateSecretKey(EncryPo encryPo) {
        mongoTemplate.save(encryPo,SECRET_KEY);
    }

    @Override
    public void deleteSecretKey(Query query) {
        mongoTemplate.remove(query,SECRET_KEY);
    }

    @Override
    public List<EncryPo> findSecretKeyAll() {
        return mongoTemplate.findAll(EncryPo.class,SECRET_KEY);
    }


}