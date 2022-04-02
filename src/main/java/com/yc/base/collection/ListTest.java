package com.yc.base.collection;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

/**
 * List sublist测试
 *
 * @author Yue Chang
 * @date 2018/9/12 16:12
 */
public class ListTest {

    public static int LIST_COUNT = 10;

    public static void main(String[] args) {
        testSubListToArrayList();
        testSubList();
        testListToArray();
        testArrayAsList();
    }

    public static void testSubListToArrayList() {

        try {

            List<String> list = new ArrayList<>();
            for (int i = 0; i < LIST_COUNT; i++) {
                list.add(i + "");
            }

            ArrayList<String> subList = (ArrayList<String>)list.subList(1, 5);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void testSubList() {

        try {

            List<String> list = new ArrayList<>();
            for (int i = 0; i < LIST_COUNT; i++) {
                list.add(i + "");
            }

            List<String> subList = list.subList(1, 5);

            list.add("10");
            list.add("11");

            Iterator<String> subListIterator = subList.iterator();
            while (subListIterator.hasNext()) {
                String temp = subListIterator.next();
                System.out.println(temp);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static void testListToArray() {

        try {

            List<String> list = new ArrayList<>();
            for (int i = 0; i < LIST_COUNT; i++) {
                list.add(i + "");
            }
            String[] arr = new String[list.size()];
            arr = list.toArray(arr);
            System.out.println("arr:" + arr);

            Object[] tempArr = list.toArray();
            System.out.println("tempArr:" + tempArr);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static void testArrayAsList() {

        try {

            String[] arr = {"hello", ",", "world"};
            List<String> list = Arrays.asList(arr);

            list.add("!");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
/*
Java开发书册List集合规则：
2. 【强制】 ArrayList的subList结果不可强转成ArrayList，否则会抛出ClassCastException异常，
即java.util.RandomAccessSubList cannot be cast to java.util.ArrayList.
说明：subList 返回的是 ArrayList 的内部类 SubList，并不是 ArrayList ，
而是 ArrayList 的一个视图，对于SubList子列表的所有操作最终会反映到原列表上。
3. 【强制】在subList场景中，高度注意对原集合元素个数的修改，会导致子列表的遍历、增加、删除均会产生ConcurrentModificationException 异常。
4. 【强制】使用集合转数组的方法，必须使用集合的toArray(T[] array)，传入的是类型完全一样的数组，大小就是list.size()。
说明：使用toArray带参方法，入参分配的数组空间不够大时，toArray方法内部将重新分配内存空间，并返回新数组地址；
如果数组元素大于实际所需，下标为[ list.size() ]的数组元素将被置为null，其它数组元素保持原值，因此最好将方法入参数组大小定义与集合元素个数一致。
5. 【强制】使用工具类Arrays.asList()把数组转换成集合时，不能使用其修改集合相关的方法，它的add/remove/clear方法会抛出UnsupportedOperationException异常。
说明：asList的返回对象是一个Arrays内部类，并没有实现集合的修改方法。Arrays.asList体现的是适配器模式，只是转换接口，后台的数据仍是数组

程序结果：
java.lang.ClassCastException: java.util.ArrayList$SubList cannot be cast to java.util.ArrayList
	at com.yc.base.collection.ListSubListTest.testSubListToArrayList(ListSubListTest.java:32)
	at com.yc.base.collection.ListSubListTest.main(ListSubListTest.java:18)
java.util.ConcurrentModificationException
	at java.util.ArrayList$SubList.checkForComodification(ArrayList.java:1231)
	at java.util.ArrayList$SubList.listIterator(ArrayList.java:1091)
	at java.util.AbstractList.listIterator(AbstractList.java:299)
	at java.util.ArrayList$SubList.iterator(ArrayList.java:1087)
	at com.yc.base.collection.ListSubListTest.testSubList(ListSubListTest.java:53)
	at com.yc.base.collection.ListSubListTest.main(ListSubListTest.java:19)
arr:[Ljava.lang.String;@16b3fc9e
tempArr:[Ljava.lang.Object;@e2d56bf
java.lang.UnsupportedOperationException
	at java.util.AbstractList.add(AbstractList.java:148)
	at java.util.AbstractList.add(AbstractList.java:108)
	at com.yc.base.collection.ListTest.testArrayAsList(ListTest.java:93)
	at com.yc.base.collection.ListTest.main(ListTest.java:22)

 */