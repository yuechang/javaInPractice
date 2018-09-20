/*!
 * Copyright(c) 2017 Yue Chang
 * MIT Licensed
 */
package com.yc.base.sort;

/**
 * @author Yue Chang
 * @ClassName: QuickSort
 * @Description: 快速排序
 * @date 2018/9/20 15:38
 */
public class QuickSort {

    public static void sort(int data[], int low, int hight){
        QuickSort qs = new QuickSort();

        qs.data = data;
        qs.sort(low, hight);
    }
    public int data[];
    private int partition(int sortArray[], int low, int high) {
        int key = sortArray[low];
        // 循环直到低位和高位值相等
        while (low < high) {
            while (low < high && sortArray[high] >= key) {
                high--;
            }
            // 当高位中出现了数值小于基准值时，把数组高位的值赋值给数组低位
            sortArray[low] = sortArray[high];
            while (low < high && sortArray[low] <= key) {
                low++;
            }
            // 当低位中出现了数值大于基准值时，把数组低位的值赋值给数组高位
            sortArray[high] = sortArray[low];
        }
        // 将基准值赋值给数据低位
        sortArray[low] = key;
        // display();
        // System.out.println("low:" + low);
        return low;
    }
    public void sort(int low, int high) {

        if (low < high) {
            int result = partition(data, low, high);
            sort(low, result - 1);
            sort(result + 1, high);
        }
    }
    public void display() {
        for (int i = 0; i < data.length; i++) {
            System.out.print(data[i]);
            System.out.print(" ");
        }
    }
}

