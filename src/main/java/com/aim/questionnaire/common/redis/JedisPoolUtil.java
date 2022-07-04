package com.aim.questionnaire.common.redis;

import java.util.*;


public class JedisPoolUtil {
    private static RedisUtil redisUtil = null;

    public JedisPoolUtil() {
    }

    public static String get(String key) {
        return redisUtil.get(key);
    }

    public static String set(String key, String value) {
        return redisUtil.set(key, value);
    }

    public static Long del(String... keys) {
        return redisUtil.del(keys);
    }

    public static Long append(String key, String str) {
        return redisUtil.append(key, str);
    }

    public static Boolean exists(String key) {
        return redisUtil.exists(key);
    }

    public static Long setnx(String key, String value) {
        return redisUtil.setnx(key, value);
    }

    public static String setex(String key, String value, int seconds) {
        return redisUtil.setex(key, value, seconds);
    }

    public static Long setrange(String key, String str, int offset) {
        return redisUtil.setrange(key, str, offset);
    }

    public static List<String> mget(String... keys) {
        return redisUtil.mget(keys);
    }

    public static String mset(String... keysvalues) {
        return redisUtil.mset(keysvalues);
    }

    public static Long msetnx(String... keysvalues) {
        return redisUtil.msetnx(keysvalues);
    }

    public static String getset(String key, String value) {
        return redisUtil.getset(key, value);
    }

    public static String getrange(String key, int startOffset, int endOffset) {
        return redisUtil.getrange(key, startOffset, endOffset);
    }

    public static Long incr(String key) {
        return redisUtil.incr(key);
    }

    public static Long expire(String key, int seconds) {
        return redisUtil.expire(key, seconds);
    }

    public static Long incrBy(String key, Long integer) {
        return redisUtil.incrBy(key, integer);
    }

    public static Long decr(String key) {
        return redisUtil.decr(key);
    }

    public static Long decrBy(String key, Long integer) {
        return redisUtil.decrBy(key, integer);
    }

    public static Long serlen(String key) {
        return redisUtil.serlen(key);
    }

    public static Long hset(String key, String field, String value) {
        return redisUtil.hset(key, field, value);
    }

    public static Long hsetnx(String key, String field, String value) {
        return redisUtil.hsetnx(key, field, value);
    }

    public static String hmset(String key, Map<String, String> hash) {
        return redisUtil.hmset(key, hash);
    }

    public static String hget(String key, String field) {
        return redisUtil.hget(key, field);
    }

    public static List<String> hmget(String key, String... fields) {
        return redisUtil.hmget(key, fields);
    }

    public static Long hincrby(String key, String field, Long value) {
        return redisUtil.hincrby(key, field, value);
    }

    public static Boolean hexists(String key, String field) {
        return redisUtil.hexists(key, field);
    }

    public static Long hlen(String key) {
        return redisUtil.hlen(key);
    }

    public static Long hdel(String key, String... fields) {
        return redisUtil.hdel(key, fields);
    }

    public static Set<String> hkeys(String key) {
        return redisUtil.hkeys(key);
    }

    public static List<String> hvals(String key) {
        return redisUtil.hvals(key);
    }

    public static Map<String, String> hgetall(String key) {
        return redisUtil.hgetall(key);
    }

    public static Long lpush(String key, String... strs) {
        return redisUtil.lpush(key, strs);
    }

    public static Long rpush(String key, String... strs) {
        return redisUtil.rpush(key, strs);
    }

    public static String lset(String key, Long index, String value) {
        return redisUtil.lset(key, index, value);
    }

    public static Long lrem(String key, long count, String value) {
        return redisUtil.lrem(key, count, value);
    }

    public static String ltrim(String key, long start, long end) {
        return redisUtil.ltrim(key, start, end);
    }

    public static String lpop(String key) {
        return redisUtil.lpop(key);
    }

    public static String rpop(String key) {
        return redisUtil.rpop(key);
    }

    public static String rpoplpush(String srckey, String dstkey) {
        return redisUtil.rpoplpush(srckey, dstkey);
    }

