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
* @ClassName: CasCounter
* @Description: 基于CAS实现的非租塞计数器
* @author Yue Chang 
* @date 2018年1月13日 下午12:03:58 
* @since 1.0
*/
public class CasCounter {

	private SimulatedCAS value;
	
	public int getValue() {
		return value.get();
	}
	
	public int increment() {
		
		int v;
		do {
			v = value.get();
		}
		while(v != value.compareAndSwap(v, v + 1));
		return v + 1;
	}
}
