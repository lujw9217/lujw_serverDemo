package com.example.serverdemo.redis.impl;

import com.example.serverdemo.po.User;
import com.example.serverdemo.redis.TestRedis;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class TestRedisImpl implements TestRedis {

    @Autowired
    RedisTemplate redisTemplate;

    @Override
    public String insertCookie(User user) {
        String response;
        System.out.println("开始存入缓存");
        try{
            redisTemplate.opsForValue().set(user.getUserName(),user.getPassword());
            System.out.println("缓存存入成功");
            response="缓存存入成功";
        }catch (Exception e){
            System.out.println("缓存存入失败");
            response="缓存存入失败";
        }

        return response;
    }
}
