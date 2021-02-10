/* Copyright © 2020 Yuech and/or its affiliates. All rights reserved. */
package com.yc.leetcode;

/**
 * 无重复字符的最长子串
 * longest-substring-without-repeating-characters
 *
 * @author Yue Chang
 * @version 1.0
 * @date 2021-02-04 10:39 下午
 */
public class LengthOfLongestSubstringSolution {

    public static void main(String[] args) {
        LengthOfLongestSubstringSolution instance = new LengthOfLongestSubstringSolution();

        System.out.println(instance.lengthOfLongestSubstring("abcabcd"));
        System.out.println();
        System.out.println(instance.lengthOfLongestSubstring("abcdefj"));
        System.out.println();
        System.out.println(instance.lengthOfLongestSubstring("au"));
    }

    public int lengthOfLongestSubstring(String str) {

        if (str == null || "".equals(str)) {
            return 0;
        }
        int length = str.length();

        int resultMaxLength = 1;
        for (int i = 0; i < length; i++) {
            // 记录本次的最长子串的长度
            int maxLength = 1;
            for (int j = i; j < length ; j++) {
                String subStr = str.substring(i, j + 1);
                String nextStr = "";
                // 如果不是最后的元素，截取下一个字符，否则为空串
                if (j + 2 <= length) {
                    nextStr = str.substring(j + 1, j + 2);
                }
                maxLength = subStr.length();
                if (subStr.contains(nextStr)) {
                    break;
                }
            }
            if (maxLength > resultMaxLength) {
                resultMaxLength = maxLength;
            }
        }
        return resultMaxLength;
    }
}
