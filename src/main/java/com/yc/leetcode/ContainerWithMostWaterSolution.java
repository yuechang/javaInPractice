/* Copyright © 2020 Yuech and/or its affiliates. All rights reserved. */
package com.yc.leetcode;

/**
 * 盛最多水的容器
 * container-with-most-water
 * https://leetcode-cn.com/problems/container-with-most-water/
 *
 * @author Yue Chang
 * @version 1.0
 * @date 2021-02-22 7:55 下午
 */
public class ContainerWithMostWaterSolution {

    public static void main(String[] args) {

        ContainerWithMostWaterSolution instance = new ContainerWithMostWaterSolution();
        int area = instance.maxArea(new int[]{1, 2, 1});
        System.out.println(area);

        area = instance.maxArea(new int[]{1,8,6,2,5,4,8,3,7});
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
        for (int i = 0; i < height.length; i++) {
            int tempMaxArea = 0;
            for (int j = i + 1; j < height.length; j++) {

                int area = (Math.min(height[j], height[i])) * (j - i);
                if (area > tempMaxArea) {
                    tempMaxArea = area;
                }
            }
            if (tempMaxArea > maxArea) {
                maxArea = tempMaxArea;
            }
        }
        return maxArea;
    }
}
