/*
 * Copyright (c) 2016, 2025, HHLY and/or its affiliates. All rights reserved.
 * HHLY PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 */
package com.yc.concurrency.reorder;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

/**
* @ClassName: PossibleReordering
* @Description: 重排序例子
* @author Yue Chang 
* @date 2018年1月13日 下午4:33:38 
* @since 1.0
*/
public class PossibleReordering {

	int x = 0, y = 0;
	int a = 0, b = 0;
	
	static Map<String,String> map = new HashMap<String, String>();
	
	@Test
	public void test() throws InterruptedException {

		PossibleReordering possibleReordering = new PossibleReordering();
		for (int i = 0; i < 100000; i++) {
			possibleReordering.reOrder();
		}
		
		for (String key : map.keySet()) {
			System.out.println(key);
		}
	}

	/**
	 * @category 重排序测试
	 * @since Java并发编程实战
	 * @author Yue Chang 
	 * @date 2018年1月16日 上午10:16:40 
	 * @throws InterruptedException
	 */
	private void reOrder() throws InterruptedException {

		x = 0;
		y = 0;
		a = 0; 
		b = 0;
		
		Thread one = new Thread(new Runnable() {
			public void run() {
				a = 1;
				x = b;
			}
		});
		
		Thread other = new Thread(new Runnable() {
			public void run() {
				b = 1;
				y = a;
			}
		});
		
		other.start();
		one.start();
		
		one.join();
		other.join();
		
		String key = "(" + x + "," + y + ")";
		map.put(key, key);
		

	}
}
