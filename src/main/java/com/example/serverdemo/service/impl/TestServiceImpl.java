package com.example.serverdemo.service.impl;

import com.example.serverdemo.po.User;
import com.example.serverdemo.dao.TestMongoDao;
import com.example.serverdemo.redis.TestRedis;
import com.example.serverdemo.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service(value = "TestService")
public class TestServiceImpl implements TestService {

    @Autowired
    private TestMongoDao testMongoDao;

    @Autowired
    private TestRedis testRedis;

    @Override
    public String insertUser(User user) {
        return testMongoDao.insertUser(user);
    }

    @Override
    public String insertCookie(User user){
        return testRedis.insertCookie(user);
    }
}
