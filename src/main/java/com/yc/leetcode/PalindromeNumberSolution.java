/* Copyright © 2020 Yuech and/or its affiliates. All rights reserved. */
package com.yc.leetcode;

/**
 * 回文数
 * palindrome-number
 * https://leetcode-cn.com/problems/palindrome-number/
 *
 * @author Yue Chang
 * @version 1.0
 * @date 2021-02-20 11:35 下午
 */
public class PalindromeNumberSolution {

    public static void main(String[] args) {

        PalindromeNumberSolution instance = new PalindromeNumberSolution();

        boolean palindrome = instance.isPalindrome(121);
        System.out.println(palindrome);

        palindrome = instance.isPalindrome(-121);
        System.out.println(palindrome);

        palindrome = instance.isPalindrome(10);
        System.out.println(palindrome);

    }


    /**
     * 判断数字是否为回文

     *
     * @param x 数字
     * @return true：是，false：否
     */
    public boolean isPalindrome(int x) {

        if (x < 0) {
            return false;
        }
        String str = String.valueOf(x);
        int length = str.length();
        for (int i = 0; i < length / 2; i++) {
            if (str.charAt(i) != str.charAt(length - i - 1)) {
                return false;
            }
        }
        return true;
    }
}
