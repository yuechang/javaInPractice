/* Copyright © 2020 Yuech and/or its affiliates. All rights reserved. */
package com.yc.leetcode;

/**
 * 合并两个有序链表
 * merge-two-sorted-lists
 * https://leetcode-cn.com/problems/merge-two-sorted-lists/
 *
 * @author Yue Chang
 * @version 1.0
 * @date 2021-03-03 10:00 下午
 */
public class MergeTwoSortedListsSolution {


    public static void main(String[] args) {

        MergeTwoSortedListsSolution instance = new MergeTwoSortedListsSolution();
        ListNode l1 = new ListNode(1,new ListNode(2, new ListNode(12, null)));
        ListNode l2 = new ListNode(5,new ListNode(6, new ListNode(9, null)));
        ListNode resultListNode = instance.mergeTwoLists(l1, l2);
        System.out.println(resultListNode);

    }


    /**
     * 合并两个有序链表
     *
     * @param l1 有序列表1
     * @param l2 有序列表2
     * @return 有序结果列表
     */
    public ListNode mergeTwoLists(ListNode l1, ListNode l2) {

        ListNode resultListNode = null;
        ListNode tempListNode = null;

        if (null == l1) {
            return l2;
        }
        if (null == l2) {
            return l1;
        }

        // 赋值结果集
        if (l1.val > l2.val) {
            tempListNode = l2;
            resultListNode = tempListNode;
            l2 = l2.next;
        } else {
            tempListNode = l1;
            resultListNode = tempListNode;
            l1 = l1.next;
        }

        // 迭代有序链表
        while (l1 != null || l2 != null) {

            if (l1 == null) {
                tempListNode.next = l2;
                tempListNode = tempListNode.next;
                l2 = l2.next;
            } else if (l2 == null) {
                tempListNode.next = l1;
                tempListNode = tempListNode.next;
                l1 = l1.next;
            } else if (l1.val > l2.val) {
                tempListNode.next = l2;
                tempListNode = tempListNode.next;
                l2 = l2.next;
            } else {
                tempListNode.next = l1;
                tempListNode = tempListNode.next;
                l1 = l1.next;
            }
        }
        return resultListNode;
    }
}

