/* Copyright © 2020 Yuech and/or its affiliates. All rights reserved. */
package com.yc.leetcode;

/**
 * 整数反转
 * reverse-integer
 * https://leetcode-cn.com/problems/reverse-integer/
 *
 * @author Yue Chang
 * @version 1.0
 * @date 2021-02-18 8:46 下午
 */
public class ReverseIntegerSolution {

    public static void main(String[] args) {

        ReverseIntegerSolution instance = new ReverseIntegerSolution();
        int result = instance.reverse(-123);
        System.out.println(result);

        result = instance.reverse(-2147483648);
        System.out.println(result);

    }

    /**
     * 整形反转
     *
     * @param x 整形数字
     * @return 反转整形数字
     */
    public int reverse(int x) {

        if (0 == x) {
            return x;
        }

        // 负数标志
        boolean negativeNumberFlag = false;

        int result = 0;
        StringBuilder resultStr = new StringBuilder();

        String tempX = String.valueOf(x);
        // 处理-2147483648绝对值仍然是-2147483648的情况，因为溢出导致
        if (tempX.startsWith("-")) {
            negativeNumberFlag = true;
            tempX = tempX.substring(tempX.indexOf("-") + 1);
        }
        for (int i = tempX.length(); i > 0; i--) {
            char c = tempX.charAt(i - 1);
            resultStr.append(c);
        }

        long longResult = Long.parseLong(resultStr.toString());
        if (Integer.MIN_VALUE <= longResult && longResult <= Integer.MAX_VALUE) {
            result = Integer.parseInt(resultStr.toString());
        }

        if (negativeNumberFlag) {
            result = -result;
        }
        return result;
    }

}

