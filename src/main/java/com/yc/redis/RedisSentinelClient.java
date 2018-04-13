/*!
 * Copyright(c) 2017 Yue Chang
 * MIT Licensed
 */
package com.yc.redis;

import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.JedisSentinelPool;

import java.util.HashSet;
import java.util.Set;

/**
 * @author Yue Chang
 * @ClassName: RedisClient
 * @Description: redis-sentinel客户端例子
 * @date 2018/4/4 23:46
 */
public class RedisSentinelClient {
    public static void main(String[] args) {
        JedisPoolConfig poolConfig = new JedisPoolConfig();
        String masterName = args[0];
        Set<String> sentinels = new HashSet<String>();
        sentinels.add(args[1]);
        sentinels.add(args[2]);
        sentinels.add(args[3]);
        String password = args[4];
        JedisSentinelPool jedisSentinelPool = new JedisSentinelPool(masterName, sentinels, poolConfig, 5000, password);
        HostAndPort currentHostMaster = jedisSentinelPool.getCurrentHostMaster();
        System.out.println(currentHostMaster.getHost()+"--"+currentHostMaster.getPort());//获取主节点的信息
        Jedis resource = jedisSentinelPool.getResource();
        String value = resource.get("key");
        System.out.println(value);//获得键a对应的value值
        // 关闭连接
        resource.close();
    }
}

