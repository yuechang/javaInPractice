/* Copyright © 2020 Yuech and/or its affiliates. All rights reserved. */
package com.yc.leetcode;

/**
 * 链表节点
 *
 * @author Yue Chang
 * @version 1.0
 * @date 2021-01-24 上午9:05
 */
public class ListNode {

    int val;
    com.yc.leetcode.ListNode next;

    ListNode() {
    }

    ListNode(int val) {
        this.val = val;
    }

    ListNode(int val, com.yc.leetcode.ListNode next) {
        this.val = val;
        this.next = next;
    }


    public int getVal() {
        return val;
    }

    public void setVal(int val) {
        this.val = val;
    }

    public com.yc.leetcode.ListNode getNext() {
        return next;
    }

    public void setNext(com.yc.leetcode.ListNode next) {
        this.next = next;
    }

    @Override
    public String toString() {
        return "ListNode{" +
                "val=" + val +
                ", next=" + next +
                '}';
    }

}
