/* Copyright © 2020 Yuech and/or its affiliates. All rights reserved. */
package com.yc.leetcode;

/**
 * 有效的括号
 * valid-parentheses
 * https://leetcode-cn.com/problems/valid-parentheses/
 *
 * @author Yue Chang
 * @version 1.0
 * @date 2021-03-04 11:38 下午
 */
public class ValidParenthesesSolution {

    public static void main(String[] args) {

        ValidParenthesesSolution instance = new ValidParenthesesSolution();
        boolean valid = instance.isValid("(()(");
        System.out.println(valid);

        valid = instance.isValid("()[]}{");
        System.out.println(valid);

        valid = instance.isValid("()[]{}");
        System.out.println(valid);

        valid = instance.isValid("{{{{{{{[]}}}}}}");
        System.out.println(valid);
    }


    /**
     * 左方括号:[
     */
    public static final String OPEN_BRACKET = "[";

    /**
     * 右方括号:]
     */
    public static final String CLOSE_BRACKET = "]";

    /**
     * 左圆括号:(
     */
    public static final String OPEN_PARENTHESIS = "(";

    /**
     * 右圆括号:)
     */
    public static final String CLOSE_PARENTHESIS = ")";

    /**
     * 左花括号:{
     */
    public static final String OPEN_BRACE = "{";

    /**
     * 右花括号:}
     */
    public static final String CLOSE_BRACE = "}";

    /**
     * 判断括号字符串是否合法
     *
     * @param str 括号字符串
     * @return 是否合法
     */
    public boolean isValid(String str) {

        if (null == str || "".equals(str) || str.length()%2 == 1) {
            return false;
        }
        return doCheck(str, str.length(), 0);
    }

    /**
     * 判断括号字符串是否合法
     * @param str 括号字符串
     * @param length 括号字符串总长度
     * @param checkCount 检查次数
     * @return 是否合法
     */
    private boolean doCheck(String str, int length, int checkCount) {

        if (null == str || "".equals(str)) {
            return true;
        }

        if (checkCount*2 >= length) {
            return false;
        }

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < str.length(); i++) {
            String startCharacter = str.charAt(i) + "";
            String endCharacter = "";
            if (i + 1 < str.length()) {
                endCharacter = str.charAt(i + 1) + "";
            }

            boolean isValid = (OPEN_BRACE.equals(startCharacter) && CLOSE_BRACE.equals(endCharacter)) ||
                    (OPEN_BRACKET.equals(startCharacter) && CLOSE_BRACKET.equals(endCharacter)) ||
                    (OPEN_PARENTHESIS.equals(startCharacter) && CLOSE_PARENTHESIS.equals(endCharacter));
            if (isValid) {
                i++;
            } else {
                sb.append(startCharacter);
            }
        }
        checkCount++;
        return doCheck(sb.toString(), length, checkCount);
    }
}
