/* Copyright © 2020 Yuech and/or its affiliates. All rights reserved. */
package com.yc.leetcode;

/**
 * 整数转罗马数字
 * integer-to-roman
 * https://leetcode-cn.com/problems/integer-to-roman/
 *
 * 罗马数字包含以下七种字符： I， V， X， L，C，D 和 M。
 *
 * 字符          数值
 * I             1
 * V             5
 * X             10
 * L             50
 * C             100
 * D             500
 * M             1000
 * 例如， 罗马数字 2 写做 II ，即为两个并列的 1。12 写做 XII ，即为 X + II 。 27 写做  XXVII, 即为 XX + V + II 。
 *
 * 通常情况下，罗马数字中小的数字在大的数字的右边。但也存在特例，例如 4 不写做 IIII，而是 IV。数字 1 在数字 5 的左边，所表示的数等于大数 5 减小数 1 得到的数值 4 。同样地，数字 9 表示为 IX。这个特殊的规则只适用于以下六种情况：
 *
 * I 可以放在 V (5) 和 X (10) 的左边，来表示 4 和 9。
 * X 可以放在 L (50) 和 C (100) 的左边，来表示 40 和 90。
 * C 可以放在 D (500) 和 M (1000) 的左边，来表示 400 和 900。
 * 给定一个整数，将其转为罗马数字。输入确保在 1 到 3999 的范围内。
 *
 * @author Yue Chang
 * @version 1.0
 * @date 2021-02-16 9:45 下午
 */
public class IntegerToRomanSolution {

    public static void main(String[] args) {

        IntegerToRomanSolution instance = new IntegerToRomanSolution();
        String result = instance.intToRoman(1994);
        System.out.println(result);

        result = instance.intToRoman(3);
        System.out.println(result);

        result = instance.intToRoman(58);
        System.out.println(result);


    }

    /**
     * 1
     */
    public static final String I = "I";

    /**
     * 5
     */
    public static final String V = "V";

    /**
     * 10
     */
    public static final String X = "X";


    /**
     * 50
     */
    public static final String L = "L";

    /**
     * 100
     */
    public static final String C = "C";

    /**
     * 500
     */
    public static final String D = "D";

    /**
     * 1000
     */
    public static final String M = "M";


    public String intToRoman(int num) {

        StringBuilder stringBuilder = new StringBuilder();
        String str = String.valueOf(num);
        int length = str.length();
        int carryIndex = 1;
        for (int i = length - 1; i >= 0; i--) {
            String temp = str.charAt(i) + "";
            int intValue = Integer.parseInt(temp);
            String roman = getRoman(carryIndex, intValue);
            // System.out.println("temp:" + temp + ",roman:" + roman + ",carryIndex:" + carryIndex);
            stringBuilder.insert(0, roman);
            carryIndex *= 10;
        }
        return stringBuilder.toString();
    }

    private String getRoman(int carryIndex, int intValue) {
        if (1 == carryIndex) {
            return doGetRoman(I, V, X, intValue);
        } else if (10 == carryIndex) {
            return doGetRoman(X, L, C, intValue);
        } else if (100 == carryIndex) {
            return doGetRoman(C, D, M, intValue);
        } else if (1000 == carryIndex) {
            return doGetRoman(M, "", "", intValue);
        }
        return "";
    }

    private String doGetRoman(String one, String five, String ten, int intValue) {

        if (0 == intValue) {
            return "";
        } else if (intValue < 4 ) {
            if (1 == intValue) {
                return one;
            } else if (2 == intValue) {
                return one + one;
            } else {
                return one + one + one;
            }
        } else if (4 == intValue) {
            return one + five;
        } else if (5 == intValue) {
            return five;
        } else if (intValue < 9) {
            if (6 == intValue) {
                return five + one;
            } else if (7 == intValue) {
                return five + one + one;
            } else {
                return five + one + one + one;
            }
        } else if (9 == intValue) {
            return one + ten;
        }
        return "";
    }


}
