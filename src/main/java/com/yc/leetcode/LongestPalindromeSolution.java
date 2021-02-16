/* Copyright © 2020 Yuech and/or its affiliates. All rights reserved. */
package com.yc.leetcode;

/**
 * 最长回文子串（此解法提交时超时）
 * longest-palindromic-substring
 * https://leetcode-cn.com/problems/longest-palindromic-substring/
 *
 * @author Yue Chang
 * @version 1.0
 * @date 2021-02-16 10:57 上午
 */
public class LongestPalindromeSolution {

    public static void main(String[] args) {

        LongestPalindromeSolution instance = new LongestPalindromeSolution();
        long start = System.currentTimeMillis();
        System.out.println(instance.longestPalindrome("yzwhuvljgkbxonhkpnxldwkaiboqoflbotqamsxyglfqniflcrtsxbsxlwmxowwnnxychyrjedlijejqzsgwakzohghpxgamecmhcalfoyjtutxeciwqupwlxrgdcpfvybskrytvmwkvnbdjitmohjavhmjobudvbsnkvszyrahpanocltwzeubgxkkthxhjgvcvygfkjctkubtjdocncmjzmxujetybdwmqutvrrulhlsbcbripctbkacwoutkrqsohiihiegqqlasykkgjjskgphieofsjlkkmvwcltgjqbpakercxypfcqqsmkowmgjglbzbidapmqoitpzwhupliynjynsdfncaosrfegquetyfhfqohxytqhjxxpskpwxegmnnppnnmgexwpkspxxjhqtyxhoqfhfyteuqgefrsoacnfdsnyjnyilpuhwzptioqmpadibzblgjgmwokmsqqcfpyxcrekapbqjgtlcwvmkkljsfoeihpgksjjgkkysalqqgeihiihosqrktuowcakbtcpirbcbslhlurrvtuqmwdbytejuxmzjmcncodjtbuktcjkfgyvcvgjhxhtkkxgbuezwtlconapharyzsvknsbvdubojmhvajhomtijdbnvkwmvtyrksbyvfpcdgrxlwpuqwicextutjyoflachmcemagxphghozkawgszqjejildejryhcyxnnwwoxmwlxsbxstrclfinqflgyxsmaqtoblfoqobiakwdlxnpkhnoxbkgjlvuhwzy"));
        System.out.println(System.currentTimeMillis() - start);
    }

    public String longestPalindrome(String str) {

        if (str == null || "".equals(str) || str.length() == 1) {
            return str;
        }
        int length = str.length();

        byte[] byteArr = str.getBytes();

        // 如果仅仅存在一种类型的字符
        boolean flag = true;
        for (int j = 1; j < length; j++) {
            if (byteArr[0] != byteArr[j]) {
                flag = false;
                break;
            }
        }
        if (flag) {
            return str;
        }

        String resultLongestPalindrome = "";
        for (int i = 0; i < length; i++) {
            // 记录本次的最长回文子串
            String longestPalindrome = "";
            for (int j = i; j < length ; j++) {
                String subStr = str.substring(i, j + 1);

                // 判断是否为回文
                if (isPalindromeStr(subStr)) {
                    longestPalindrome = subStr;
                }
            }
            if (longestPalindrome.length() > resultLongestPalindrome.length()) {
                resultLongestPalindrome = longestPalindrome;
            }
        }
        return resultLongestPalindrome;
    }

    /**
     * 判断字符串是否为回文串
     *
     * @param subStr 字符串
     * @return true：是回文串，false：不是回文串
     */
    private boolean isPalindromeStr(String subStr) {

        if (null == subStr || "".equals(subStr) || 1 == subStr.length()) {
            return true;
        }
        byte[] strArr = subStr.getBytes();
        int strLength = strArr.length;
        boolean flag = true;
        for (int i = 0; i < strLength /2; i++) {
            if (strArr[i] != strArr[strLength-i-1]) {
                flag = false;
                break;
            }
        }
        return flag;
    }
}
