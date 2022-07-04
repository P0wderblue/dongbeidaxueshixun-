package com.aim.questionnaire.common.redis;

import lombok.extern.slf4j.Slf4j;
import redis.clients.jedis.BinaryClient;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;
@Slf4j
public class RedisUtil {

    private static JedisPool pool;
    private static ResourceBundle bundle;

    public RedisUtil(ResourceBundle bundle) {
        if (pool == null) {
            RedisUtil.bundle = bundle;
            if (bundle == null) {
                throw new IllegalArgumentException("bundle is null");
            }

            JedisPoolConfig config = new JedisPoolConfig();
            config.setMaxTotal(Integer.valueOf(bundle.getString("redis.pool.maxActive")));
            config.setMaxIdle(Integer.valueOf(bundle.getString("redis.pool.maxIdle")));
            config.setMaxWaitMillis(Long.valueOf(bundle.getString("redis.pool.maxWait")));
            config.setTestOnBorrow(Boolean.valueOf(bundle.getString("redis.pool.testOnBorrow")));
            config.setTestOnReturn(Boolean.valueOf(bundle.getString("redis.pool.testOnReturn")));

            try {
                if (bundle.containsKey("redis.password") && bundle.getString("redis.password") != null && !bundle.getString("redis.password").isEmpty()) {
                    String password = bundle.getString("redis.password");
                    pool = new JedisPool(config, bundle.getString("redis.ip"), Integer.valueOf(bundle.getString("redis.port")), 2000, password);
                } else {
                    pool = new JedisPool(config, bundle.getString("redis.ip"), Integer.valueOf(bundle.getString("redis.port")));
                }
            } catch (Exception var4) {
                log.error("initialize exception:" + var4.getMessage(), var4);
            }
        }

    }

    public String get(String key) {
        Jedis jedis = null;
        String value = null;

        try {
            jedis = pool.getResource();
            value = jedis.get(key);
        } catch (Exception var8) {
            jedis.close();
            var8.printStackTrace();
        } finally {
            returnResource(pool, jedis);
        }

        return value;
    }

    public Long expire(String key, int seconds) {
        Jedis jedis = null;
        Long value = null;

        try {
            jedis = pool.getResource();
            value = jedis.expire(key, seconds);
        } catch (Exception var9) {
            jedis.close();
            var9.printStackTrace();
        } finally {
            returnResource(pool, jedis);
        }

        return value;
    }

    public String set(String key, String value) {
        Jedis jedis = null;

        String var5;
        try {
            jedis = pool.getResource();
            String var4 = jedis.set(key, value);
            return var4;
        } catch (Exception var9) {
            jedis.close();
            var9.printStackTrace();
            var5 = "0";
        } finally {
            returnResource(pool, jedis);
        }

        return var5;
    }

    public Long del(String... keys) {
        Jedis jedis = null;

        Long var4;
        try {
            jedis = pool.getResource();
            Long var3 = jedis.del(keys);
            return var3;
        } catch (Exception var8) {
            jedis.close();
            var8.printStackTrace();
            var4 = 0L;
        } finally {
            returnResource(pool, jedis);
        }

        return var4;
    }

    public Long append(String key, String str) {
        Jedis jedis = null;
        Long res = null;

        Long var6;
        try {
            jedis = pool.getResource();
            res = jedis.append(key, str);
            return res;
        } catch (Exception var10) {
            jedis.close();
            var10.printStackTrace();
            var6 = 0L;
        } finally {
            returnResource(pool, jedis);
        }

        return var6;
    }

    public Boolean exists(String key) {
        Jedis jedis = null;

        Boolean var4;
        try {
            jedis = pool.getResource();
            Boolean var3 = jedis.exists(key);
            return var3;
        } catch (Exception var8) {
            jedis.close();
            var8.printStackTrace();
            var4 = false;
        } finally {
            returnResource(pool, jedis);
        }

        return var4;
    }

