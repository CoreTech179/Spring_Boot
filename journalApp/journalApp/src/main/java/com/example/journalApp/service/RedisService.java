package com.example.journalApp.service;

import com.example.journalApp.API.response.WeatherResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
@Slf4j
public class RedisService {

    @Autowired
    @Qualifier("stringRedisTemplate")
    private RedisTemplate redisTemplateObj;

    public <T> T get(String key, Class<T>  entityClass){
        try{
            Object obj = redisTemplateObj.opsForValue().get(key);
            ObjectMapper mapper = new ObjectMapper();
            return mapper.readValue(obj.toString(), entityClass);

        }catch(Exception e){
            log.error("Exception Occured!", e);
            return null;
        }
    }

    public void set(String key, Object obj, long expire){
        try{
            ObjectMapper mapper = new ObjectMapper();
            String jsonValue = mapper.writeValueAsString(obj);
            redisTemplateObj.opsForValue().set(key, jsonValue, expire, TimeUnit.SECONDS);
        } catch (Exception e) {
            log.error("Error while setting the value inside redis!");
        }
    }
}
