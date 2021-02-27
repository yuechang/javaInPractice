/* Copyright © 2020 Yuech and/or its affiliates. All rights reserved. */
package com.yc.leetcode;

import java.util.Arrays;

/**
 * 最接近的三数之和
 * 3sum-closest
 * https://leetcode-cn.com/problems/3sum-closest/
 *
 * @author Yue Chang
 * @version 1.0
 * @date 2021-02-23 8:39 下午
 */
public class ThreeSumClosestSolution2 {

    public static void main(String[] args) {

        ThreeSumClosestSolution2 instance = new ThreeSumClosestSolution2();

        int result = instance.threeSumClosest(new int[]{-1, 2, 1, -4}, 1);
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
        Arrays.sort(nums);
        int resultValue = nums[0] + nums[1] + nums[2];
        int minInterval = resultValue - target;
        if (minInterval < 0) {
            minInterval = -minInterval;
        }
        for (int i = 0; i < nums.length; i++) {
            int startIndex = i + 1;
            int endIndex = nums.length - 1;

            while (startIndex < endIndex) {

                int sum = nums[i] + nums[startIndex] + nums[endIndex];
                int interval = sum - target;
                // 如果相等，直接返回三数和
                if (0 == interval) {
                    return sum;
                }else if (interval < 0) {
                    interval = -interval;
                }

                if (interval < minInterval) {
                    minInterval = interval;
                    resultValue = sum;
                }
                if (sum > target) {
                    endIndex--;
                    // endIndex 已经自减1，初始值需要先将1加回得到当前的结束索引值
                    while (startIndex < endIndex && nums[endIndex + 1] == nums[endIndex]) {
                        endIndex--;
                    }
                } else if (sum < target) {
                    startIndex++;
                    // startIndex 已经自增1，初始值需要先将1减去得到当前的开始索引值
                    while (startIndex < endIndex && nums[startIndex - 1] == nums[startIndex]) {
                        startIndex++;
                    }
                }
            }
        }
        return resultValue;
    }
}
