package com.aim.questionnaire.common.redis;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;


@Configuration
public class RedisConfig {

    @Bean
    public RedisTemplate<String, Object> redisTemplate(LettuceConnectionFactory lcf) {
        RedisTemplate<String, Object> restTemplate = new RedisTemplate<String, Object>();
        // 为String类型的key设置序列化
        restTemplate.setKeySerializer(new StringRedisSerializer());
        // 为String类型的value设置序列化
        restTemplate.setValueSerializer(new GenericJackson2JsonRedisSerializer());
        // 为Hash类型的key设置序列化
        restTemplate.setHashKeySerializer(new StringRedisSerializer());
        // 为Hash类型的value设置序列化
        restTemplate.setHashValueSerializer(new GenericJackson2JsonRedisSerializer());
        restTemplate.setConnectionFactory(lcf);
        return restTemplate;
    }

    /**
     * redis哨兵的配置
     * @return
     */
   /*
    @Bean
    public RedisSentinelConfiguration redisSentinelConfiguration(){
        RedisSentinelConfiguration rsconf = new RedisSentinelConfiguration()
            // 主节点名称
            .master("mymaster")
            // 哨兵的配置
            .sentinel("127.0.0.1", 26379)
            .sentinel("127.0.0.1", 26380)
            .sentinel("127.0.0.1", 26381);
        rsconf.setPassword("moluroot");
        return rsconf;
    }
    */
}
