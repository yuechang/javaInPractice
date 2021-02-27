/* Copyright © 2020 Yuech and/or its affiliates. All rights reserved. */
package com.yc.leetcode;

/**
 * 最接近的三数之和
 * 3sum-closest
 * https://leetcode-cn.com/problems/3sum-closest/
 *
 * @author Yue Chang
 * @version 1.0
 * @date 2021-02-22 14:05
 */
public class ThreeSumClosestSolution {

    public static void main(String[] args) {

        ThreeSumClosestSolution instance = new ThreeSumClosestSolution();

        int result = instance.threeSumClosest(new int[]{-1,2,1,-4}, 1);
        System.out.println(result);

        instance.threeSumClosest(new int[]{1, 1, 1, 0}, -100);
        System.out.println(result);

        result = instance.threeSumClosest(new int[]{1, 1, 1, 1}, 0);
        System.out.println(result);

        instance.threeSumClosest(new int[]{-1, 2, 1, -4}, 1);
        System.out.println(result);
    }


    public int threeSumClosest(int[] nums, int target) {

        if (null == nums || nums.length < 3) {
            return 0;
        }
        if (3 == nums.length) {
            return nums[0] + nums[1] + nums[2];
        }
        int resultValue = nums[0] + nums[1] + nums[2];
        int minInterval = resultValue - target;
        if (minInterval < 0) {
            minInterval = -minInterval;
        }
        for (int i = 0; i < nums.length; i++) {
            for (int j = i + 1; j < nums.length; j++) {
                for (int k = j + 1; k < nums.length; k++) {

                    int value = nums[i] + nums[j] + nums[k];
                    int interval = value - target;
                    if (interval < 0 ) {
                        interval = -interval;
                    }
                    if (interval < minInterval) {
                        minInterval = interval;
                        resultValue = value;
                    }
                }
            }
        }
        return resultValue;
    }
}

