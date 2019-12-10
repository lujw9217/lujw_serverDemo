package com.example.serverdemo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import javax.annotation.Resource;

/**
 * 解决redis存入乱码问题
 */
@Configuration
public class RedisConfiguration {

    @Resource
    private RedisTemplate redisTemplate;

    /**
     * @description   : 定制RedisTemplate<String, Object>，修改序列化方式
     * @method_name   : objRedisTemplate
     * @param         : []
     * @return        : org.springframework.data.redis.core.RedisTemplate<java.lang.String,java.lang.Object>
     * @throws        :
     * @date          : 2019/12/10 17:41
     * @author        : Lujw
     * @update date   :
     * @update author :
     */
    @Bean(name = "objRedisTemplate")
    RedisTemplate<String, Object> objRedisTemplate(){
        //key使用string序列化
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        //value采用json序列化
        redisTemplate.setValueSerializer(new Jackson2JsonRedisSerializer<>(
                Object.class));
        redisTemplate.setHashKeySerializer(new StringRedisSerializer());
        return redisTemplate;
    }
}
