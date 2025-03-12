package com.example.journalApp;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

@SpringBootTest
public class RedisTest {

    @Autowired
    @Qualifier("stringRedisTemplate")
    private RedisTemplate redisTemplateObj;

    @Disabled
    @Test
    public void testRedis(){
//        redisTemplateObj.opsForValue().set("email","gmail@gmail.com");
        Object salary = redisTemplateObj.opsForValue().get("salary");
        int a = 1;
    }

}