    public static String lindex(String key, long index) {
        return redisUtil.lindex(key, index);
    }

    public static Long llen(String key) {
        return redisUtil.llen(key);
    }

    public static List<String> lrange(String key, long start, long end) {
        return redisUtil.lrange(key, start, end);
    }

    public static Long sadd(String key, String... members) {
        return redisUtil.sadd(key, members);
    }

    public static Long srem(String key, String... members) {
        return redisUtil.srem(key, members);
    }

    public static String spop(String key) {
        return redisUtil.spop(key);
    }

    public static Set<String> sdiff(String... keys) {
        return redisUtil.sdiff(keys);
    }

    public static Long sdiffstore(String dstkey, String... keys) {
        return redisUtil.sdiffstore(dstkey, keys);
    }

    public static Set<String> sinter(String... keys) {
        return redisUtil.sinter(keys);
    }

    public static Long sinterstore(String dstkey, String... keys) {
        return redisUtil.sinterstore(dstkey, keys);
    }

    public static Set<String> sunion(String... keys) {
        return redisUtil.sunion(keys);
    }

    public static Long sunionstore(String dstkey, String... keys) {
        return redisUtil.sunionstore(dstkey, keys);
    }

    public static Long smove(String srckey, String dstkey, String member) {
        return redisUtil.smove(srckey, dstkey, member);
    }

    public static Long scard(String key) {
        return redisUtil.scard(key);
    }

    public static Boolean sismember(String key, String member) {
        return redisUtil.sismember(key, member);
    }

    public static String srandmember(String key) {
        return redisUtil.srandmember(key);
    }

    public static Set<String> smembers(String key) {
        return redisUtil.smembers(key);
    }

    public static Long zadd(String key, Map<String, Double> scoreMembers) {
        return redisUtil.zadd(key, scoreMembers);
    }

    public static Long zadd(String key, double score, String member) {
        return redisUtil.zadd(key, score, member);
    }

    public static Long zrem(String key, String... members) {
        return redisUtil.zrem(key, members);
    }

    public static Double zincrby(String key, double score, String member) {
        return redisUtil.zincrby(key, score, member);
    }

    public static Long zrank(String key, String member) {
        return redisUtil.zrank(key, member);
    }

    public static Long zrevrank(String key, String member) {
        return redisUtil.zrevrank(key, member);
    }

    public static Set<String> zrevrange(String key, long start, long end) {
        return redisUtil.zrevrange(key, start, end);
    }

    public static Set<String> zrangebyscore(String key, String max, String min) {
        return redisUtil.zrangebyscore(key, max, min);
    }

    public static Set<String> zrangeByScore(String key, double max, double min) {
        return redisUtil.zrangeByScore(key, max, min);
    }

    public static Long zcount(String key, String min, String max) {
        return redisUtil.zcount(key, min, max);
    }

    public static Long zcard(String key) {
        return redisUtil.zcard(key);
    }

    public static Double zscore(String key, String member) {
        return redisUtil.zscore(key, member);
    }

    public static Long zremrangeByRank(String key, long start, long end) {
        return redisUtil.zremrangeByRank(key, start, end);
    }

    public static Long zremrangeByScore(String key, double start, double end) {
        return redisUtil.zremrangeByScore(key, start, end);
    }

    public static Set<String> keys(String pattern) {
        return redisUtil.keys(pattern);
    }

    public static String type(String key) {
        return redisUtil.type(key);
    }

    public static String lset(byte[] key, byte[] value) {
        return redisUtil.setList(key, value);
    }

    public static byte[] lget(byte[] key) {
        return redisUtil.getList(key);
    }

    public static void main(String[] args) {
        List<String> l = new ArrayList();
        l.add("a");
        l.add("b");
        l.add("c");
    }

    static {
        ResourceBundle bundle = ResourceBundle.getBundle("redis");
        if (bundle == null) {
            throw new IllegalArgumentException("[redis.properties] is not found!");
        } else {
            redisUtil = RedisFactory.getInstance(bundle);
        }
    }
}
