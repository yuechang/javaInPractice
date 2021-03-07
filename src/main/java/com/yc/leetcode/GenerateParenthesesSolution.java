/* Copyright © 2020 Yuech and/or its affiliates. All rights reserved. */
package com.yc.leetcode;

import java.util.*;

/**
 * 括号生成
 * generate-parentheses
 * https://leetcode-cn.com/problems/generate-parentheses/
 *
 * @author Yue Chang
 * @version 1.0
 * @date 2021-03-07 3:32 下午
 */
public class GenerateParenthesesSolution {

    public static void main(String[] args) {

        GenerateParenthesesSolution instance = new GenerateParenthesesSolution();
        List<String> resultList = instance.generateParenthesis(1);
        System.out.println(resultList);
        System.out.println();

        resultList = instance.generateParenthesis(2);
        System.out.println(resultList);
        System.out.println();

        resultList = instance.generateParenthesis(3);
        System.out.println(resultList);
        System.out.println();

        resultList = instance.generateParenthesis(4);
        System.out.println(resultList);
        System.out.println();
    }


    /**
     * 生成N对括号组合
     *
     * @param n 括号数量
     * @return 括号组合
     */
    public List<String> generateParenthesis(int n) {

        Set<String> set = new HashSet<>();
        if (n < 0) {
            return null;
        } else if (1 == n) {
            return Collections.singletonList("()");
        }
        set.add("()");
        int parenthesesNum = 2;
        return doGenerateParenthesis(set, n, parenthesesNum);
    }

    private List<String> doGenerateParenthesis(Set<String> set, int n, int parenthesesNum) {

        if (parenthesesNum > n) {
            return new ArrayList<>(set);
        }
        Set<String> resultSet = new HashSet<>();
        for (String parenthesisStr : set) {
            for (int i = 0; i < parenthesisStr.length(); i++) {
                String prefixStr = parenthesisStr.substring(0, i);
                String suffixStr = parenthesisStr.substring(i);
                resultSet.add(prefixStr + "()" + suffixStr);
            }
        }
        parenthesesNum++;
        return doGenerateParenthesis(resultSet, n, parenthesesNum);
    }
}