    public Long setnx(String key, String value) {
        Jedis jedis = null;

        Long var5;
        try {
            jedis = pool.getResource();
            Long var4 = jedis.setnx(key, value);
            return var4;
        } catch (Exception var9) {
            jedis.close();
            var9.printStackTrace();
            var5 = 0L;
        } finally {
            returnResource(pool, jedis);
        }

        return var5;
    }

    public String setex(String key, String value, int seconds) {
        Jedis jedis = null;
        String res = null;

        try {
            jedis = pool.getResource();
            res = jedis.setex(key, seconds, value);
        } catch (Exception var10) {
            jedis.close();
            var10.printStackTrace();
        } finally {
            returnResource(pool, jedis);
        }

        return res;
    }

    public Long setrange(String key, String str, int offset) {
        Jedis jedis = null;

        Long var6;
        try {
            jedis = pool.getResource();
            Long var5 = jedis.setrange(key, (long)offset, str);
            return var5;
        } catch (Exception var10) {
            jedis.close();
            var10.printStackTrace();
            var6 = 0L;
        } finally {
            returnResource(pool, jedis);
        }

        return var6;
    }

    public List<String> mget(String... keys) {
        Jedis jedis = null;
        List values = null;

        try {
            jedis = pool.getResource();
            values = jedis.mget(keys);
        } catch (Exception var8) {
            jedis.close();
            var8.printStackTrace();
        } finally {
            returnResource(pool, jedis);
        }

        return values;
    }

    public String mset(String... keysvalues) {
        Jedis jedis = null;
        String res = null;

        try {
            jedis = pool.getResource();
            res = jedis.mset(keysvalues);
        } catch (Exception var8) {
            jedis.close();
            var8.printStackTrace();
        } finally {
            returnResource(pool, jedis);
        }

        return res;
    }

    public Long msetnx(String... keysvalues) {
        Jedis jedis = null;
        Long res = 0L;

        try {
            jedis = pool.getResource();
            res = jedis.msetnx(keysvalues);
        } catch (Exception var8) {
            jedis.close();
            var8.printStackTrace();
        } finally {
            returnResource(pool, jedis);
        }

        return res;
    }

    public String getset(String key, String value) {
        Jedis jedis = null;
        String res = null;

        try {
            jedis = pool.getResource();
            res = jedis.getSet(key, value);
        } catch (Exception var9) {
            jedis.close();
            var9.printStackTrace();
        } finally {
            returnResource(pool, jedis);
        }

        return res;
    }

    public String getrange(String key, int startOffset, int endOffset) {
        Jedis jedis = null;
        String res = null;

        try {
            jedis = pool.getResource();
            res = jedis.getrange(key, (long)startOffset, (long)endOffset);
        } catch (Exception var10) {
            jedis.close();
            var10.printStackTrace();
        } finally {
            returnResource(pool, jedis);
        }

        return res;
    }

    public Long incr(String key) {
        Jedis jedis = null;
        Long res = null;

        try {
            jedis = pool.getResource();
            res = jedis.incr(key);
        } catch (Exception var8) {
            jedis.close();
            var8.printStackTrace();
        } finally {
            returnResource(pool, jedis);
        }

        return res;
    }

    public Long incrBy(String key, Long integer) {
        Jedis jedis = null;
        Long res = null;

        try {
            jedis = pool.getResource();
            res = jedis.incrBy(key, integer);
        } catch (Exception var9) {
            jedis.close();
            var9.printStackTrace();
        } finally {
            returnResource(pool, jedis);
        }

        return res;
    }

    public Long decr(String key) {
        Jedis jedis = null;
        Long res = null;

        try {
            jedis = pool.getResource();
            res = jedis.decr(key);
        } catch (Exception var8) {
            jedis.close();
            var8.printStackTrace();
        } finally {
            returnResource(pool, jedis);
        }

        return res;
    }

    public Long decrBy(String key, Long integer) {
        Jedis jedis = null;
        Long res = null;

        try {
            jedis = pool.getResource();
            res = jedis.decrBy(key, integer);
        } catch (Exception var9) {
            jedis.close();
            var9.printStackTrace();
        } finally {
            returnResource(pool, jedis);
        }

        return res;
    }

