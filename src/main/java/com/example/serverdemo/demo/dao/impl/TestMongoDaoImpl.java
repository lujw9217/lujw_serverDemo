package com.example.serverdemo.demo.dao.impl;

import com.example.serverdemo.demo.po.User;
import com.example.serverdemo.demo.dao.TestMongoDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class TestMongoDaoImpl implements TestMongoDao {
    private static final String DB_USER ="user";

    @Autowired
   private MongoTemplate mongoTemplate;

    @Override
    public String insertUser(User user) {
        String response;

        System.out.println("开始入库");
        try{
            mongoTemplate.save(user,DB_USER);
            System.out.println("入库成功");
            response="入库成功";
        }catch (Exception e){
            System.out.println("入库失败");
            response="入库失败";
        }
        return response;
    }
}
