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
package com.yc.concurrency.countdownlatch;

import org.junit.Test;
import java.util.concurrent.CountDownLatch;


/**
 * CountDownLatch测试类
 *
 * @author Yue Chang
 * @date 2018年1月16日 上午11:31:26
 * @since 1.0
 */
public class CountDownLatchTest {

	@Test
	public void test() throws InterruptedException {

		CountDownLatch latch = new CountDownLatch(1);

		MyThread myThread = new MyThread(latch);
		Thread t1 = new Thread(myThread);
		t1.setName("t1");

		Thread t2 = new Thread(myThread);
		t2.setName("t2");

		t1.start();
		t2.start();

		// junit主线程执行完之后，不会再去管子线程的执行情况，如果没有join(),那么大多数情况下子线程没法执行完
		t1.join();
	}

	public static void main(String[] args) {
		CountDownLatch latch = new CountDownLatch(1);

		MyThread myThread = new MyThread(latch);
		Thread t1 = new Thread(myThread);
		t1.setName("t1");

		Thread t2 = new Thread(myThread);
		t2.setName("t2");

		t1.start();
		t2.start();

	}
}

class MyThread implements Runnable{

	private CountDownLatch latch;
	private volatile int number;

	/**
	 * @param latch
	 */
	public MyThread(CountDownLatch latch) {
		this.latch = latch;
	}

	public MyThread() {
	}

	public CountDownLatch getLatch() {
		return latch;
	}

	public void setLatch(CountDownLatch latch) {
		this.latch = latch;
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	@Override
	public void run() {

		String threadName = Thread.currentThread().getName().trim();
		// t2线程
		if ("t2".equals(threadName)) {
			try {
				Thread.sleep(5000);
				System.out.println("线程0：" + threadName);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			this.setNumber(1);
			latch.countDown(); // 计数减1
		}
		// t1线程
		else if ("t1".equals(threadName)) {
			try {
				System.out.println("线程1：" + threadName);
				latch.await(); // 阻塞等待计数为0
				System.out.println("线程1：" + threadName);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			System.out.println("num = " + this.getNumber());
		}
	}
}