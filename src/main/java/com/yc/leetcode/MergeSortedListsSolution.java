/* Copyright © 2020 Yuech and/or its affiliates. All rights reserved. */
package com.yc.leetcode;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 合并K个升序链表（运行超时）
 * merge-k-sorted-lists
 * https://leetcode-cn.com/problems/merge-k-sorted-lists/
 *
 * @author Yue Chang
 * @version 1.0
 * @date 2021-03-08 11:08 下午
 */
public class MergeSortedListsSolution {

    public static void main(String[] args) {

        MergeSortedListsSolution instance = new MergeSortedListsSolution();
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

        lists = new ListNode[]{
                new ListNode(1, new ListNode(4, new ListNode(5, null))),
                new ListNode(1, new ListNode(3, new ListNode(4, null))),
                new ListNode(2, new ListNode(6, new ListNode(50, null))),
                new ListNode(7, null)
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
        ListNode tempListNode = null;

        int minValue = (int)Math.pow(10, 4) + 1;
        // 值最小的索引
        int increaseIndex = -1;
        // KEY：lists下标，VALUE：lists下标对应元素是否为空
        Map<Integer, Boolean> iteratorMap = new ConcurrentHashMap<>((int)Math.pow(10, 4));

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
        tempListNode = lists[increaseIndex];
        resultListNode = tempListNode;
        lists[increaseIndex] = lists[increaseIndex].next;
        // 如果最小值元素为空，将对应值设置为true
        if (lists[increaseIndex] == null) {
            iteratorMap.put(increaseIndex, true);
        }

        int index = 0;
        minValue = (int)Math.pow(10, 4) + 1;

        while (lists[index] != null || iteratorMap.containsValue(false)) {

            if (null != lists[index] && lists[index].val < minValue) {
                minValue = lists[index].val;
                increaseIndex = index;
            }
            // 重置迭代索引
            if (index == lists.length - 1) {
                tempListNode.next = lists[increaseIndex];
                tempListNode = tempListNode.next;
                lists[increaseIndex] = lists[increaseIndex].next;

                // 如果元素为空，将对应值设置为true
                if (lists[increaseIndex] == null) {
                    iteratorMap.put(increaseIndex, true);
                }
                // 重置索引值
                index = -1;
                increaseIndex = -1;
                minValue = (int)Math.pow(10, 4) + 1;
            }
            index++;
        }
        return resultListNode;
    }

}

