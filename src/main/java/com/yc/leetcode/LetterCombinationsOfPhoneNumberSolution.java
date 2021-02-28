/* Copyright © 2020 Yuech and/or its affiliates. All rights reserved. */
package com.yc.leetcode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 电话号码的字母组合
 * letter-combinations-of-a-phone-number
 * https://leetcode-cn.com/problems/letter-combinations-of-a-phone-number/
 *
 * @author Yue Chang
 * @version 1.0
 * @date 2021-02-28 10:44 上午
 */
public class LetterCombinationsOfPhoneNumberSolution {

    public static void main(String[] args) {

        LetterCombinationsOfPhoneNumberSolution instance = new LetterCombinationsOfPhoneNumberSolution();
        List<String> resultList = instance.letterCombinations("2");
        System.out.println(resultList);

        resultList = instance.letterCombinations("23");
        System.out.println(resultList);

        resultList = instance.letterCombinations("2345");
        System.out.println(resultList);

    }

    /**
     * 电话号码对应字母组合二维数组
     */
    private static final String[][] PHONE_NUMBER_AND_LETTER_ARR =
            {{},
                    {},
                    {"a", "b", "c"},
                    {"d", "e", "f"},
                    {"g", "h", "i"},
                    {"j", "k", "l"},
                    {"m", "n", "o"},
                    {"p", "q", "r", "s"},
                    {"t", "u", "v"},
                    {"w", "x", "y", "z"}
            };

    /**
     * 获得电话号码的字母组合
     *
     * @param digits 电话号码
     * @return 电话号码的字母组合
     */
    public List<String> letterCombinations(String digits) {
        return doLetterCombinations(digits, new ArrayList<>());
    }

    /**
     * 获得电话号码的字母组合
     *
     * @param digits                电话号码
     * @param letterCombinationList 组合列表
     * @return 电话号码的字母组合
     */
    private List<String> doLetterCombinations(String digits, List<String> letterCombinationList) {

        if (null == digits || "".equals(digits)) {
            return letterCombinationList;
        }
        List<String> resultList = new ArrayList<>();
        // 第一个数字对应的字母数组
        String[] letterList = PHONE_NUMBER_AND_LETTER_ARR[Integer.parseInt(digits.charAt(0) + "")];
        // 如果组合列表为空，赋值后转入下一个递归
        if (null == letterCombinationList || letterCombinationList.size() <= 0) {
            resultList = Arrays.asList(letterList);
        }
        // 如果组合列表不为空，循环字母数组和组合列表
        else {
            for (String letter : letterList) {
                for (String letterCombination : letterCombinationList) {
                    resultList.add(letterCombination + letter);
                }
            }
        }
        return doLetterCombinations(digits.substring(1), resultList);
    }
}