    public Long serlen(String key) {
        Jedis jedis = null;
        Long res = null;

        try {
            jedis = pool.getResource();
            res = jedis.strlen(key);
        } catch (Exception var8) {
            jedis.close();
            var8.printStackTrace();
        } finally {
            returnResource(pool, jedis);
        }

        return res;
    }

    public Long hset(String key, String field, String value) {
        Jedis jedis = null;
        Long res = null;

        try {
            jedis = pool.getResource();
            res = jedis.hset(key, field, value);
        } catch (Exception var10) {
            jedis.close();
            var10.printStackTrace();
        } finally {
            returnResource(pool, jedis);
        }

        return res;
    }

    public Long hsetnx(String key, String field, String value) {
        Jedis jedis = null;
        Long res = null;

        try {
            jedis = pool.getResource();
            res = jedis.hsetnx(key, field, value);
        } catch (Exception var10) {
            jedis.close();
            var10.printStackTrace();
        } finally {
            returnResource(pool, jedis);
        }

        return res;
    }

    public String hmset(String key, Map<String, String> hash) {
        Jedis jedis = null;
        String res = null;

        try {
            jedis = pool.getResource();
            res = jedis.hmset(key, hash);
        } catch (Exception var9) {
            jedis.close();
            var9.printStackTrace();
        } finally {
            returnResource(pool, jedis);
        }

        return res;
    }

    public String hget(String key, String field) {
        Jedis jedis = null;
        String res = null;

        try {
            jedis = pool.getResource();
            res = jedis.hget(key, field);
        } catch (Exception var9) {
            jedis.close();
            var9.printStackTrace();
        } finally {
            returnResource(pool, jedis);
        }

        return res;
    }

    public List<String> hmget(String key, String... fields) {
        Jedis jedis = null;
        List res = null;

        try {
            jedis = pool.getResource();
            res = jedis.hmget(key, fields);
        } catch (Exception var9) {
            jedis.close();
            var9.printStackTrace();
        } finally {
            returnResource(pool, jedis);
        }

        return res;
    }

    public Long hincrby(String key, String field, Long value) {
        Jedis jedis = null;
        Long res = null;

        try {
            jedis = pool.getResource();
            res = jedis.hincrBy(key, field, value);
        } catch (Exception var10) {
            jedis.close();
            var10.printStackTrace();
        } finally {
            returnResource(pool, jedis);
        }

        return res;
    }

    public Boolean hexists(String key, String field) {
        Jedis jedis = null;
        Boolean res = false;

        try {
            jedis = pool.getResource();
            res = jedis.hexists(key, field);
        } catch (Exception var9) {
            jedis.close();
            var9.printStackTrace();
        } finally {
            returnResource(pool, jedis);
        }

        return res;
    }

    public Long hlen(String key) {
        Jedis jedis = null;
        Long res = null;

        try {
            jedis = pool.getResource();
            res = jedis.hlen(key);
        } catch (Exception var8) {
            jedis.close();
            var8.printStackTrace();
        } finally {
            returnResource(pool, jedis);
        }

        return res;
    }

    public Long hdel(String key, String... fields) {
        Jedis jedis = null;
        Long res = null;

        try {
            jedis = pool.getResource();
            res = jedis.hdel(key, fields);
        } catch (Exception var9) {
            jedis.close();
            var9.printStackTrace();
        } finally {
            returnResource(pool, jedis);
        }

        return res;
    }

    public Set<String> hkeys(String key) {
        Jedis jedis = null;
        Set res = null;

        try {
            jedis = pool.getResource();
            res = jedis.hkeys(key);
        } catch (Exception var8) {
            jedis.close();
            var8.printStackTrace();
        } finally {
            returnResource(pool, jedis);
        }

        return res;
    }

    public List<String> hvals(String key) {
        Jedis jedis = null;
        List res = null;

        try {
            jedis = pool.getResource();
            res = jedis.hvals(key);
        } catch (Exception var8) {
            jedis.close();
            var8.printStackTrace();
        } finally {
            returnResource(pool, jedis);
        }

        return res;
    }

