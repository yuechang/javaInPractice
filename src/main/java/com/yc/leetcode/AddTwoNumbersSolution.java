/* Copyright © 2020 Yuech and/or its affiliates. All rights reserved. */
package com.yc.leetcode;

/**
 * 两数相加
 * add-two-numbers
 *
 * @author Yue Chang
 * @version 1.0
 * @date 2021-01-23 下午10:06
 */
public class AddTwoNumbersSolution {

    public static void main(String[] args) {

        ListNode l1 = new ListNode();
        l1.val = 9;
        l1.next = new ListNode(9, new ListNode(9, new ListNode(9, new ListNode(9, new ListNode(9, new ListNode(9, null))))));

        ListNode l2 = new ListNode();
        l2.val = 9;
        l2.next = new ListNode(9, new ListNode(9, new ListNode(9, null)));
        ListNode listNode = new AddTwoNumbersSolution().addTwoNumbers(l1, l2);
        System.out.println(listNode);


        l1 = new ListNode(9, null);
        l2 = new ListNode();
        l2.val = 1;
        l2.next = new ListNode(9, new ListNode(9, new ListNode(9,
                new ListNode(9, new ListNode(9, new ListNode(9,
                new ListNode(9, new ListNode(9, new ListNode(9, null)))))))));
        listNode = new AddTwoNumbersSolution().addTwoNumbers(l1, l2);
        System.out.println(listNode);
    }

    public ListNode addTwoNumbers(ListNode l1, ListNode l2) {

        // 获得第一个数字
        int multiple = 1;
        int number1 = 0;
        while (l1.next != null ) {
            number1 += l1.val*multiple;
            multiple *= 10;
            l1 = l1.next;
        }
        number1 += l1.val*multiple;

        // 获得第二个数字
        multiple = 1;
        int number2 = 0;
        while(l2.next != null) {
            number2 += l2.val*multiple;
            multiple *= 10;
            l2 = l2.next;
        }
        number2 += l2.val*multiple;

        // 两数相加，超出int范围时，处理有问题
        int sum = number1 + number2;

        ListNode resultListNode = new ListNode();
        getResultListNode(sum, resultListNode);
        return resultListNode;
    }

    /**
     * 倒序输出ListNode节点
     *
     * @param sum 总和
     * @param currentListNode 当前节点
     * @return 最后的ListNode节点
     */
    ListNode getResultListNode(int sum, ListNode currentListNode) {
        if (sum / 10 == 0) {
            currentListNode.val = sum;
            currentListNode.next = null;
            return currentListNode;
        } else {
            ListNode nextListNode = new ListNode();
            currentListNode.val = sum % 10;
            currentListNode.next = nextListNode;
            sum /= 10;
            return getResultListNode(sum, nextListNode);
        }
    }
}