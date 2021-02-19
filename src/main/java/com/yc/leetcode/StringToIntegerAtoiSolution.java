/* Copyright © 2020 Yuech and/or its affiliates. All rights reserved. */
package com.yc.leetcode;

/**
 * 字符串转换整数 (atoi)
 * string-to-integer-atoi
 * https://leetcode-cn.com/problems/string-to-integer-atoi/
 *
 * @author Yue Chang
 * @version 1.0
 * @date 2021-02-19 9:29 下午
 */
public class StringToIntegerAtoiSolution {
    public static void main(String[] args) {

        StringToIntegerAtoiSolution instance = new StringToIntegerAtoiSolution();

        int result = instance.myAtoi("9223372036854775808");
        System.out.println(result);

        result = instance.myAtoi("-000000000000000000000000000000000000000000000000001");
        System.out.println(result);

        result = instance.myAtoi("-20000000000000000000");
        System.out.println(result);

        result = instance.myAtoi("4193 with words");
        System.out.println(result);

        result = instance.myAtoi("      -42");
        System.out.println(result);

        result = instance.myAtoi("-91283472332");
        System.out.println(result);

    }

    /**
     * 字符前缀
     */
    public static final char ATOI_PREFIX = ' ';

    /**
     * 加号
     */
    public static final char PLUS_SIGN = '+';

    /**
     * 减号
     */
    public static final char MINUS_SIGN = '-';

    /**
     * 开始字符0
     */
    public static final char NUMBER_START = '0';

    /**
     * 结束字符9
     */
    public static final char NUMBER_END = '9';

    /**
     * 字符串转换整数 (atoi)
     *
     * @param str 字符串
     * @return 整数结果
     */
    public int myAtoi(String str) {

        if (null == str || "".equals(str)) {
            return 0;
        }

        int index = 0;
        // 获得前导符合的位置索引
        for (int i = 0; i < str.length(); i++) {
            char c = str.charAt(i);
            // 如果为前导字符
            if (ATOI_PREFIX != c) {
                index = i;
                break;
            }
        }
        // 获得不包含前导字符的字符串
        String substring = str.substring(index);

        // 拼接后续字符串
        StringBuilder stringBuilder = new StringBuilder();
        boolean negativeFlag = false;
        for (int i = 0; i < substring.length(); i++) {

            char c = substring.charAt(i);
            if (0 == i) {
                if (MINUS_SIGN == c) {
                    negativeFlag = true;
                    continue;
                } else if (PLUS_SIGN == c) {
                    continue;
                }
            }
            if (NUMBER_START <= c && c <= NUMBER_END) {
                stringBuilder.append(c);
            } else {
                break;
            }
        }
        if ("".equals(stringBuilder.toString())) {
            return 0;
        }

        int notZeroStrIndex = 0;
        // 获得前导符合的位置索引
        for (int i = 0; i < stringBuilder.length(); i++) {
            char c = stringBuilder.charAt(i);
            // 如果为前导字符
            if (NUMBER_START != c) {
                notZeroStrIndex = i;
                break;
            }
        }
        // 获得不包含前导字符0的字符串
        String realStr = stringBuilder.substring(notZeroStrIndex);
        if ("".equals(realStr)) {
            return 0;
        }

        long l;
        // 如果大于long类型的最大长度19位
        if (realStr.length() >= 19) {
            l = Long.MAX_VALUE - 2;
        } else {
            l = Long.parseLong(realStr);
        }
        if (negativeFlag) {
            l = -l;
        }
        if (l > Integer.MAX_VALUE) {
            return Integer.MAX_VALUE;
        }
        if (l < Integer.MIN_VALUE) {
            return Integer.MIN_VALUE;
        }
        return (int) l;
    }

}
