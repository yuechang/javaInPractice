/* Copyright © 2020 Yuech and/or its affiliates. All rights reserved. */
package com.yc.leetcode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 四数之和
 * 4sum
 * https://leetcode-cn.com/problems/4sum/
 *
 * @author Yue Chang
 * @version 1.0
 * @date 2021-02-24 14:07
 */
public class FourSumSolution {

    public static void main(String[] args) {

        FourSumSolution instance = new FourSumSolution();
        List<List<Integer>> lists = instance.fourSum(new int[]{-2, -1, -1, 1, 1, 2, 2}, 0);
        System.out.println(lists);

        lists = instance.fourSum(new int[]{-7, -5, 0, 7, 1, 1, -10, -2, 7, 7, -2, -6, 0, -10, -5, 7, -8, 5}, 28);
        System.out.println(lists);

        lists = instance.fourSum(new int[]{1, 0, -1, 0, -2, 2}, 0);
        System.out.println(lists);


    }

    /**
     * 返回四数之和为目标值的组合列表
     *
     * @param nums 数组
     * @param target 目标值
     * @return 四数之和为目标值的组合列表
     */
    public List<List<Integer>> fourSum(int[] nums, int target) {

        List<List<Integer>> resultList = new ArrayList<>();
        if (null == nums || nums.length < 4) {
            return resultList;
        }
        if (4 == nums.length) {
            if (target == (nums[0] + nums[1] + nums[2] + nums[3])) {
                resultList.add(Arrays.asList(nums[0], nums[1], nums[2], nums[3]));
            }
            return resultList;
        }

        Arrays.sort(nums);


        for (int i = 0; i < nums.length; i++) {
            for (int j = i + 1; j < nums.length; j++) {
                int startIndex = j + 1;
                int endIndex = nums.length - 1;

                while (startIndex < endIndex) {

                    int sum = nums[i] + nums[j] + nums[startIndex] + nums[endIndex];
                    if (sum > target) {
                        endIndex--;
                        while (startIndex < endIndex && nums[endIndex + 1] == nums[endIndex]) {
                            endIndex--;
                        }
                    } else if (sum < target) {
                        startIndex++;
                        while (startIndex < endIndex && nums[startIndex - 1] == nums[startIndex]) {
                            startIndex++;
                        }
                    } else {
                        resultList.add(Arrays.asList(nums[i], nums[j], nums[startIndex], nums[endIndex]));
                        startIndex++;
                        endIndex--;
                        while (startIndex < endIndex && nums[startIndex - 1] == nums[startIndex]) {
                            startIndex++;
                        }
                        while (startIndex < endIndex && nums[endIndex + 1] == nums[endIndex]) {
                            endIndex--;
                        }
                        while (i < nums.length - 1 && nums[i] == nums[i + 1]) {
                            i++;
                        }
                        while (j < nums.length - 1 && nums[j] == nums[j + 1]) {
                            j++;
                        }
                    }
                }
            }
        }
        return resultList;
    }
}

