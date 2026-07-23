package com.sky.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Dictionary;
import java.util.Set;

/**
 * @projectName: sky-take-out
 * @package: com.sky.utils
 * @className: RedisUtil
 * @author: lwj
 * @description: TODO
 * @date: 2026/7/23 22:28
 * @version: 1.0
 */
@Component
public class RedisUtil {

    @Autowired
    private RedisTemplate redisTemplate;

    public void cleanCache(String pattern){
        Set keys = redisTemplate.keys(pattern);
        redisTemplate.delete(keys);
    }
}
