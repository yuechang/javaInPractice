/*!
 * Copyright(c) 2017 Yue Chang
 * MIT Licensed
 */
package com.yc.jvm.classLoad;

import java.io.IOException;
import java.io.InputStream;

/**
 * @author Yue Chang
 * @ClassName: ClassLoadTest
 * @Description: 类加载器与instanceof关键字示例
 * @date 2018/5/19 11:19
 */
public class ClassLoadTest {

    public static void main(String[] args) throws ClassNotFoundException, IllegalAccessException, InstantiationException {

        ClassLoader myLoader = new ClassLoader() {
            @Override
            public Class<?> loadClass(String name) throws ClassNotFoundException {
                try {
                    String fileName = name.substring(name.lastIndexOf(".") + 1) + ".class";
                    InputStream is = getClass().getResourceAsStream(fileName);
                    if (is == null) {
                        return super.loadClass(name);
                    }
                    byte[] bytes = new byte[is.available()];
                    is.read(bytes);
                    return defineClass(name, bytes, 0, bytes.length);
                } catch (IOException e) {
                    throw new ClassNotFoundException(name);
                }
            }
        };

        Object obj = myLoader.loadClass("com.yc.jvm.classLoad.ClassLoadTest").newInstance();
        System.out.println(obj.getClass());

        System.out.println(obj instanceof com.yc.jvm.classLoad.ClassLoadTest);
    }
}
/*
环境信息：
windows 7
jdk1.8.0_101

程序分析：
通过构造了一个简单的类加载器，它可以加载与自己在同一路径下的Class文件。我们使用这个类加载器去加载一个名为
"com.yc.jvm.classLoad.ClassLoadTest"的类，并实例化了这个类的对象。
两行输出结果中，从第一句可以看出，这个对象确实是类com.yc.jvm.classLoad.ClassLoadTest实例化出来的对象，
但从第二句可以发现，这个对象与类com.yc.jvm.classLoad.ClassLoadTest做所属类型检查的时候却返回了false，
这是因为虚拟机中存在了两个ClassLoaderTest类，一个是由系统应用程序加载器加载的，另外一个是由我们自定义的
类加载器加载的，虽然都来来自同一个Class文件，单依然是两个独立的类，做对象所属类型检查时结果返回false。

程序输出：
class com.yc.jvm.classLoad.ClassLoadTest
false
 */