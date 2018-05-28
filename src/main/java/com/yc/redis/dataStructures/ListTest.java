/*!
 * Copyright(c) 2017 Yue Chang
 * MIT Licensed
 */
package com.yc.redis.dataStructures;

import com.yc.redis.base.JedisUtil;
import redis.clients.jedis.Jedis;

import java.util.List;

/**
 * @author Yue Chang
 * @ClassName: ListTest
 * @Description: List类型数据结构操作示例
 * @date 2018/5/28 12:47
 */
public class ListTest {

    public static void main(String[] args) {
        Jedis jedis = null;
        try {
            // 获取jedis连接
            jedis = JedisUtil.getJedis(args[0], Integer.valueOf(args[1]), args[2]);
            // 测试map数据结构
            listTest(jedis);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // 释放jedis连接
            JedisUtil.close(jedis);
        }
    }

    private static void listTest(Jedis jedis) {

        String LIST_KEY = "listKey";
        String LIST_VALUE_ONE = "listValue01";
        String LIST_VALUE_TWO = "listValue02";
        String LIST_VALUE_THREE = "listValue03";
        String LIST_VALUE_FOUR = "listValue02";
        int REMOVE_COUNT = 1;
        int START_INDEX = 0;
        int END_INDEX = -1;

        jedis.del(LIST_KEY);

        // 将多个元素插入到列表的尾部
        Long rpushResult = jedis.rpush(LIST_KEY, LIST_VALUE_ONE, LIST_VALUE_TWO, LIST_VALUE_THREE, LIST_VALUE_FOUR);
        System.out.println("rpush(" + LIST_KEY + "," + LIST_VALUE_ONE + ","
                + LIST_VALUE_TWO + "," + LIST_VALUE_THREE  + "," + LIST_VALUE_FOUR +
                ")" + " return : " + rpushResult);

        // 从列表中获取指定范围的子集
        List<String> lrangeResult = jedis.lrange(LIST_KEY, START_INDEX, END_INDEX);
        System.out.println("lrange(" + LIST_KEY + ","+ START_INDEX + "," + END_INDEX +")" + " return : " + lrangeResult);

        // 返回list的元素个数
        Long llenResult = jedis.llen(LIST_KEY);
        System.out.println("llen(" + LIST_KEY + ")" + " return : " + llenResult);

        // 从列表头部移除并返回list的第一个元素
        String lpopResult = jedis.lpop(LIST_KEY);
        System.out.println("lpop(" + LIST_KEY + ")" + " return : " + lpopResult);

        // 从头部开始找，删除n个值
        Long lremResult = jedis.lrem(LIST_KEY, REMOVE_COUNT, LIST_VALUE_TWO);
        System.out.println("lrem(" + LIST_KEY + "," + REMOVE_COUNT + "," + LIST_VALUE_TWO +")"
                + " return : " + lremResult);

        // 从列表中获取指定范围的子集
        lrangeResult = jedis.lrange(LIST_KEY, START_INDEX, END_INDEX);
        System.out.println("lrange(" + LIST_KEY + ","+ START_INDEX + "," + END_INDEX +")" + " return : " + lrangeResult);

        jedis.del(LIST_KEY);
    }
}
/*
程序输出：
rpush(listKey,listValue01,listValue02,listValue03,listValue02) return : 4
lrange(listKey,0,-1) return : [listValue01, listValue02, listValue03, listValue02]
llen(listKey) return : 4
lpop(listKey) return : listValue01
lrem(listKey,1,listValue02) return : 1
lrange(listKey,0,-1) return : [listValue03, listValue02]
 */