    public Map<String, String> hgetall(String key) {
        Jedis jedis = null;
        Map res = null;

        try {
            jedis = pool.getResource();
            res = jedis.hgetAll(key);
        } catch (Exception var8) {
            jedis.close();
            var8.printStackTrace();
        } finally {
            returnResource(pool, jedis);
        }

        return res;
    }

    public Long lpush(String key, String... strs) {
        Jedis jedis = null;
        Long res = null;

        try {
            jedis = pool.getResource();
            res = jedis.lpush(key, strs);
        } catch (Exception var9) {
            jedis.close();
            var9.printStackTrace();
        } finally {
            returnResource(pool, jedis);
        }

        return res;
    }

    public Long rpush(String key, String... strs) {
        Jedis jedis = null;
        Long res = null;

        try {
            jedis = pool.getResource();
            res = jedis.rpush(key, strs);
        } catch (Exception var9) {
            jedis.close();
            var9.printStackTrace();
        } finally {
            returnResource(pool, jedis);
        }

        return res;
    }

    public Long linsert(String key, BinaryClient.LIST_POSITION where, String pivot, String value) {
        Jedis jedis = null;
        Long res = null;

        try {
            jedis = pool.getResource();
            res = jedis.linsert(key, where, pivot, value);
        } catch (Exception var11) {
            jedis.close();
            var11.printStackTrace();
        } finally {
            returnResource(pool, jedis);
        }

        return res;
    }

    public String lset(String key, Long index, String value) {
        Jedis jedis = null;
        String res = null;

        try {
            jedis = pool.getResource();
            res = jedis.lset(key, index, value);
        } catch (Exception var10) {
            jedis.close();
            var10.printStackTrace();
        } finally {
            returnResource(pool, jedis);
        }

        return res;
    }

    public Long lrem(String key, long count, String value) {
        Jedis jedis = null;
        Long res = null;

        try {
            jedis = pool.getResource();
            res = jedis.lrem(key, count, value);
        } catch (Exception var11) {
            jedis.close();
            var11.printStackTrace();
        } finally {
            returnResource(pool, jedis);
        }

        return res;
    }

    public String ltrim(String key, long start, long end) {
        Jedis jedis = null;
        String res = null;

        try {
            jedis = pool.getResource();
            res = jedis.ltrim(key, start, end);
        } catch (Exception var12) {
            jedis.close();
            var12.printStackTrace();
        } finally {
            returnResource(pool, jedis);
        }

        return res;
    }

    public String lpop(String key) {
        Jedis jedis = null;
        String res = null;

        try {
            jedis = pool.getResource();
            res = jedis.lpop(key);
        } catch (Exception var8) {
            jedis.close();
            var8.printStackTrace();
        } finally {
            returnResource(pool, jedis);
        }

        return res;
    }

    public String rpop(String key) {
        Jedis jedis = null;
        String res = null;

        try {
            jedis = pool.getResource();
            res = jedis.rpop(key);
        } catch (Exception var8) {
            jedis.close();
            var8.printStackTrace();
        } finally {
            returnResource(pool, jedis);
        }

        return res;
    }

    public String rpoplpush(String srckey, String dstkey) {
        Jedis jedis = null;
        String res = null;

        try {
            jedis = pool.getResource();
            res = jedis.rpoplpush(srckey, dstkey);
        } catch (Exception var9) {
            jedis.close();
            var9.printStackTrace();
        } finally {
            returnResource(pool, jedis);
        }

        return res;
    }

    public String lindex(String key, long index) {
        Jedis jedis = null;
        String res = null;

        try {
            jedis = pool.getResource();
            res = jedis.lindex(key, index);
        } catch (Exception var10) {
            jedis.close();
            var10.printStackTrace();
        } finally {
            returnResource(pool, jedis);
        }

        return res;
    }

