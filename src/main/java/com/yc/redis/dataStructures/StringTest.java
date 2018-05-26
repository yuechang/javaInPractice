/*!
 * Copyright(c) 2017 Yue Chang
 * MIT Licensed
 */
package com.yc.redis.dataStructures;

import com.yc.redis.base.JedisUtil;
import redis.clients.jedis.Jedis;

import java.util.concurrent.TimeUnit;

/**
 * @author Yue Chang
 * @ClassName: StringTest
 * @Description: String类型数据结构示例
 * @date 2018/5/26 22:41
 */
public class StringTest {

    public static void main(String[] args) throws InterruptedException {

        Jedis jedis = JedisUtil.getJedis(args[0], Integer.valueOf(args[1]), args[2]);

        String key = "key";
        String strValue = "value";
        int stepLength = 10;
        int expireTime = 5;

        // 设置key值
        String setResult = jedis.set(key, strValue);
        System.out.println("set(" + key + " , " + strValue + ")" + " return : " + setResult);

        // 获得key值
        String result = jedis.get(key);
        System.out.println("get(" + key + ")  return : " + result);

        // 判断key是否存在
        Boolean existsResult = jedis.exists(key);
        System.out.println("exists(" + key + ")  return : " + existsResult);
        // 删除key
        Long delResult = jedis.del(key);
        System.out.println("del(" + key + ") return : " + delResult);
        // 判断key是否存在
        existsResult = jedis.exists(key);
        System.out.println("exists(" + key + ")  return : " + existsResult);
        // 删除key
        delResult = jedis.del(key);
        System.out.println("del(" + key + ") return : " + delResult);

        // key自增1
        Long incrResult = jedis.incr(key);
        System.out.println("incr(" + key + ") return : " + incrResult);

        // key自增,指定步长
        Long incrByResult = jedis.incrBy(key, stepLength);
        System.out.println("incrBy(" + key + "," + stepLength + ") return : " + incrByResult);

        // key自减1
        Long decrResult = jedis.decr(key);
        System.out.println("decr(" + key + ") return : " + decrResult);

        // key自减，指定步长
        Long decrByResult = jedis.decrBy(key, stepLength);
        System.out.println("decrBy(" + key + "," + stepLength + ") return : " + decrByResult);

        // 设置key值，指定存活时间(秒数)
        String setexResult = jedis.setex(key, expireTime, strValue);
        System.out.println("setex(" + key + "," + expireTime + "," + strValue + ") return : " + setexResult);
        setexResult = jedis.setex(key, expireTime, strValue);
        System.out.println("setex(" + key + "," + expireTime + "," + strValue + ") return : " + setexResult);

        // 删除key
        delResult = jedis.del(key);
        System.out.println("del(" + key + ") return : " + delResult);

        // 设置key值。key不存在才会设置，如果key存在则回滚操作，结果0:设置失败，1:设置成功
        Long setnxResult = jedis.setnx(key, strValue);
        System.out.println("setnx(" + key + "," + strValue + ") return : " + setnxResult);
        setnxResult = jedis.setnx(key, strValue);
        System.out.println("setnx(" + key + "," + strValue + ") return : " + setnxResult);
        Long expireResult = jedis.expire(key, expireTime);
        System.out.println("expire(" + key + "," + expireTime + ") return : " + expireResult);

        // 判断key是否存在
        existsResult = jedis.exists(key);
        System.out.println("exists(" + key + ")  return : " + existsResult);
        // 休眠5秒
        System.out.println("---------休眠5秒---------");
        TimeUnit.SECONDS.sleep(5);
        // 判断key是否存在
        existsResult = jedis.exists(key);
        System.out.println("exists(" + key + ")  return : " + existsResult);
    }
}
/*

结果输出：
set(key , value) return : OK
get(key)  return : value
exists(key)  return : true
del(key) return : 1
exists(key)  return : false
del(key) return : 0
incr(key) return : 1
incrBy(key,10) return : 11
decr(key) return : 10
decrBy(key,10) return : 0
setex(key,5,value) return : OK
setex(key,5,value) return : OK
del(key) return : 1
setnx(key,value) return : 1
setnx(key,value) return : 0
expire(key,5) return : 1
exists(key)  return : true
---------休眠5秒---------
exists(key)  return : false

setnx为实现分布式锁的底层方法，需要注意超时时间
 */
