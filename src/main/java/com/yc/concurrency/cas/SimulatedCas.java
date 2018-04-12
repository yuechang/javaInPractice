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
package com.yc.concurrency.cas;

/**
* @ClassName: SimulatedCas
* @Description: 模拟CAS操作
* @author Yue Chang 
* @date 2018年1月13日 上午11:58:19 
* @since 1.0
*/
public class SimulatedCas {

	private int value;
	
	public synchronized int get() {
		return value;
	}
	
	public synchronized int compareAndSwap(int expectedValue,int newValue) {
		
		// 将内存中的值赋值给oldValue，作为返回结果
		int oldValue = value;
		// 仅仅当内存中的值与期望的值相等时，才进行赋值
		if(oldValue == expectedValue) {
			value = newValue;
		}
		return oldValue;
	}
	
	public synchronized boolean compareAndSet(int expectedValue,int newValue) {
		// 比较期望值与compareAndSwap()方法返回值，结果相同时，才表示修改成功
		return (expectedValue == compareAndSwap(expectedValue,newValue));
	}
	
}