    public Long llen(String key) {
        Jedis jedis = null;
        Long res = null;

        try {
            jedis = pool.getResource();
            res = jedis.llen(key);
        } catch (Exception var8) {
            jedis.close();
            var8.printStackTrace();
        } finally {
            returnResource(pool, jedis);
        }

        return res;
    }

    public List<String> lrange(String key, long start, long end) {
        Jedis jedis = null;
        List res = null;

        try {
            jedis = pool.getResource();
            res = jedis.lrange(key, start, end);
        } catch (Exception var12) {
            jedis.close();
            var12.printStackTrace();
        } finally {
            returnResource(pool, jedis);
        }

        return res;
    }

    public Long sadd(String key, String... members) {
        Jedis jedis = null;
        Long res = null;

        try {
            jedis = pool.getResource();
            res = jedis.sadd(key, members);
        } catch (Exception var9) {
            jedis.close();
            var9.printStackTrace();
        } finally {
            returnResource(pool, jedis);
        }

        return res;
    }

    public Long srem(String key, String... members) {
        Jedis jedis = null;
        Long res = null;

        try {
            jedis = pool.getResource();
            res = jedis.srem(key, members);
        } catch (Exception var9) {
            jedis.close();
            var9.printStackTrace();
        } finally {
            returnResource(pool, jedis);
        }

        return res;
    }

    public String spop(String key) {
        Jedis jedis = null;
        String res = null;

        try {
            jedis = pool.getResource();
            res = jedis.spop(key);
        } catch (Exception var8) {
            jedis.close();
            var8.printStackTrace();
        } finally {
            returnResource(pool, jedis);
        }

        return res;
    }

    public Set<String> sdiff(String... keys) {
        Jedis jedis = null;
        Set res = null;

        try {
            jedis = pool.getResource();
            res = jedis.sdiff(keys);
        } catch (Exception var8) {
            jedis.close();
            var8.printStackTrace();
        } finally {
            returnResource(pool, jedis);
        }

        return res;
    }

    public Long sdiffstore(String dstkey, String... keys) {
        Jedis jedis = null;
        Long res = null;

        try {
            jedis = pool.getResource();
            res = jedis.sdiffstore(dstkey, keys);
        } catch (Exception var9) {
            jedis.close();
            var9.printStackTrace();
        } finally {
            returnResource(pool, jedis);
        }

        return res;
    }

    public Set<String> sinter(String... keys) {
        Jedis jedis = null;
        Set res = null;

        try {
            jedis = pool.getResource();
            res = jedis.sinter(keys);
        } catch (Exception var8) {
            jedis.close();
            var8.printStackTrace();
        } finally {
            returnResource(pool, jedis);
        }

        return res;
    }

    public Long sinterstore(String dstkey, String... keys) {
        Jedis jedis = null;
        Long res = null;

        try {
            jedis = pool.getResource();
            res = jedis.sinterstore(dstkey, keys);
        } catch (Exception var9) {
            jedis.close();
            var9.printStackTrace();
        } finally {
            returnResource(pool, jedis);
        }

        return res;
    }

    public Set<String> sunion(String... keys) {
        Jedis jedis = null;
        Set res = null;

        try {
            jedis = pool.getResource();
            res = jedis.sunion(keys);
        } catch (Exception var8) {
            jedis.close();
            var8.printStackTrace();
        } finally {
            returnResource(pool, jedis);
        }

        return res;
    }

    public Long sunionstore(String dstkey, String... keys) {
        Jedis jedis = null;
        Long res = null;

        try {
            jedis = pool.getResource();
            res = jedis.sunionstore(dstkey, keys);
        } catch (Exception var9) {
            jedis.close();
            var9.printStackTrace();
        } finally {
            returnResource(pool, jedis);
        }

        return res;
    }

    public Long smove(String srckey, String dstkey, String member) {
        Jedis jedis = null;
        Long res = null;

        try {
            jedis = pool.getResource();
            res = jedis.smove(srckey, dstkey, member);
        } catch (Exception var10) {
            jedis.close();
            var10.printStackTrace();
        } finally {
            returnResource(pool, jedis);
        }

        return res;
    }

