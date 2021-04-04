/* Copyright © 2020 Yuech and/or its affiliates. All rights reserved. */
package com.yc.leetcode;

/**
 * 两两交换链表中的节点
 * swap-nodes-in-pairs
 * https://leetcode-cn.com/problems/swap-nodes-in-pairs/
 *
 * @author Yue Chang
 * @version 1.0
 * @date 2021-04-04 9:25 下午
 */
public class SwapNodesInPairsSolution {


    public static void main(String[] args) {
        SwapNodesInPairsSolution instance = new SwapNodesInPairsSolution();

        ListNode listNode = new ListNode(1, new ListNode(2, new ListNode(3, new ListNode(4, new ListNode(5, null)))));
        ListNode resultListNode = instance.swapPairs(listNode);
        System.out.println(resultListNode);

        listNode = new ListNode(11, new ListNode(2, null));
        resultListNode = instance.swapPairs(listNode);
        System.out.println(resultListNode);

        listNode = new ListNode(11, new ListNode(2, new ListNode(3, null)));
        resultListNode = instance.swapPairs(listNode);
        System.out.println(resultListNode);

        listNode = new ListNode(11, new ListNode(2, new ListNode(3, new ListNode(4, null))));
        resultListNode = instance.swapPairs(listNode);
        System.out.println(resultListNode);

    }

    public ListNode swapPairs(ListNode head) {

        if (null == head) {
            return null;
        }

        if (null == head.next) {
            return head;
        }

        ListNode result = null;
        ListNode tempResultList = null;

        while(head != null) {

            ListNode firstListNode = head;
            head = head.next;
            // 将首元素的下一个元素置为null，解决循环引用问题
            firstListNode.next = null;

            ListNode secondListNode = null;
            if (head != null) {
                secondListNode = head;
                head = head.next;
            }

            if (result == null) {
                tempResultList = secondListNode;
                result = tempResultList;

                tempResultList.next = firstListNode;
                tempResultList = tempResultList.next;
            } else if (null == secondListNode) {
                tempResultList.next = firstListNode;
                tempResultList = tempResultList.next;
            } else if (null != secondListNode) {

                tempResultList.next = secondListNode;
                tempResultList = tempResultList.next;

                tempResultList.next = firstListNode;
                tempResultList = tempResultList.next;
            }
        }
        return result;
    }
}

