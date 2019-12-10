package com.example.serverdemo.module.encryptAndDecode.dao;

import com.example.serverdemo.module.encryptAndDecode.po.EncryPo;
import org.springframework.data.mongodb.core.query.Query;

import java.util.List;

public interface IEncryRepository {

     EncryPo findSecretKey(Query query);

     void addSecretKey(EncryPo encryPo);

     void updateSecretKey(EncryPo encryPo);

     void deleteSecretKey(Query query);

     List<EncryPo> findSecretKeyAll();
}
