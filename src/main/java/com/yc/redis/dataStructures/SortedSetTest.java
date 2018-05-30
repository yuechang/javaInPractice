/*!
 * Copyright(c) 2017 Yue Chang
 * MIT Licensed
 */
package com.yc.redis.dataStructures;

import com.yc.redis.base.JedisUtil;
import redis.clients.jedis.Jedis;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @author Yue Chang
 * @ClassName: SortedSetTest
 * @Description: SortedSet(有序集)类型数据结构示例
 * @date 2018/5/29 20:22
 */
public class SortedSetTest {

    public static void main(String[] args) {
        Jedis jedis = null;
        try {
            // 获取jedis连接
            jedis = JedisUtil.getJedis(args[0], Integer.valueOf(args[1]), args[2]);
            // 测试sortedSet数据结构
            sortedSetTest(jedis);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // 释放jedis连接
            JedisUtil.close(jedis);
        }
    }

    private static void sortedSetTest(Jedis jedis) {

        // 有序集键
        String SORTED_SET_KEY = "sortedSetKey";
        // 有序集分值
        int SORTED_SET_SCORE_1 = 1;
        int SORTED_SET_SCORE_2 = 2;
        int SORTED_SET_SCORE_3 = 3;
        double SORTED_SET_SCORE_DOUBLE_4 = 4;
        double SORTED_SET_SCORE_DOUBLE_5 = 5;
        double SORTED_SET_SCORE_DOUBLE_6 = 6;
        // 有序集成员
        String SORTED_SET_MEMBER_1 = "member1";
        String SORTED_SET_MEMBER_2 = "member2";
        String SORTED_SET_MEMBER_3 = "member3";
        String SORTED_SET_MEMBER_4 = "member4";
        String SORTED_SET_MEMBER_5 = "member5";
        String SORTED_SET_MEMBER_6 = "member6";
        String SORTED_SET_MEMBER_7 = "member7";
        // 最大最小分值
        int MAX_SCORE = 10;
        int MIN_SCORE = 1;
        // 分值
        int SCORE = 9;
        // 最大最小索引
        int END_INDEX = -1;
        int START_INDEX = 0;
        // 最小排名
        int FIRST_RANK = 0;
        // 大于小于等于之类的
        String LEFT_PARENTHESIS = "(";
        String LEFT_BRACKET = "[";

        // 将一个member元素和对应score值加入到有序集当中，在redis存入的为有序的。返回存入个数，1：成功存入1个，0：未存入
        Long zaddResult = jedis.zadd(SORTED_SET_KEY, SORTED_SET_SCORE_2, SORTED_SET_MEMBER_2);
        System.out.println("zadd(" + SORTED_SET_KEY + "," + SORTED_SET_SCORE_2 + "," + SORTED_SET_MEMBER_2 +")" + " return : " + zaddResult);
        zaddResult = jedis.zadd(SORTED_SET_KEY, SORTED_SET_SCORE_1, SORTED_SET_MEMBER_1);
        System.out.println("zadd(" + SORTED_SET_KEY + "," + SORTED_SET_SCORE_1 + "," + SORTED_SET_MEMBER_1 +")" + " return : " + zaddResult);
        zaddResult = jedis.zadd(SORTED_SET_KEY, SORTED_SET_SCORE_3, SORTED_SET_MEMBER_3);
        System.out.println("zadd(" + SORTED_SET_KEY + "," + SORTED_SET_SCORE_3 + "," + SORTED_SET_MEMBER_3 +")" + " return : " + zaddResult);

        Map<String, Double> map = new HashMap<>();
        map.put(SORTED_SET_MEMBER_6, SORTED_SET_SCORE_DOUBLE_6);
        map.put(SORTED_SET_MEMBER_4, SORTED_SET_SCORE_DOUBLE_4);
        map.put(SORTED_SET_MEMBER_5, SORTED_SET_SCORE_DOUBLE_5);
        // 将多个member元素和对应score值加入到有序集当中，在redis存入的为有序的，返回存入个数
        zaddResult = jedis.zadd(SORTED_SET_KEY, map);
        System.out.println("zadd(" + SORTED_SET_KEY + "," + map +")" + " return : " + zaddResult);

        // 获取有序集当中某个member对应score
        Double zscoreResult = jedis.zscore(SORTED_SET_KEY, SORTED_SET_MEMBER_1);
        System.out.println("zscore(" + SORTED_SET_KEY + "," + SORTED_SET_MEMBER_1 + ")" + " return : " + zscoreResult);

        // 获取有序集中member个数
        Long zcardResult = jedis.zcard(SORTED_SET_KEY);
        System.out.println("zcard(" + SORTED_SET_KEY + ")" + " return : " + zcardResult);

        // 获取有序集中，score在[min,max]之间成员的数量
        Long zcountResult = jedis.zcount(SORTED_SET_KEY, MIN_SCORE, MAX_SCORE);
        System.out.println("zcount(" + SORTED_SET_KEY + "," + MIN_SCORE + "," + MAX_SCORE + ")" + " return : " + zcountResult);

        // 将有序集中member增加score
        Double zincrbyResult = jedis.zincrby(SORTED_SET_KEY, SCORE, SORTED_SET_MEMBER_1);
        System.out.println("zincrby(" + SORTED_SET_KEY + "," + SCORE + "," + SORTED_SET_MEMBER_1 + ")" + " return : " + zincrbyResult);

        // 返回有序集中指定区间内的成员，其中成员的位置按score值递增排列
        Set<String> zrangeResult = jedis.zrange(SORTED_SET_KEY, START_INDEX, END_INDEX);
        System.out.println("zrange(" + SORTED_SET_KEY + "," + START_INDEX + "," + END_INDEX + ")" + " return : " + zrangeResult);
        // 返回有序集中指定区间内的成员，其中成员的位置按score值递减排列
        Set<String> zrevrangeResult = jedis.zrevrange(SORTED_SET_KEY, START_INDEX, END_INDEX);
        System.out.println("zrevrange(" + SORTED_SET_KEY + "," + START_INDEX + "," + END_INDEX + ")" + " return : " + zrevrangeResult);

        // 返回有序集中，所有score值在[min,max]之间成员，递增排列
        Set<String> zrangeByScoreResult = jedis.zrangeByScore(SORTED_SET_KEY, MIN_SCORE, MAX_SCORE);
        System.out.println("zrangeByScore(" + SORTED_SET_KEY + "," + MIN_SCORE + "," + MAX_SCORE + ")" + " return : " + zrangeByScoreResult);
        // 返回有序集中，所有score值在[min,max]之间成员，递减排列。注意后面的两个参数第一个是Max，跟zrangeByScore不一样
        Set<String> zrevrangeByScoreResult = jedis.zrevrangeByScore(SORTED_SET_KEY, MAX_SCORE, MIN_SCORE);
        System.out.println("zrevrangeByScore(" + SORTED_SET_KEY + "," + MAX_SCORE + "," + MIN_SCORE + ")" + " return : " + zrevrangeByScoreResult);

        // 返回member在按score值递增排列的有序集中的排名
        Long zrankResult = jedis.zrank(SORTED_SET_KEY, SORTED_SET_MEMBER_1);
        System.out.println("zrank(" + SORTED_SET_KEY + "," + SORTED_SET_MEMBER_1 + ")" + " return : " + zrankResult);

        // 返回member在按score值递减排列的有序集中的排名
        Long zrevrankResult = jedis.zrevrank(SORTED_SET_KEY, SORTED_SET_MEMBER_1);
        System.out.println("zrevrank(" + SORTED_SET_KEY + "," + SORTED_SET_MEMBER_1 + ")" + " return : " + zrevrankResult);

        // 移除有序集中的一个或者多个成员，不存在的成员将被忽视，返回移除个数
        Long zremResult = jedis.zrem(SORTED_SET_KEY, SORTED_SET_MEMBER_1, SORTED_SET_MEMBER_7);
        System.out.println("zrem(" + SORTED_SET_KEY + "," + SORTED_SET_MEMBER_1 + ")" + " return : " + zremResult);

        // 根据排名移除有序集中的成员
        Long zremrangeByRankResult = jedis.zremrangeByRank(SORTED_SET_KEY, FIRST_RANK, FIRST_RANK);
        System.out.println("zremrangeByRank(" + SORTED_SET_KEY + "," + FIRST_RANK + "," + FIRST_RANK + ")" + " return : " + zremrangeByRankResult);

        // 根据score移除有序集中的成员
        Long zremrangeByScoreResult = jedis.zremrangeByScore(SORTED_SET_KEY, SORTED_SET_SCORE_3, SORTED_SET_SCORE_3);
        System.out.println("zremrangeByScore(" + SORTED_SET_KEY + "," + SORTED_SET_SCORE_3 + "," + SORTED_SET_SCORE_3 + ")"
                + " return : " + zremrangeByScoreResult);

        zrangeResult = jedis.zrange(SORTED_SET_KEY, START_INDEX, END_INDEX);
        System.out.println("zrange(" + SORTED_SET_KEY + "," + START_INDEX + "," + END_INDEX + ")" + " return : " + zrangeResult);

        // 返回有序集成员(member)值介于min和max之间的成员
        Set<String> zrangeByLexResult = jedis.zrangeByLex(SORTED_SET_KEY,
                LEFT_BRACKET.concat(SORTED_SET_MEMBER_1),
                LEFT_BRACKET.concat(SORTED_SET_MEMBER_7));
        System.out.println("zrangeByLex(" + SORTED_SET_KEY + "," +
                LEFT_BRACKET.concat(SORTED_SET_MEMBER_1) + "," +
                LEFT_BRACKET.concat(SORTED_SET_MEMBER_7) + ")" + " return : " + zrangeByLexResult);


        // 计算有序集中成员(member)的数量
        Long zlexcountResult = jedis.zlexcount(SORTED_SET_KEY,
                LEFT_BRACKET.concat(SORTED_SET_MEMBER_1),
                LEFT_PARENTHESIS.concat(SORTED_SET_MEMBER_6));
        System.out.println("zlexcount(" + SORTED_SET_KEY + "," +
                LEFT_BRACKET.concat(SORTED_SET_MEMBER_1) + "," +
                LEFT_PARENTHESIS.concat(SORTED_SET_MEMBER_6) + ")" + " return : " + zlexcountResult);

        // 移除该集合中，成员(member)介于min和max范围内的所有元素,[:包括等于，（:不包括等于
        Long zremrangeByLexResult = jedis.zremrangeByLex(SORTED_SET_KEY,
                LEFT_BRACKET.concat(SORTED_SET_MEMBER_4),
                LEFT_BRACKET.concat(SORTED_SET_MEMBER_4));
        System.out.println("zremrangeByLexResult(" + SORTED_SET_KEY + "," +
                LEFT_BRACKET.concat(SORTED_SET_MEMBER_4) + "," +
                LEFT_BRACKET.concat(SORTED_SET_MEMBER_4) + ")" + " return : " + zremrangeByLexResult);

        // 删除有序集
        jedis.del(SORTED_SET_KEY);
    }
}
/*
程序输出：
zadd(sortedSetKey,2,member2) return : 1
zadd(sortedSetKey,1,member1) return : 1
zadd(sortedSetKey,3,member3) return : 1
zadd(sortedSetKey,{member6=6.0, member4=4.0, member5=5.0}) return : 3
zscore(sortedSetKey,member1) return : 1.0
zcard(sortedSetKey) return : 6
zcount(sortedSetKey,1,10) return : 6
zincrby(sortedSetKey,9,member1) return : 10.0
zrange(sortedSetKey,0,-1) return : [member2, member3, member4, member5, member6, member1]
zrevrange(sortedSetKey,0,-1) return : [member1, member6, member5, member4, member3, member2]
zrangeByScore(sortedSetKey,1,10) return : [member2, member3, member4, member5, member6, member1]
zrevrangeByScore(sortedSetKey,10,1) return : [member1, member6, member5, member4, member3, member2]
zrank(sortedSetKey,member1) return : 5
zrevrank(sortedSetKey,member1) return : 0
zrem(sortedSetKey,member1) return : 1
zremrangeByRank(sortedSetKey,0,0) return : 1
zremrangeByScore(sortedSetKey,3,3) return : 1
zrange(sortedSetKey,0,-1) return : [member4, member5, member6]
zrangeByLex(sortedSetKey,[member1,[member7) return : [member4, member5, member6]
zlexcount(sortedSetKey,[member1,(member6) return : 2
zremrangeByLexResult(sortedSetKey,[member4,[member4) return : 1

 */
