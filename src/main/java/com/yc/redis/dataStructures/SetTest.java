/*!
 * Copyright(c) 2017 Yue Chang
 * MIT Licensed
 */
package com.yc.redis.dataStructures;

import com.yc.redis.base.JedisUtil;
import redis.clients.jedis.Jedis;

import java.util.HashMap;
import java.util.Set;

/**
 * @author Yue Chang
 * @ClassName: SetTest
 * @Description: Set类型数据结构操作示例
 * @date 2018/5/28 12:22
 */
public class SetTest {

    public static void main(String[] args) {
        Jedis jedis = null;
        try {
            // 获取jedis连接
            jedis = JedisUtil.getJedis(args[0], Integer.valueOf(args[1]), args[2]);
            // 测试set数据结构
            setTest(jedis);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // 释放jedis连接
            JedisUtil.close(jedis);
        }
    }

    private static void setTest(Jedis jedis) {

        String ANOTHER_SET_KEY = "anotherSetKey";
        // set并集KEY
        String UNION_STORE_KEY = "unionstoreKey";
        // set交集KEY
        String INTER_STORE_KEY = "interstoreKey";

        String SET_KEY = "setKey";
        String SET_VALUE_ONE = "value01";
        String SET_VALUE_TWO = "value02";
        String SET_VALUE_THREE = "value03";
        String SET_VALUE_FOUR = "value04";
        String SET_VALUE_FIVE = "value05";


        // 往set对象中添加值,返回添加成功元素个数
        Long saddResult = jedis.sadd(SET_KEY,
                SET_VALUE_ONE, SET_VALUE_TWO, SET_VALUE_THREE, SET_VALUE_FOUR);
        System.out.println("sadd(" + SET_KEY + "," + SET_VALUE_ONE + ","
                + SET_VALUE_TWO + "," + SET_VALUE_THREE + "," + SET_VALUE_FOUR +
                ")" + " return : " + saddResult);

        // 取得set中的所有的值
        Set<String> smembersResult = jedis.smembers(SET_KEY);
        System.out.println("smembers(" + SET_KEY + ")" + " return : " + smembersResult);

        // 判断set中是否存在某个值
        Boolean sismemberResult = jedis.sismember(SET_KEY, SET_VALUE_ONE);
        System.out.println("sismember(" + SET_KEY + "," + SET_VALUE_ONE + ")" + " return : " + sismemberResult);
        sismemberResult = jedis.sismember(SET_KEY, SET_VALUE_FIVE);
        System.out.println("sismember(" + SET_KEY + "," + SET_VALUE_FIVE + ")" + " return : " + sismemberResult);

        // 从set中随机取一个值
        String srandmemberResult = jedis.srandmember(SET_KEY);
        System.out.println("srandmember(" + SET_KEY + ")" + " return : " + srandmemberResult);

        // 从set中删除值
        Long sremResult = jedis.srem(SET_KEY, SET_VALUE_THREE, SET_VALUE_FOUR);
        System.out.println("srem(" + SET_KEY + "," +SET_VALUE_FOUR + "," + SET_VALUE_FOUR + ")" + " return : " + sremResult);

        // 返回set的元素个数
        Long scardResult = jedis.scard(SET_KEY);
        System.out.println("scard(" + SET_KEY + ")" + " return : " + scardResult);

        // 给另一个set集合添加值
        saddResult = jedis.sadd(ANOTHER_SET_KEY, SET_VALUE_ONE, SET_VALUE_TWO,
                SET_VALUE_THREE, SET_VALUE_FOUR, SET_VALUE_FIVE);
        System.out.println("zadd(" + ANOTHER_SET_KEY + "," + SET_VALUE_ONE + "," + SET_VALUE_TWO +
                "," + SET_VALUE_THREE + "," + SET_VALUE_FOUR + "," + SET_VALUE_FIVE +
                ")" + " return : " + saddResult);

        // 计算给定的一个或者多个set集合的并集，并将并集存入到destination
        Long sunionstoreResult = jedis.sunionstore(UNION_STORE_KEY, SET_KEY, ANOTHER_SET_KEY);
        System.out.println("sunionstore(" + UNION_STORE_KEY + ","
                + SET_KEY + "," + ANOTHER_SET_KEY + ")" + " return : " + sunionstoreResult);
        // 取得set中的所有的值
        smembersResult = jedis.smembers(UNION_STORE_KEY);
        System.out.println("smembers(" + UNION_STORE_KEY + ")" + " return : " + smembersResult);

        // 计算给定的一个或者多个set集合的并集，并将并集存入到destination
        Long sinterstoreResult = jedis.sinterstore(UNION_STORE_KEY, SET_KEY, ANOTHER_SET_KEY);
        System.out.println("sinterstore(" + UNION_STORE_KEY + ","
                + SET_KEY + "," + ANOTHER_SET_KEY + ")" + " return : " + sinterstoreResult);
        // 取得set中的所有的值
        smembersResult = jedis.smembers(UNION_STORE_KEY);
        System.out.println("smembers(" + UNION_STORE_KEY + ")" + " return : " + smembersResult);

        jedis.del(SET_KEY);
    }
}
/*
程序输出：
sadd(setKey,value01,value02,value03,value04) return : 2
smembers(setKey) return : [value02, value01, value03, value04]
sismember(setKey,value01) return : true
sismember(setKey,value05) return : false
srandmember(setKey) return : value03
srem(setKey,value04,value04) return : 2
scard(setKey) return : 2
zadd(anotherSetKey,value01,value02,value03,value04,value05) return : 0
sunionstore(unionstoreKey,setKey,anotherSetKey) return : 5
smembers(unionstoreKey) return : [value05, value03, value02, value04, value01]
sinterstore(unionstoreKey,setKey,anotherSetKey) return : 2
smembers(unionstoreKey) return : [value01, value02]

srandmember(setKey)随机获取元素的程序输出结果可能会不一样
 */
