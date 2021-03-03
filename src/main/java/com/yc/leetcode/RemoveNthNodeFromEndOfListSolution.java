/* Copyright © 2020 Yuech and/or its affiliates. All rights reserved. */
package com.yc.leetcode;

/**
 * 删除链表的倒数第N个结点
 * remove-nth-node-from-end-of-list
 * https://leetcode-cn.com/problems/remove-nth-node-from-end-of-list/
 *
 * @author Yue Chang
 * @version 1.0
 * @date 2021-03-03 9:56 下午
 */
public class RemoveNthNodeFromEndOfListSolution {

    public static void main(String[] args) {

        ListNode listNode = new ListNode();
        RemoveNthNodeFromEndOfListSolution instance = new RemoveNthNodeFromEndOfListSolution();

        listNode.val = 1;
        listNode.next = new ListNode(2, new ListNode(3, new ListNode(4, new ListNode(5, null))));
        ListNode resultListNode = instance.removeNthFromEnd(listNode, 2);
        System.out.println(resultListNode);

        listNode.val = 1;
        listNode.next = new ListNode(2, null);
        resultListNode = instance.removeNthFromEnd(listNode, 2);
        System.out.println(resultListNode);

        listNode.val = 1;
        listNode.next = null;
        resultListNode = instance.removeNthFromEnd(listNode, 1);
        System.out.println(resultListNode);

        listNode.val = 0;
        listNode.next = new ListNode(1, new ListNode(2, new ListNode(3,
                new ListNode(4, new ListNode(5, new ListNode(6,
                        new ListNode(7, new ListNode(8, new ListNode(9, null)))))))));
        resultListNode = instance.removeNthFromEnd(listNode, 10);
        System.out.println(resultListNode);

    }

    public ListNode removeNthFromEnd(ListNode head, int n) {

        if (null == head) {
            return null;
        }
        if (1 == n && head.next == null) {
            return null;
        }
        ListNode tempListNode = head;
        int size = 1;
        while (head.next != null) {
            size++;
            head = head.next;
        }

        ListNode resultListNode = tempListNode;
        if (size == n) {
            resultListNode = resultListNode.next;
        } else {
            int index = 1;
            ListNode preListNode = tempListNode;
            while (tempListNode.next != null) {
                if (index == (size - n)) {
                    preListNode.next = tempListNode.next.next;
                    break;
                }
                preListNode = tempListNode.next;
                tempListNode = tempListNode.next;
                index++;

            }
        }
        return resultListNode;
    }
}

