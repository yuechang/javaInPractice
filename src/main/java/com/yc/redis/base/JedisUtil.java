/*!
 * Copyright(c) 2017 Yue Chang
 * MIT Licensed
 */
package com.yc.redis.base;

import org.apache.commons.lang3.ArrayUtils;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.JedisSentinelPool;

import java.util.HashSet;
import java.util.Set;

/**
 * @author Yue Chang
 * @ClassName: JedisUtil
 * @Description: Redis连接工具类
 * @date 2018/5/26 22:42
 */
public class JedisUtil {

    /**
     * 获取本机jedis连接
     */
    public static Jedis getJedis(){
        Jedis jedis = new Jedis("127.0.0.1", 6379);
        return jedis;
    }

    /**
     * 获取jedis连接
     * @param host 主机IP
     * @param port 主机端口
     * @return
     */
    public static Jedis getJedis(String host, int port){
        Jedis jedis = new Jedis(host, port);
        return jedis;
    }

    /**
     * 获取jedis连接
     * @param host 主机IP
     * @param port 主机端口
     * @param password 密码
     * @return
     */
    public static Jedis getJedis(String host, int port, String password){
        Jedis jedis = getJedis(host, port);
        if (jedis == null) {
            return jedis;
        }
        jedis.auth(password);
        return jedis;
    }

    /**
     * 获得redis-sentinel模式下Jedis连接池
     * @param masterName 主名称
     * @param timeout 超时时间
     * @param password 密码
     * @param hostAndIps 主机及端口信息，为可变参数
     * @return
     */
    public static JedisSentinelPool getJedisSentinelPool(String masterName, int timeout, String password, String... hostAndIps) {
        JedisPoolConfig poolConfig = new JedisPoolConfig();
        Set<String> sentinels = new HashSet<String>();
        if (ArrayUtils.isEmpty(hostAndIps))
            return null;
        for (String hostAndIp : hostAndIps) {
            sentinels.add(hostAndIp);
        }
        JedisSentinelPool jedisSentinelPool = new JedisSentinelPool(masterName, sentinels, poolConfig, timeout, password);
        return jedisSentinelPool;
    }

    /**
     * 释放jedis连接
     * @param jedis
     */
    public static void close(Jedis jedis){

        if (jedis != null)
            jedis.close();
    }
}

