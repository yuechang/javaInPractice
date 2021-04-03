/* Copyright © 2020 Yuech and/or its affiliates. All rights reserved. */
package com.yc.leetcode;

/**
 * 合并K个升序链表
 * merge-k-sorted-lists
 * https://leetcode-cn.com/problems/merge-k-sorted-lists/
 *
 * @author Yue Chang
 * @version 1.0
 * @date 2021-04-03 10:07 下午
 */
public class MergeSortedListsSolution3 {

    public static void main(String[] args) {

        MergeSortedListsSolution3 instance = new MergeSortedListsSolution3();
        ListNode[] lists = new ListNode[] {null,
                new ListNode(-1, new ListNode(5, new ListNode(11, null))),
                null,
                new ListNode(6, new ListNode(10, null))};
        ListNode listNode = instance.mergeKLists(lists);
        System.out.println(listNode);
        System.out.println();

        lists = new ListNode[]{
                new ListNode(1, new ListNode(4, new ListNode(5, null))),
                new ListNode(1, new ListNode(3, new ListNode(4, null))),
                new ListNode(2, new ListNode(6, null))
        };
        listNode = instance.mergeKLists(lists);
        System.out.println(listNode);
        System.out.println();

        lists = null;
        listNode = instance.mergeKLists(lists);
        System.out.println(listNode);
        System.out.println();

        lists = new ListNode[]{null};
        listNode = instance.mergeKLists(lists);
        System.out.println(listNode);
    }

    public ListNode mergeKLists(ListNode[] lists) {

        if (null == lists || lists.length <= 0) {
            return null;
        }

        ListNode resultListNode = null;
        for (ListNode listNode : lists) {
            if (listNode == null) {
                continue;
            }
            resultListNode = mergeTwoLists(resultListNode, listNode);
        }
        return resultListNode;
    }

    /**
     * 合并两个有序链表
     *
     * @param l1 有序列表1
     * @param l2 有序列表2
     * @return 有序结果列表
     */
    public ListNode mergeTwoLists(ListNode l1, ListNode l2) {

        ListNode result;
        ListNode temp;

        if (null == l1) {
            return l2;
        }
        if (null == l2) {
            return l1;
        }

        // 赋值结果集
        if (l1.val > l2.val) {
            temp = l2;
            result = temp;
            l2 = l2.next;
        } else {
            temp = l1;
            result = temp;
            l1 = l1.next;
        }
        // 迭代有序链表
        while (l1 != null || l2 != null) {

            if (l1 == null) {
                temp.next = l2;
                temp = temp.next;
                l2 = l2.next;
            } else if (l2 == null) {
                temp.next = l1;
                temp = temp.next;
                l1 = l1.next;
            } else if (l1.val > l2.val) {
                temp.next = l2;
                temp = temp.next;
                l2 = l2.next;
            } else {
                temp.next = l1;
                temp = temp.next;
                l1 = l1.next;
            }
        }
        return result;
    }
}