    public Long scard(String key) {
        Jedis jedis = null;
        Long res = null;

        try {
            jedis = pool.getResource();
            res = jedis.scard(key);
        } catch (Exception var8) {
            jedis.close();
            var8.printStackTrace();
        } finally {
            returnResource(pool, jedis);
        }

        return res;
    }

    public Boolean sismember(String key, String member) {
        Jedis jedis = null;
        Boolean res = null;

        try {
            jedis = pool.getResource();
            res = jedis.sismember(key, member);
        } catch (Exception var9) {
            jedis.close();
            var9.printStackTrace();
        } finally {
            returnResource(pool, jedis);
        }

        return res;
    }

    public String srandmember(String key) {
        Jedis jedis = null;
        String res = null;

        try {
            jedis = pool.getResource();
            res = jedis.srandmember(key);
        } catch (Exception var8) {
            jedis.close();
            var8.printStackTrace();
        } finally {
            returnResource(pool, jedis);
        }

        return res;
    }

    public Set<String> smembers(String key) {
        Jedis jedis = null;
        Set res = null;

        try {
            jedis = pool.getResource();
            res = jedis.smembers(key);
        } catch (Exception var8) {
            jedis.close();
            var8.printStackTrace();
        } finally {
            returnResource(pool, jedis);
        }

        return res;
    }

    public Long zadd(String key, Map<String, Double> scoreMembers) {
        Jedis jedis = null;
        Long res = null;

        try {
            jedis = pool.getResource();
            res = jedis.zadd(key, scoreMembers);
        } catch (Exception var9) {
            jedis.close();
            var9.printStackTrace();
        } finally {
            returnResource(pool, jedis);
        }

        return res;
    }

    public Long zadd(String key, double score, String member) {
        Jedis jedis = null;
        Long res = null;

        try {
            jedis = pool.getResource();
            res = jedis.zadd(key, score, member);
        } catch (Exception var11) {
            jedis.close();
            var11.printStackTrace();
        } finally {
            returnResource(pool, jedis);
        }

        return res;
    }

    public Long zrem(String key, String... members) {
        Jedis jedis = null;
        Long res = null;

        try {
            jedis = pool.getResource();
            res = jedis.zrem(key, members);
        } catch (Exception var9) {
            jedis.close();
            var9.printStackTrace();
        } finally {
            returnResource(pool, jedis);
        }

        return res;
    }

    public Double zincrby(String key, double score, String member) {
        Jedis jedis = null;
        Double res = null;

        try {
            jedis = pool.getResource();
            res = jedis.zincrby(key, score, member);
        } catch (Exception var11) {
            jedis.close();
            var11.printStackTrace();
        } finally {
            returnResource(pool, jedis);
        }

        return res;
    }

    public Long zrank(String key, String member) {
        Jedis jedis = null;
        Long res = null;

        try {
            jedis = pool.getResource();
            res = jedis.zrank(key, member);
        } catch (Exception var9) {
            jedis.close();
            var9.printStackTrace();
        } finally {
            returnResource(pool, jedis);
        }

        return res;
    }

    public Long zrevrank(String key, String member) {
        Jedis jedis = null;
        Long res = null;

        try {
            jedis = pool.getResource();
            res = jedis.zrevrank(key, member);
        } catch (Exception var9) {
            jedis.close();
            var9.printStackTrace();
        } finally {
            returnResource(pool, jedis);
        }

        return res;
    }

    public Set<String> zrevrange(String key, long start, long end) {
        Jedis jedis = null;
        Set res = null;

        try {
            jedis = pool.getResource();
            res = jedis.zrevrange(key, start, end);
        } catch (Exception var12) {
            jedis.close();
            var12.printStackTrace();
        } finally {
            returnResource(pool, jedis);
        }

        return res;
    }

    public Set<String> zrangebyscore(String key, String max, String min) {
        Jedis jedis = null;
        Set res = null;

        try {
            jedis = pool.getResource();
            res = jedis.zrevrangeByScore(key, max, min);
        } catch (Exception var10) {
            jedis.close();
            var10.printStackTrace();
        } finally {
            returnResource(pool, jedis);
        }

        return res;
    }

