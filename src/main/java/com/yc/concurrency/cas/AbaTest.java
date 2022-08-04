/* Copyright © 2020 Yuech and/or its affiliates. All rights reserved. */
package com.yc.concurrency.cas;

import java.util.concurrent.atomic.AtomicStampedReference;

/**
 * ABA问题测试代码
 *
 * @author Yue Chang
 * @version 1.0
 * @date 2020-12-23 下午10:19
 */
public class AbaTest {

    public static final int expectedReference = 103;
    public static final int newReference = 103;

    private static AtomicStampedReference<Integer> atomicStampedReference
            = new AtomicStampedReference<>(expectedReference,0);

    public static void main(String[] args) {

        boolean flag = atomicStampedReference.compareAndSet(expectedReference, newReference,
                atomicStampedReference.getStamp(), atomicStampedReference.getStamp() + 1);

        System.out.println(flag + ", " + atomicStampedReference.getReference() + ", " + atomicStampedReference.getStamp());

        flag = atomicStampedReference.compareAndSet(expectedReference, newReference,
                atomicStampedReference.getStamp(), atomicStampedReference.getStamp() + 1);

        System.out.println(flag + ", " + atomicStampedReference.getReference() + ", " + atomicStampedReference.getStamp());


    }
}
