package com.example.journalApp.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
public class RedisConfig {

    @Bean
    public RedisTemplate redisTemplate(RedisConnectionFactory factory){
//        Basically RedisConnection factory is an interface which is used to connect with the redis server and also
//        for managing the redis server
        RedisTemplate redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(factory);

//        While setting the value and getting the value from the redis server "String serializer" must be used
//        because both the key & value pair is in the string format
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(new StringRedisSerializer());

        return redisTemplate;
    }

}
