/* Copyright © 2020 Yuech and/or its affiliates. All rights reserved. */
package com.yc.leetcode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * 将 x 减到 0 的最小操作数
 * minimum-operations-to-reduce-x-to-zero
 * https://leetcode-cn.com/problems/minimum-operations-to-reduce-x-to-zero/
 *
 * @author Yue Chang
 * @version 1.0
 * @date 2021-02-28 12:04 下午
 */
public class MinimumOperationsToReduceToZeroSolution {

    public static void main(String[] args) {

        MinimumOperationsToReduceToZeroSolution instance = new MinimumOperationsToReduceToZeroSolution();
        int result = instance.minOperations(new int[]{1, 1, 4, 2, 3}, 5);
        System.out.println(result);

        result = instance.minOperations(new int[]{5, 6, 7, 8, 9}, 4);
        System.out.println(result);

        result = instance.minOperations(new int[]{3, 2, 20, 1, 1, 3}, 10);
        System.out.println(result);

    }


    public int minOperations(int[] nums, int x) {

        if (null == nums) {
            return -1;
        }
        if (1 == nums.length) {
            if (nums[0] == x) {
                return 1;
            } else {
                return -1;
            }
        }

        List<Integer> list = new ArrayList<>();
        for (int num : nums) {
            if (num == x) {
                return 1;
            } else if (num < x) {
                list.add(num);
            }
        }
        Collections.sort(list);
        // doMinOperations();


        return -1;
    }
}
