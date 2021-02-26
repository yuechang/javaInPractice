/* Copyright © 2020 Yuech and/or its affiliates. All rights reserved. */
package com.yc.leetcode;

import java.util.*;

/**
 * 三数之和
 * 3sum
 * https://leetcode-cn.com/problems/3sum/
 *
 * @author Yue Chang
 * @version 1.0
 * @date 2021-02-22 9:00
 */
public class ThreeSumSolution2 {

    public static void main(String[] args) {

        ThreeSumSolution2 instance = new ThreeSumSolution2();
        List<List<Integer>> lists = instance.threeSum(new int[]{34, 55, 79, 28, 46, 33, 2, 48, 31, -3, 84, 71, 52, -3, 93, 15, 21, -43, 57, -6, 86, 56, 94, 74, 83, -14, 28, -66, 46, -49, 62, -11, 43, 65, 77, 12, 47, 61, 26, 1, 13, 29, 55, -82, 76, 26, 15, -29, 36, -29, 10, -70, 69, 17, 49});
        System.out.println(lists);

        lists = instance.threeSum(new int[]{-1, 0, 1, 2, -1, -4, -2, -3, 3, 0, 4});
        System.out.println(lists);

        lists = instance.threeSum(new int[]{-1, 0, 1, 2, -1, -4});
        System.out.println(lists);

        lists = instance.threeSum(new int[]{0});

        System.out.println(lists);

        lists = instance.threeSum(new int[]{-1, 0, 1, 2, -1, -4});
        System.out.println(lists);
    }

    public List<List<Integer>> threeSum(int[] nums) {

        if (null == nums || nums.length < 3) {
            return new ArrayList<>();
        }
        // 负数元素列表
        List<Integer> negativeList = new ArrayList<>();
        // 零元素列表
        List<Integer> zeroList = new ArrayList<>();
        // 正数元素列表
        List<Integer> positiveList = new ArrayList<>();

        Arrays.sort(nums);
        for (int num : nums) {
            if (0 == num) {
                zeroList.add(num);
            } else if (num > 0) {
                positiveList.add(num);
            } else {
                negativeList.add(num);
            }
        }
        // 负数元素去重Set列表
        Set<Integer> negativeSet = new HashSet<>(negativeList);
        // 正数元素去重Set列表
        Set<Integer> positiveSet = new HashSet<>(positiveList);

        List<List<Integer>> resultList = new ArrayList<>();

        // 1、三个零的情况
        if (zeroList.size() >= 3) {
            resultList.add(Arrays.asList(0, 0, 0));
        }
        // 2、一正数一负数一个零的情况
        if (negativeSet.size() > 0 && positiveSet.size() > 0 && zeroList.size() > 0) {
            for (Integer negative : negativeSet) {
                // 优化为Set集合判断是否存在相反数来判断
                if (positiveSet.contains(-negative)) {
                    List<Integer> list = Arrays.asList(negative, 0, -negative);
                    resultList.add(list);
                }
            }
        }
        // 3、两个正数一个负数的情况
        if (negativeSet.size() >= 1 && positiveList.size() >= 2) {
            for (Integer negative : negativeSet) {
                int startIndex = 0;
                int endIndex = positiveList.size() - 1;
                while (startIndex < endIndex) {
                    int value = positiveList.get(startIndex) + positiveList.get(endIndex);
                    if (value > -negative) {
                        endIndex--;
                    } else if (value < -negative) {
                        startIndex++;
                    } else {
                        List<Integer> list = Arrays.asList(negative, positiveList.get(startIndex), positiveList.get(endIndex));
                        // 将!resultList.contains(list)优化为通过自动缩短双向指针的下标的做法
                        resultList.add(list);
                        startIndex++;
                        endIndex--;
                        // 下标索引已经自增操作了，需要连续的自增只能判断startIndex和startIndex - 1的值，否则跳跃过大，无法正常缩短双向指针下标
                        while (startIndex < endIndex && positiveList.get(startIndex).equals(positiveList.get(startIndex - 1))) {
                            startIndex++;
                        }
                        while (startIndex < endIndex && positiveList.get(endIndex).equals(positiveList.get(endIndex + 1))) {
                            endIndex--;
                        }
                    }
                }
            }
        }
        // 4、两个负数一个正数的情况
        if (negativeList.size() >= 2 && positiveSet.size() >= 1) {
            for (Integer positive : positiveSet) {
                int startIndex = 0;
                int endIndex = negativeList.size() - 1;
                while (startIndex < endIndex) {
                    int value = negativeList.get(startIndex) + negativeList.get(endIndex);
                    if (-value > positive) {
                        startIndex++;
                    } else if (-value < positive) {
                        endIndex--;
                    } else {
                        List<Integer> list = Arrays.asList(negativeList.get(startIndex), negativeList.get(endIndex), positive);
                        resultList.add(list);
                        startIndex++;
                        endIndex--;
                        // 下标索引已经自增操作了，需要连续的自增只能判断startIndex和startIndex - 1的值，否则跳跃过大，无法正常缩短双向指针下标
                        while (startIndex < endIndex && negativeList.get(startIndex).equals(negativeList.get(startIndex - 1))) {
                            startIndex++;
                        }
                        while (startIndex < endIndex && negativeList.get(endIndex).equals(negativeList.get(endIndex + 1))) {
                            endIndex--;
                        }
                    }
                }
            }
        }
        return resultList;
    }
}
