package com.project.myfirstproject.service;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class Redistest {

    private final RedisTemplate<String, Object> redisTemplate;

    public Redistest(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public void setValue(String key, String value) {

        redisTemplate.opsForValue().set(key, value);
    }

    public String getValue(String key) {

        Object value = redisTemplate.opsForValue().get(key);

        System.out.println("KEY = " + key);
        System.out.println("VALUE FROM REDIS = " + value);

        return value != null ? value.toString() : "Key not found";
    }
}