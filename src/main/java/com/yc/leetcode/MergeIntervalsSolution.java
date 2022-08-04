/* Copyright © 2020 Yuech and/or its affiliates. All rights reserved. */
package com.yc.leetcode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

/**
 * 合并区间
 * merge-intervals
 * https://leetcode-cn.com/problems/merge-intervals/
 *
 *
 * @author Yue Chang
 * @version 1.0
 * @date 2021-02-24 8:24 下午
 */
public class MergeIntervalsSolution {

    public static void main(String[] args) {

        MergeIntervalsSolution instance = new MergeIntervalsSolution();

        int[][] resultArr = instance.merge(new int[][]{{1, 4}, {0, 1}});
        System.out.println(Arrays.deepToString(resultArr));

        resultArr = instance.merge(new int[][]{{1, 3}, {2, 6}, {8, 10}, {15, 18}});
        System.out.println(Arrays.deepToString(resultArr));

    }

    /**
     * 合并区间
     *
     * @param intervals 区间数组
     * @return 合并后的区间信息
     */
    public int[][] merge(int[][] intervals) {

        if (null == intervals) {
            return new int[1][2];
        }
        if (1 == intervals.length) {
            return intervals;
        }
        List<int[]> resultList = new ArrayList<>();
        for (int[] interval : intervals) {
            // 过滤非法数据
            if (null == interval || interval.length != 2 || interval[0] > interval[1]) {
                continue;
            }
            // 初始化结果集，加入第一个区间
            if (resultList.size() <= 0) {
                resultList.add(new int[]{interval[0], interval[1]});
            } else {
                Iterator<int[]> iterator = resultList.iterator();
                List<int[]> tempResultList = new ArrayList<>();
                // 比较待比较区间与结果区间是否有重叠
                boolean overlappingFlag = false;
                while (iterator.hasNext()) {

                    // 结果区间值
                    int[] resultInterval = iterator.next();
                    int resultStartValue = resultInterval[0];
                    int resultEndValue = resultInterval[1];

                    // 待比较区间值
                    int startValue = interval[0];
                    int endValue = interval[1];

                    // 结果区间数组涵盖了整个待比较的区间,此时添加结果区间
                    if (resultStartValue <= startValue && startValue <= resultEndValue &&
                            resultStartValue <= endValue && endValue <= resultEndValue) {
                        overlappingFlag = true;
                        // 删除旧区间数据
                        iterator.remove();
                        // 加入新区间数据至临时列表
                        tempResultList.add(resultInterval);
                    }
                    // 待比较区间数组涵盖了整个的结果区间,此时添加待比较区间
                    else if (startValue <= resultStartValue && resultStartValue <= endValue &&
                            startValue <= resultEndValue && resultEndValue <= endValue) {
                        overlappingFlag = true;
                        // 删除旧区间数据
                        iterator.remove();
                        // 加入新区间数据至临时列表
                        tempResultList.add(interval);
                    }
                    // 待比较区间开始值处在结果区间内
                    else if (resultStartValue <= startValue && startValue <= resultEndValue) {

                        overlappingFlag = true;
                        // 获得区间结束值
                        int end = resultEndValue;
                        if (endValue > resultEndValue) {
                            end = endValue;
                        }
                        // 删除旧区间数据
                        iterator.remove();
                        // 加入新区间数据至临时列表
                        tempResultList.add(new int[]{resultStartValue, end});
                    }
                    // 结果区间开始值处在比较区间内
                    else if (startValue <= resultStartValue && resultStartValue <= endValue) {
                        overlappingFlag = true;
                        // 获得区间结束值
                        int end = endValue;
                        if (resultEndValue > endValue) {
                            end = resultEndValue;
                        }
                        // 删除旧区间数据
                        iterator.remove();
                        // 加入新区间数据至临时列表
                        tempResultList.add(new int[]{startValue, end});
                    }
                    //
                    else if (resultStartValue <= endValue && endValue <= resultEndValue) {
                        overlappingFlag = true;
                        // 获得区间结束值
                        int start = resultStartValue;
                        if (resultStartValue > startValue) {
                            start = startValue;
                        }
                        // 删除旧区间数据
                        iterator.remove();
                        // 加入新区间数据至临时列表
                        tempResultList.add(new int[]{start, resultEndValue});

                    }
                    //
                    else if (startValue <= resultEndValue && resultEndValue <= endValue) {
                        overlappingFlag = true;
                        // 获得区间结束值
                        int start = startValue;
                        if (start > resultStartValue) {
                            start = resultStartValue;
                        }
                        // 删除旧区间数据
                        iterator.remove();
                        // 加入新区间数据至临时列表
                        tempResultList.add(new int[]{start, endValue});

                    }


                }
                // 如果结果区间与待比较区间没有重叠，加入新区间数据至临时列表
                if (!overlappingFlag) {
                    tempResultList.add(new int[]{interval[0], interval[1]});
                }
                // 更新结果区间数据列表
                resultList.addAll(tempResultList);
            }
        }
        int[][] resultArr = new int[resultList.size()][2];
        resultArr = resultList.toArray(resultArr);
        return resultArr;
    }
}
