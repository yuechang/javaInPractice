/* Copyright © 2020 Yuech and/or its affiliates. All rights reserved. */
package com.yc.leetcode;

/**
 * 寻找中位数
 * median-of-two-sorted-arrays
 * https://leetcode-cn.com/problems/median-of-two-sorted-arrays/
 *
 * @author Yue Chang
 * @version 1.0
 * @date 2021-02-04 10:25 下午
 */
public class MedianOfTwoSortedArraysSolution {

    public static void main(String[] args) {

        MedianOfTwoSortedArraysSolution instance = new MedianOfTwoSortedArraysSolution();

        int[] nums1 = {1,3};
        int[] nums2 = {2};
        double result = instance.findMedianSortedArrays(nums1, nums2);
        System.out.println(result);
    }

    public double findMedianSortedArrays(int[] nums1, int[] nums2) {

        // 总数
        int count = nums1.length + nums2.length;

        boolean isOdd = true;
        int median = count/2;
        // 元素总数为偶数，找其中的两个值[median-1] 及 [median]
        if (count % 2 == 0) {
            isOdd = false;
        }

        int nums1Index = 0;
        int nums2Index = 0;
        // 汇总数组，仅仅需要统计到[median]，所以新建长度为median + 1的汇总数组
        int[] summaryArr = new int[median + 1];

        for (int i = 0; i <= median; i++) {
            // 如果nums1Index >= nums1.length，表示nums1全部添加到了汇总数组中，接下来把nums2数组中元素添加到汇总数组
            if (nums1Index >= nums1.length) {
                summaryArr[i] = nums2[nums2Index++];
            } else if (nums2Index >= nums2.length) {
                summaryArr[i] = nums1[nums1Index++];
            }
            // 如果nums1[nums1Index] > nums2[nums2Index]，将小的元素nums2[nums2Index]添加到汇总数组，并且将下标自增
            else if (nums1[nums1Index] > nums2[nums2Index]) {
                summaryArr[i] = nums2[nums2Index++];
            } else {
                summaryArr[i] = nums1[nums1Index++];
            }
        }
        if (!isOdd) {
            return (summaryArr[median-1] + summaryArr[median]) / 2.0;
        }
        return summaryArr[median];
    }
}