    public Set<String> zrangeByScore(String key, double max, double min) {
        Jedis jedis = null;
        Set res = null;

        try {
            jedis = pool.getResource();
            res = jedis.zrevrangeByScore(key, max, min);
        } catch (Exception var12) {
            jedis.close();
            var12.printStackTrace();
        } finally {
            returnResource(pool, jedis);
        }

        return res;
    }

    public Long zcount(String key, String min, String max) {
        Jedis jedis = null;
        Long res = null;

        try {
            jedis = pool.getResource();
            res = jedis.zcount(key, min, max);
        } catch (Exception var10) {
            jedis.close();
            var10.printStackTrace();
        } finally {
            returnResource(pool, jedis);
        }

        return res;
    }

    public Long zcard(String key) {
        Jedis jedis = null;
        Long res = null;

        try {
            jedis = pool.getResource();
            res = jedis.zcard(key);
        } catch (Exception var8) {
            jedis.close();
            var8.printStackTrace();
        } finally {
            returnResource(pool, jedis);
        }

        return res;
    }

    public Double zscore(String key, String member) {
        Jedis jedis = null;
        Double res = null;

        try {
            jedis = pool.getResource();
            res = jedis.zscore(key, member);
        } catch (Exception var9) {
            jedis.close();
            var9.printStackTrace();
        } finally {
            returnResource(pool, jedis);
        }

        return res;
    }

    public Long zremrangeByRank(String key, long start, long end) {
        Jedis jedis = null;
        Long res = null;

        try {
            jedis = pool.getResource();
            res = jedis.zremrangeByRank(key, start, end);
        } catch (Exception var12) {
            jedis.close();
            var12.printStackTrace();
        } finally {
            returnResource(pool, jedis);
        }

        return res;
    }

    public Long zremrangeByScore(String key, double start, double end) {
        Jedis jedis = null;
        Long res = null;

        try {
            jedis = pool.getResource();
            res = jedis.zremrangeByScore(key, start, end);
        } catch (Exception var12) {
            jedis.close();
            var12.printStackTrace();
        } finally {
            returnResource(pool, jedis);
        }

        return res;
    }

    public Set<String> keys(String pattern) {
        Jedis jedis = null;
        Set res = null;

        try {
            jedis = pool.getResource();
            res = jedis.keys(pattern);
        } catch (Exception var8) {
            jedis.close();
            var8.printStackTrace();
        } finally {
            returnResource(pool, jedis);
        }

        return res;
    }

    public String type(String key) {
        Jedis jedis = null;
        String res = null;

        try {
            jedis = pool.getResource();
            res = jedis.type(key);
        } catch (Exception var8) {
            jedis.close();
            var8.printStackTrace();
        } finally {
            returnResource(pool, jedis);
        }

        return res;
    }

    public String setList(byte[] key, byte[] value) {
        Jedis jedis = null;
        String res = null;

        try {
            jedis = pool.getResource();
            res = jedis.set(key, value);
        } catch (Exception var9) {
            jedis.close();
            var9.printStackTrace();
        } finally {
            returnResource(pool, jedis);
        }

        return res;
    }

    public byte[] getList(byte[] key) {
        Jedis jedis = null;
        byte[] res = null;

        try {
            jedis = pool.getResource();
            res = jedis.get(key);
        } catch (Exception var8) {
            jedis.close();
            var8.printStackTrace();
        } finally {
            returnResource(pool, jedis);
        }

        return res;
    }

    private static void returnResource(JedisPool pool, Jedis jedis) {
        if (jedis != null) {
            jedis.close();
        }

    }

    public static void main(String[] args) {
        ResourceBundle bundle = ResourceBundle.getBundle("redis");
        RedisUtil redis = new RedisUtil(bundle);
        redis.set("abcd123", "dddd");
        System.out.println(redis.get("abcd123"));
    }
}
