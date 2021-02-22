/* Copyright © 2020 Yuech and/or its affiliates. All rights reserved. */
package com.yc.leetcode;

/**
 * 盛最多水的容器(双指针法)
 * container-with-most-water
 * https://leetcode-cn.com/problems/container-with-most-water/
 *
 * @author Yue Chang
 * @version 1.0
 * @date 2021-02-22 7:56 下午
 */
public class ContainerWithMostWaterSolution2 {

    public static void main(String[] args) {

        ContainerWithMostWaterSolution2 instance = new ContainerWithMostWaterSolution2();
        int area = instance.maxArea(new int[]{1, 2, 1});
        System.out.println(area);

        area = instance.maxArea(new int[]{1, 8, 6, 2, 5, 4, 8, 3, 7});
        System.out.println(area);

    }

    /**
     * 盛最多水的容器
     *
     * @param height 高度
     * @return 最多的水
     */
    public int maxArea(int[] height) {

        int maxArea = 0;
        int startIndex = 0;
        int endIndex = height.length - 1;
        while (startIndex <= endIndex) {

            int area = Math.min(height[startIndex], height[endIndex]) * (endIndex - startIndex);
            if (area > maxArea) {
                maxArea = area;
            }
            if (height[startIndex] > height[endIndex]) {
                endIndex--;
            } else {
                startIndex++;
            }
        }
        return maxArea;
    }
}
