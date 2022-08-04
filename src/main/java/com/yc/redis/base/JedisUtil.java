/* Copyright(c) 2022 Yue Chang and/or its affiliates. All right reserved. */
package com.yc.redis.base;

import org.apache.commons.lang3.ArrayUtils;
import redis.clients.jedis.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
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
     * 获得连接池配置对象
     * @param maxTotal 最大连接数
     * @param maxIdle 最大空闲数
     * @param minIdle 最小空闲数
     * @param maxWaitMills 等待可用连接的最大时间，单位毫秒，默认值为-1，表示永不超时
     * @return
     */
    public static JedisPoolConfig getJedisPoolConfig(int maxTotal, int maxIdle, int minIdle, int maxWaitMills) {

        JedisPoolConfig config = new JedisPoolConfig();
        config.setMaxTotal(maxTotal);
        config.setMaxIdle(maxIdle);
        config.setMinIdle(minIdle);
        config.setMaxWaitMillis(maxWaitMills);
        return config;
    }

    /**
     * 获得普通连接池对象
     * @param host 主机地址
     * @param port 端口号
     * @param jedisPoolConfig 连接池配置对象
     * @return
     */
    public static JedisPool getJedisPool(String host, int port, JedisPoolConfig jedisPoolConfig) {
        if (null == jedisPoolConfig) {
            jedisPoolConfig = new JedisPoolConfig();
        }
        return new JedisPool(jedisPoolConfig,host,port);
    }


    /**
     * 获得切片连接池连接信息列表
     * @param masterName 主机名称
     * @param hostAndIps 主机及端口信息，为可变参数,例如：127.0.0.1:6379
     * @return
     */
    public static List<JedisShardInfo> getJedisShardInfoList(String masterName,String... hostAndIps) {

        List<JedisShardInfo> list = new ArrayList<>();

        if (ArrayUtils.isEmpty(hostAndIps)) {
            return null;
        }
        for (String hostAndIp : hostAndIps) {

            String[] hostAndIpArr = hostAndIp.split(":");
            if (ArrayUtils.isEmpty(hostAndIpArr) || hostAndIpArr.length != 2) {
                continue;
            }
            JedisShardInfo jedisShardInfo = new JedisShardInfo(hostAndIpArr[0], Integer.valueOf(hostAndIpArr[1]), masterName);
            list.add(jedisShardInfo);
        }
        return list;
    }

    /**
     * 获得切片连接池对象
     * @param jedisPoolConfig 连接池配置信息
     * @param shards 分片服务器列表
     * @return
     */
    public static ShardedJedisPool getShardedJedisPool(JedisPoolConfig jedisPoolConfig, List<JedisShardInfo> shards) {

        if (null == jedisPoolConfig) {
            jedisPoolConfig = new JedisPoolConfig();
        }
        return new ShardedJedisPool(jedisPoolConfig, shards);
    }

    /**
     * 获得redis-sentinel模式下Jedis连接池
     * @param masterName 主名称
     * @param timeout 超时时间
     * @param password 密码
     * @param hostAndIps 主机及端口信息，为可变参数,例如：127.0.0.1:6379
     * @return
     */
    public static JedisSentinelPool getJedisSentinelPool(String masterName, int timeout, String password, JedisPoolConfig jedisPoolConfig, String... hostAndIps) {

        if (null == jedisPoolConfig) {
            jedisPoolConfig = new JedisPoolConfig();
        }
        Set<String> sentinels = new HashSet<String>();
        if (ArrayUtils.isEmpty(hostAndIps)) {
            return null;
        }
        for (String hostAndIp : hostAndIps) {
            sentinels.add(hostAndIp);
        }
        return new JedisSentinelPool(masterName, sentinels, jedisPoolConfig, timeout, password);
    }

    /**
     * 释放jedis连接
     * @param jedis
     */
    public static void close(Jedis jedis){

        if (jedis != null) {
            jedis.close();
        }
    }
}