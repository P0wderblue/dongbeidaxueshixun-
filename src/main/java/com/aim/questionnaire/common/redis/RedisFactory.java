package com.aim.questionnaire.common.redis;

import lombok.extern.slf4j.Slf4j;

import java.util.ResourceBundle;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
public class RedisFactory {
    private static ConcurrentHashMap<String, RedisUtil> poolMap = new ConcurrentHashMap();

    public RedisFactory() {
    }

    public static RedisUtil getInstance(ResourceBundle bundle) {
        if (bundle == null) {
            throw new IllegalArgumentException("bundle is null");
        } else {
            try {
                String key = bundle.getString("redis.ip") + ":" + bundle.getString("redis.port");
                if (poolMap.contains(key)) {
                    return (RedisUtil)poolMap.get(key);
                } else {
                    RedisUtil redisUtil = new RedisUtil(bundle);
                    poolMap.put(key, redisUtil);
                    return redisUtil;
                }
            } catch (Exception var3) {
                log.error("getInstance Exception:" + var3, var3);
                return null;
            }
        }
    }
}
