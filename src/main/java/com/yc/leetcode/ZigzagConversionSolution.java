/* Copyright © 2020 Yuech and/or its affiliates. All rights reserved. */
package com.yc.leetcode;

/**
 * Z字形变换
 * zigzag-conversion
 * https://leetcode-cn.com/problems/zigzag-conversion/
 *
 * @author Yue Chang
 * @version 1.0
 * @date 2021-02-17 10:24 下午
 */
public class ZigzagConversionSolution {

    public static void main(String[] args) {

        ZigzagConversionSolution instance = new ZigzagConversionSolution();
        String result = instance.convert("ABA", 1);
        System.out.println(result);
        System.out.println();

        result = instance.convert("PAYPALISHIRING", 3);
        System.out.println(result);
        System.out.println();

    }

    /**
     * Z字形变换
     *
     * @param str 字符串
     * @param numRows 行数
     * @return 变形后的
     */
    public String convert(String str, int numRows) {

        // 如果行数为1，直接返回
        if (1 == numRows) {
            return str;
        }

        // 获得中间元素个数，即中间对角线的元素个数
        int middleCount = Math.max(numRows - 2, 0);
        // 每列元素数，可以根据"Z"字形的"7"这一段来统计
        // "7"这一段的，总元素数为：numRows + middleCount，总列数：middleCount + 1，两数相除得到每列元素数为2，具体计算过程如下
        // (numRows + middleCount) / (middleCount + 1) = (numRows + numRows - 2) / (numRows - 2 + 1) = 2
        int columnCount = str.length() / 2 + 1;
        String[][] arr = new String[numRows][columnCount];

        setArrValue(arr, 0, str, numRows, middleCount);

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < numRows; i++) {
            for (int j = 0; j < columnCount; j++) {
                if (arr[i][j] != null) {
                    sb.append(arr[i][j]);
                }
            }
        }
        return sb.toString();
    }

    /**
     * 把字符串维持"7"型设置到二维数组中
     *
     * @param arr 二维数组
     * @param columnIndex 列序号
     * @param str 字符串
     * @param numRows 行数/竖线位置每元素数量
     * @param middleCount 斜线位置元素数量
     *
     */
    void setArrValue(String[][] arr, int columnIndex, String str, int numRows, int middleCount) {

        int length = str.length();
        if (length < (numRows + middleCount)) {

            int charIndex = 0;
            // 迭代竖线位置剩余元素
            for (int i = 0; i < numRows && i < str.length(); i++) {
                arr[i][columnIndex] = str.charAt(charIndex++) + "";
            }
            int tempNumRows = numRows - 2;
            int tempColumnIndex = columnIndex + 1;
            // 迭代斜线位置剩余元素
            for (int j = 0; j < middleCount && j < (str.length() - numRows); j++) {
                arr[tempNumRows--][tempColumnIndex++] = str.charAt(charIndex++) + "";
            }
        } else {
            String tempStr = str.substring(0, numRows + middleCount);
            int charIndex = 0;
            // 迭代竖线位置元素
            for (int i = 0; i < numRows; i++) {
                arr[i][columnIndex] = tempStr.charAt(charIndex++) + "";
            }

            int tempNumRows = numRows - 2;
            int tempColumnIndex = columnIndex + 1;
            // 迭代斜线位置元素
            for (int j = 0; j < middleCount; j++) {
                arr[tempNumRows--][tempColumnIndex++] = tempStr.charAt(charIndex++) + "";
            }
            str = str.substring(numRows + middleCount);
            setArrValue(arr, tempColumnIndex, str, numRows, middleCount);
        }
    }

}
