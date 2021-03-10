/* Copyright © 2020 Yuech and/or its affiliates. All rights reserved. */
package com.yc.leetcode;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 合并K个升序链表
 * merge-k-sorted-lists
 * https://leetcode-cn.com/problems/merge-k-sorted-lists/
 *
 * @author Yue Chang
 * @version 1.0
 * @date 2021-03-10 10:05 下午
 */
public class MergeSortedListsSolution2 {

    public static void main(String[] args) {

        MergeSortedListsSolution2 instance = new MergeSortedListsSolution2();

        /*
        ListNode[] lists = new ListNode[]{
                new ListNode(7, null),
                new ListNode(49, null),
                new ListNode(73, null),
                new ListNode(58, null),
                new ListNode(30, null),

                new ListNode(72, null),
                new ListNode(44, null),
                new ListNode(78, null),
                new ListNode(23, null),
                new ListNode(9, null),

                new ListNode(40, null),
                new ListNode(65, null),
                new ListNode(92, null),
                new ListNode(42, null),
                new ListNode(87, null),

                new ListNode(3, null),
                new ListNode(27, null),
                new ListNode(29, null),
                new ListNode(40, null),
                new ListNode(12, null),

                new ListNode(3, null),
                new ListNode(69, null),
                new ListNode(9, null),
                new ListNode(47, null)
        };

        ListNode listNode = instance.mergeKLists(lists);
        System.out.println(listNode);
        System.out.println();
        */

        ListNode[] lists = new ListNode[]{
                new ListNode(1, new ListNode(4, new ListNode(5, null))),
                new ListNode(1, new ListNode(3, new ListNode(4, null))),
                new ListNode(2, new ListNode(6, null))
        };
        ListNode listNode = instance.mergeKLists(lists);
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
        ListNode tempListNode = null;

        int minValue = (int)Math.pow(10, 4) + 1;
        // 值最小的索引
        int increaseIndex = -1;
        // KEY：lists下标，VALUE：lists下标对应元素是否为空
        Map<Integer, Boolean> iteratorMap = new ConcurrentHashMap<>();

        for (int i = 0; i < lists.length; i++) {
            ListNode list = lists[i];
            iteratorMap.put(i, false);
            if (list == null) {
                iteratorMap.put(i, true);
            }
            if (list != null && list.val < minValue) {
                minValue = list.val;
                increaseIndex = i;
            }
        }
        // 如果节点为空
        if (!iteratorMap.containsValue(false)) {
            return null;
        }
        // 开始不是新建一个节点来作为tempListNode，而是指向的lists[increaseIndex]，导致传入之后循环引用的问题
        tempListNode = new ListNode(lists[increaseIndex].val, null);
        resultListNode = tempListNode;
        lists[increaseIndex] = lists[increaseIndex].next;

        for (ListNode listNode : lists) {
            if (listNode == null) {
                continue;
            }
            resultListNode = mergeTwoLists(tempListNode, listNode);
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