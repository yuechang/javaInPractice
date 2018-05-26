/*!
 * Copyright(c) 2017 Yue Chang
 * MIT Licensed
 */
package com.yc.redis.base;

import redis.clients.jedis.Jedis;

/**
 * @author Yue Chang
 * @ClassName: JedisUtil
 * @Description: Redis连接工具类
 * @date 2018/5/26 22:42
 */
public class JedisUtil {

    public static Jedis getJedis(){
        Jedis jedis = new Jedis("127.0.0.1", 6379);
        return jedis;
    }

    public static Jedis getJedis(String host, int port){
        Jedis jedis = new Jedis(host, port);
        return jedis;
    }

    public static Jedis getJedis(String host, int port, String password){
        Jedis jedis = getJedis(host, port);
        if (jedis == null) {
            return jedis;
        }
        jedis.auth(password);
        return jedis;
    }
}

