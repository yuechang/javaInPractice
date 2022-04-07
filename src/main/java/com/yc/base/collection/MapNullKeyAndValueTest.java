/*!
 * Copyright(c) 2017 Yue Chang
 * MIT Licensed
 */
package com.yc.base.collection;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.TreeMap;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 各个MAP键值是否为空测试
 *
 * @author Yue Chang
 * @date 2018/9/7 16:12
 */
public class MapNullKeyAndValueTest {
	
	public static void main(String[] args) {
		hashtableTest();
		concurrentHashMapTest();
		treeMapTest();
		hashMapTest();
	}
	

	/**
	 * Hashtable测试方法
	 */
	public static void hashtableTest() {

		try {
			Hashtable<String, String> hashtable = new Hashtable<>();
			hashtable.put(null, null);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * ConcurrentHashMap测试方法
	 */
	public static void concurrentHashMapTest() {

		try {
			ConcurrentHashMap<String, String> concurrentHashMap = new ConcurrentHashMap<>();
			concurrentHashMap.put(null, null);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * TreeMap测试方法
	 */
	public static void treeMapTest() {

		try {
			TreeMap<String, String> treeMap = new TreeMap<>();
			treeMap.put("key", null);
			treeMap.put(null, null);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


	/**
	 * HashMap测试方法
	 */
	public static void hashMapTest() {

		try {
			HashMap<String, String> hashMap = new HashMap<>();
			hashMap.put(null, null);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}

/*
结论：
 - Hashtable/ConcurrentHashMap的键值均不能为null;
 - TreeMap的键不能为null，值可以为null;
 - HashMap的键值均可以为null;

程序打印：
java.lang.NullPointerException
	at java.util.Hashtable.put(Hashtable.java:459)
	at com.yc.concurrency.map.MapNullKeyAndValueTest.hashtableTest(MapNullKeyAndValueTest.java:35)
	at com.yc.concurrency.map.MapNullKeyAndValueTest.main(MapNullKeyAndValueTest.java:21)
java.lang.NullPointerException
	at java.util.concurrent.ConcurrentHashMap.putVal(ConcurrentHashMap.java:1011)
	at java.util.concurrent.ConcurrentHashMap.put(ConcurrentHashMap.java:1006)
	at com.yc.concurrency.map.MapNullKeyAndValueTest.concurrentHashMapTest(MapNullKeyAndValueTest.java:48)
	at com.yc.concurrency.map.MapNullKeyAndValueTest.main(MapNullKeyAndValueTest.java:22)
java.lang.NullPointerException
	at java.util.TreeMap.put(TreeMap.java:563)
	at com.yc.concurrency.map.MapNullKeyAndValueTest.treeMapTest(MapNullKeyAndValueTest.java:62)
	at com.yc.concurrency.map.MapNullKeyAndValueTest.main(MapNullKeyAndValueTest.java:23)
 */