/* Copyright © 2020 Yuech and/or its affiliates. All rights reserved. */
package com.yc.leetcode;

/**
 * 长公共前缀
 * longest-common-prefix
 * https://leetcode-cn.com/problems/longest-common-prefix/
 *
 * @author Yue Chang
 * @version 1.0
 * @date 2021-02-19 9:41 下午
 */
public class LongestCommonPrefixSolution {

    public static void main(String[] args) {

        LongestCommonPrefixSolution instance = new LongestCommonPrefixSolution();
        String prefix = instance.longestCommonPrefix(new String[]{"flower", "flow", "flight"});
        System.out.println(prefix);

        prefix = instance.longestCommonPrefix(new String[]{"dog","racecar","car"});
        System.out.println(prefix);

        prefix = instance.longestCommonPrefix(new String[]{"dog"});
        System.out.println(prefix);

    }


    public String longestCommonPrefix(String[] strArr) {

        if (null == strArr || strArr.length == 0 || "".equals(strArr[0])) {
            return "";
        }
        String prefix = String.valueOf(strArr[0].charAt(0));
        return doFindLongestCommonPrefix(prefix, strArr);
    }

    private String doFindLongestCommonPrefix(String prefix, String[] strArr) {

        for (String s : strArr) {
            if (!s.startsWith(prefix)) {
                return prefix.substring(0, prefix.length() - 1);
            }
        }
        String str = strArr[0];
        int prefixEndIndex = str.indexOf(prefix) + prefix.length() + 1;
        // 如果前缀结束索引大于第一个元素的长度时，返回前缀结果
        if (prefixEndIndex > strArr[0].length()) {
            return prefix;
        }
        prefix = str.substring(0, prefixEndIndex);
        return doFindLongestCommonPrefix(prefix, strArr);
    }
}
