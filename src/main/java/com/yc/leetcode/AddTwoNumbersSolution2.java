/* Copyright © 2020 Yuech and/or its affiliates. All rights reserved. */
package com.yc.leetcode;

/**
 * 两数相加
 * add-two-numbers
 * https://leetcode-cn.com/problems/add-two-numbers/
 *
 * @author Yue Chang
 * @version 2.0
 * @date 2021-02-01 下午10:18
 */
public class AddTwoNumbersSolution2 {

    public static void main(String[] args) {

        ListNode l1 = new ListNode(9, null);
        ListNode l2 = new ListNode();
        l2.val = 9;
        l2.next = new ListNode(9, new ListNode(9, new ListNode(9,
                new ListNode(9, new ListNode(9, new ListNode(9,
                        new ListNode(9, new ListNode(9, new ListNode(9, null)))))))));

        ListNode listNode = new AddTwoNumbersSolution2().addTwoNumbers(l1, l2);
        System.out.println(listNode);

    }

    /**
     * 两数相加
     *
     * @param l1 第一个数
     * @param l2 第二个数
     * @return 结果ListNode
     */
    public ListNode addTwoNumbers(ListNode l1, ListNode l2) {

        if (l1 == null) {
            return l2;
        }
        if (l2 == null) {
            return l1;
        }

        ListNode result = new ListNode();
        int carray = 0;
        int sum = l1.val + l2.val;
        // 有进位
        if (sum >= 10) {
            carray = 1;
            result.val = sum % 10;
        } else {
            result.val = sum;
        }
        doAddTwoNumbers(l1.next, l2.next, carray, result);
        return result;
    }

    private void doAddTwoNumbers(ListNode l1, ListNode l2, int carry, ListNode result) {

        if (l1 == null && l2 == null) {
            // 如果还有进位，新增一个节点
            if (carry > 0) {
                result.next = new ListNode(1, null);
            } else {
                result.next = null;
            }
        } else {

            int value1 = 0;
            ListNode nextL1 = null;
            if (l1 != null) {
                value1 = l1.val;
                nextL1 = l1.next;
            }

            int value2 = 0;
            ListNode nextL2 = null;
            if (l2 != null) {
                value2 = l2.val;
                nextL2 = l2.next;
            }

            int nextCarry = 0;
            int sum = value1 + value2 + carry;
            int resultNextValue;
            // 有进位
            if (sum >= 10) {
                nextCarry = 1;
                resultNextValue = sum % 10;
            } else {
                resultNextValue = sum;
            }
            ListNode nextResultListNode = new ListNode(resultNextValue, null);
            result.next = nextResultListNode;
            doAddTwoNumbers(nextL1, nextL2, nextCarry, nextResultListNode);
        }
    }
}
