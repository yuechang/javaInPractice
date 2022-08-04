/* Copyright(c) 2022 Yue Chang and/or its affiliates. All right reserved. */
package com.yc.redis.dataStructures;

import com.yc.redis.base.JedisUtil;
import redis.clients.jedis.Jedis;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author Yue Chang
 * @ClassName: MapTest
 * @Description: Map类型数据结构操作示例
 * @date 2018/5/27 16:11
 */
public class MapTest {

    public static void main(String[] args) {

        Jedis jedis = null;
        try {
            // 获取jedis连接
            jedis = JedisUtil.getJedis(args[0], Integer.valueOf(args[1]), args[2]);
            // 测试map数据结构
            mapTest(jedis);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // 释放jedis连接
            JedisUtil.close(jedis);
        }
    }

    private static void mapTest(Jedis jedis) {

        String MAP_KEY = "mapKey";
        String ID = "id";
        String NAME = "name";
        String AGE = "age";
        String PHONE_NUMBER = "phoneNumber";
        String NEW_NAME_VALUE = "M";
        String PHONE_NUMER_VALUE = "12345";
        int INCREASE_AGE = 1;


        Map<String,String> map = new HashMap<>();
        map.put(ID, "5");
        map.put(NAME, "k");
        map.put(AGE, "26");


        // 为key设置值，值类型为map对象
        String hmsetResult = jedis.hmset(MAP_KEY, map);
        System.out.println("hmset(" + MAP_KEY + " , " + map + ")" + " return : " + hmsetResult);

        // 返回key值的类型，可能有none，string，hash，set，list，zset
        String typeResult = jedis.type(MAP_KEY);
        System.out.println("type(" + MAP_KEY + ")" + " return : " + typeResult);

        // 获取key对应的map中所有的keys
        Set<String> hkeysResult = jedis.hkeys(MAP_KEY);
        System.out.println("hkeys(" + MAP_KEY + ")" + " return : " + hkeysResult);

        // 获取key对应map中所有的values
        List<String> hvalsResult = jedis.hvals(MAP_KEY);
        System.out.println("hvals(" + MAP_KEY + ")" + " return : " + hvalsResult);

        // 获取key对应map中name和age两个属性的值，可获得多个属性的值，为可变参数
        List<String> hmgetResult = jedis.hmget(MAP_KEY, NAME, AGE);
        System.out.println("hmget(" + MAP_KEY + "," + NAME + "," + AGE + ")" + " return : " + hmgetResult);

        // 判断key对应map中某个属性值是否存在
        Boolean hexistsResult = jedis.hexists(MAP_KEY, NAME);
        System.out.println("hexists(" + MAP_KEY + "," + NAME + ")" + " return : " + hexistsResult);
        hexistsResult = jedis.hexists(MAP_KEY, PHONE_NUMBER);
        System.out.println("hexists(" + MAP_KEY + "," + PHONE_NUMBER + ")" + " return : " + hexistsResult);

        // 获取key对应map中name属性的值，hmget()为获取多个属性的值，hget()获取单个属性的值
        String hgetResult = jedis.hget(MAP_KEY, NAME);
        System.out.println("hget(" + MAP_KEY + "," + NAME  + ")" + " return : " + hgetResult);
        // 设置key对应map中name的属性值
        Long hsetResult = jedis.hset(MAP_KEY, NAME, NEW_NAME_VALUE);
        System.out.println("hset(" + MAP_KEY + "," + NAME + "," + NEW_NAME_VALUE + ")" + " return : " + hsetResult);
        // 获取key对应map中name属性的值，hmget()为获取多个属性的值，hget()获取单个属性的值
        hgetResult = jedis.hget(MAP_KEY, NAME);
        System.out.println("hget(" + MAP_KEY + "," + NAME  + ")" + " return : " + hgetResult);

        // 获取key对应的map数据
        Map<String, String> hgetAllMap = jedis.hgetAll(MAP_KEY);
        System.out.println("hgetAll(" + MAP_KEY + ")" + " return : " + hgetAllMap);

        // 删除key对应map中的name属性
        Long hdelResult = jedis.hdel(MAP_KEY, NAME);
        System.out.println("hdel(" + MAP_KEY + "," + NAME  + ")" + " return : " + hdelResult);
        // 获取key对应map中name属性的值
        hgetResult = jedis.hget(MAP_KEY, NAME);
        System.out.println("hget(" + MAP_KEY + "," + NAME  + ")" + " return : " + hgetResult);

        // 将key对应map中age属性增长1
        Long hincrByResult = jedis.hincrBy(MAP_KEY, AGE, INCREASE_AGE);
        System.out.println("hincrBy(" + MAP_KEY + "," + AGE + "," + INCREASE_AGE + ")" + " return : " + hincrByResult);

        // 计算key对应map中属性个数
        Long hlenResult = jedis.hlen(MAP_KEY);
        System.out.println("hlen(" + MAP_KEY + ")" + " return : " + hlenResult);

        // 设置key对应map中phoneNumber属性的值，如果对应的值存在则回滚，1：设置成功，0：设置失败
        Long hsetnxResult = jedis.hsetnx(MAP_KEY, PHONE_NUMBER, PHONE_NUMER_VALUE);
        System.out.println("hsetnx(" + MAP_KEY + "," + PHONE_NUMBER + "," + PHONE_NUMER_VALUE + ")" + " return : " + hsetnxResult);
        hsetnxResult = jedis.hsetnx(MAP_KEY, PHONE_NUMBER, PHONE_NUMER_VALUE);
        System.out.println("hsetnx(" + MAP_KEY + "," + PHONE_NUMBER + "," + PHONE_NUMER_VALUE + ")" + " return : " + hsetnxResult);

        jedis.del(MAP_KEY);

    }
}
/*
程序输出：
hmset(mapKey , {name=k, id=5, age=26}) return : OK
type(mapKey) return : hash
hkeys(mapKey) return : [name, id, phoneNumber, age]
hvals(mapKey) return : [26, 5, 12345, k]
hmget(mapKey,name,age) return : [k, 26]
hexists(mapKey,name) return : true
hexists(mapKey,phoneNumber) return : true
hget(mapKey,name) return : k
hset(mapKey,name,M) return : 0
hget(mapKey,name) return : M
hgetAll(mapKey) return : {name=M, age=26, id=5, phoneNumber=12345}
hdel(mapKey,name) return : 1
hget(mapKey,name) return : null
hincrBy(mapKey,age,1) return : 27
hlen(mapKey) return : 3
hsetnx(mapKey,phoneNumber,12345) return : 0
hsetnx(mapKey,phoneNumber,12345) return : 0

 */
