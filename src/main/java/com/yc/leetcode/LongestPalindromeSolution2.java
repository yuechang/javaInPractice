/* Copyright © 2020 Yuech and/or its affiliates. All rights reserved. */
package com.yc.leetcode;

/**
 * 最长回文子串（动态规划方式）
 * longest-palindromic-substring
 * https://leetcode-cn.com/problems/longest-palindromic-substring/
 *
 * @author Yue Chang
 * @version 1.0
 * @date 2021-02-16 10:54 上午
 */
public class LongestPalindromeSolution2 {

    public static void main(String[] args) {

        LongestPalindromeSolution2 instance = new LongestPalindromeSolution2();
        long start = System.currentTimeMillis();


        String str1 = "ccc";
        System.out.println(instance.longestPalindrome(str1));
        System.out.println("cost " + (System.currentTimeMillis() - start) + "ms");

/*

        String str2 = "babad";
        System.out.println(instance.longestPalindrome(str2));
        System.out.println("cost " + (System.currentTimeMillis() - start) + "ms");
*/

    }

    /**
     * 动态规范方法解最长回文子串
     *
     * @param str 字符串
     * @return 最长回文子串
     */
    public String longestPalindrome(String str) {

        if (null == str || "".equals(str) || str.length() == 1) {
            return str;
        }
        int length = str.length();
        boolean[][] dp = new boolean[length][length];

        String palindrome = "";
        // 初始化dp数组的对角线以及平行对角线的上一行的初始值，
        for (int i = 0; i < length; i++) {
            dp[i][i] = true;
            if (palindrome.length() < 2) {
                // 记录长度为1的回文串
                palindrome = str.substring(i, i + 1);
            }
            if (i + 1 < length && str.charAt(i) == str.charAt(i + 1)) {
                dp[i][i + 1] = true;
                // 记录长度为2的回文串
                palindrome = str.substring(i, i + 1 + 1);
            }
        }

        // 从长度为3的字符串开始迭代，往平行于对角线往上的方向迭代
        // 子串长度
        for (int l = 3; l < length + 1; l++) {
            // 枚举子串的起始点
            for (int startIndex = 0; startIndex < length - l + 1; startIndex++) {
                // 子串终点
                int endIndex = startIndex + l - 1;
                // 例如：长度为4时，startIndex = 0，endIndex = 3，此子串是否为回文串，dp[0][3]由两端的字符是否相等 && 斜下角的元素dp[1][2]的值是否为true决定是否为回文串
                if (str.charAt(startIndex) == str.charAt(endIndex) && dp[startIndex + 1][endIndex - 1] ) {
                    dp[startIndex][endIndex] = true;
                    palindrome = str.substring(startIndex, endIndex + 1);
                }
            }
        }
        return palindrome;
    }
}
