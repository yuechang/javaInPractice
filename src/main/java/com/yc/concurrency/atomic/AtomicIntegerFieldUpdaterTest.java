/*!
 * Copyright(c) 2017 Yue Chang
 * MIT Licensed
 */
package com.yc.concurrency.atomic;

import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;

/**
 * 原子更新整形的字段的更新器
 *
 * @author Yue Chang
 * @ClassName: AtomicIntegerFieldUpdaterTest
 * @date 2018/5/5 17:33
 */
public class AtomicIntegerFieldUpdaterTest {

    /**
     * 创建原子更新器，并设置需要更新的对象类和对象的属性
     */
    private static AtomicIntegerFieldUpdater<User> a =
            AtomicIntegerFieldUpdater.newUpdater(User.class, "old");

    public static void main(String[] args) {
        // 设置柯南的年龄是10岁
        User conan = new User("Conan", 10);
        // 柯南长了一岁，但是仍然会输出旧的年龄
        System.out.println(a.getAndIncrement(conan));
        // 输出柯南现在的年龄
        System.out.println(a.get(conan));
    }


    public static class User {
        private String name;
        public volatile int old;

        public User(String name, int old) {
            this.name = name;
            this.old = old;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

}
/*
输出结果：
10
11

使用原子地更新字段类需要两步，
第一步，因为原子更新字段类都是抽象类，每次使用的时候必须使用静态方法newUpdater()创建一个更新器，并且需要设置想要更新的类和属性
第二步，更新类的字段（属性）必须使用public volatile修饰符。

不声明为public将会报错，报错信息如下：
java.lang.ExceptionInInitializerError
Caused by: java.lang.RuntimeException: java.lang.IllegalAccessException: Class com.yc.concurrency.atomic.AtomicIntegerFieldUpdaterTest can not access a member of class com.yc.concurrency.atomic.AtomicIntegerFieldUpdaterTest$User with modifiers "private volatile"
	at java.util.concurrent.atomic.AtomicIntegerFieldUpdater$AtomicIntegerFieldUpdaterImpl.<init>(AtomicIntegerFieldUpdater.java:404)
	at java.util.concurrent.atomic.AtomicIntegerFieldUpdater.newUpdater(AtomicIntegerFieldUpdater.java:87)
	at com.yc.concurrency.atomic.AtomicIntegerFieldUpdaterTest.<clinit>(AtomicIntegerFieldUpdaterTest.java:19)
Caused by: java.lang.IllegalAccessException: Class com.yc.concurrency.atomic.AtomicIntegerFieldUpdaterTest can not access a member of class com.yc.concurrency.atomic.AtomicIntegerFieldUpdaterTest$User with modifiers "private volatile"
	at sun.reflect.Reflection.ensureMemberAccess(Reflection.java:102)
	at sun.reflect.misc.ReflectUtil.ensureMemberAccess(ReflectUtil.java:103)
	at java.util.concurrent.atomic.AtomicIntegerFieldUpdater$AtomicIntegerFieldUpdaterImpl.<init>(AtomicIntegerFieldUpdater.java:393)
	... 2 more
Exception in thread "main"
 */