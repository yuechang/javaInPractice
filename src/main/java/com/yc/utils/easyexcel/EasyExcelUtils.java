/* Copyright(c) 2022 Yue Chang and/or its affiliates. All right reserved. */
package com.yc.utils.easyexcel;

import com.alibaba.excel.ExcelReader;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.excel.metadata.BaseRowModel;
import com.alibaba.excel.metadata.Sheet;
import com.alibaba.excel.metadata.Table;
import com.alibaba.excel.support.ExcelTypeEnum;
import org.junit.Test;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Yue Chang
 * @ClassName: EasyExcelUtils
 * @Description: Excel工具类
 * @date 2019/8/13 22:49
 */
public class EasyExcelUtils {

    /**
     * StringList 解析监听器
     */
    private static class StringExcelListener extends AnalysisEventListener {
        /**
         * 自定义用于暂时存储data
         * 可以通过实例获取该值
         */
        private List<List<String>> datas = new ArrayList<>();

        /**
         * 每解析一行都会回调invoke()方法
         *
         * @param object
         * @param context
         */
        @Override
        public void invoke(Object object, AnalysisContext context) {
            List<String> stringList= (List<String>) object;
            //数据存储到list，供批量处理，或后续自己业务逻辑处理。
            datas.add(stringList);
            //根据自己业务做处理
        }

        @Override
        public void doAfterAllAnalysed(AnalysisContext context) {
            //解析结束销毁不用的资源
            //注意不要调用datas.clear(),否则getDatas为null
        }

        public List<List<String>> getDatas() {
            return datas;
        }
        public void setDatas(List<List<String>> datas) {
            this.datas = datas;
        }
    }

    /**
     * 用 StringList 来读取Excel
     * @param inputStream Excel的输入流
     * @param trim 是否去除首尾空格
     * @return 返回 StringList 的列表
     */
    public static List<List<String>> readExcelWithStringList(InputStream inputStream, boolean trim) {
        StringExcelListener listener = new StringExcelListener();
        ExcelReader excelReader = new ExcelReader(inputStream, null, listener, trim);
        excelReader.read();
        return  listener.getDatas();
    }

    /**
     * 使用 StringList 来写入Excel
     * @param outputStream Excel的输出流
     * @param data 要写入的以StringList为单位的数据
     * @param table 配置Excel的表的属性
     * @param excelTypeEnum Excel的格式(XLS或XLSX)
     */
    public static void writeExcelWithStringList(OutputStream outputStream, List<List<String>> data, Table table, ExcelTypeEnum excelTypeEnum) {
        //这里指定不需要表头，因为String通常表头已被包含在data里
        ExcelWriter writer = new ExcelWriter(outputStream, excelTypeEnum,false);
        //写第一个sheet, sheet1  数据全是List<String> 无模型映射关系,无表头
        Sheet sheet1 = new Sheet(0, 0);
        writer.write0(data, sheet1,table);
        writer.finish();
    }

    /**
     * 模型解析监听器 -- 每解析一行会回调invoke()方法，整个excel解析结束会执行doAfterAllAnalysed()方法
     */
    private static class ModelExcelListener<E> extends AnalysisEventListener<E> {
        private List<E> dataList = new ArrayList<E>();

        @Override
        public void invoke(E object, AnalysisContext context) {
            dataList.add(object);
        }

        @Override
        public void doAfterAllAnalysed(AnalysisContext context) {
        }

        public List<E> getDataList() {
            return dataList;
        }

        @SuppressWarnings("unused")
        public void setDataList(List<E> dataList) {
            this.dataList = dataList;
        }
    }

    /**
     * 使用 模型 来读取Excel
     *
     * @param inputStream Excel的输入流
     * @param clazz 模型的类
     * @param trim 是否去除首尾空格
     *
     * @return 返回 模型 的列表
     */
    public static <E> List<E> readExcelWithModel(InputStream inputStream, Class<? extends BaseRowModel> clazz, Boolean trim) {
        // 解析每行结果在listener中处理
        ModelExcelListener<E> listener = new ModelExcelListener<E>();
        ExcelReader excelReader = new ExcelReader(inputStream, null, listener, trim);
        //默认只有一列表头
        excelReader.read(new Sheet(1, 1, clazz));

        return listener.getDataList();
    }

    /**
     * 使用 模型 来写入Excel
     *
     * @param outputStream Excel的输出流
     * @param data 要写入的以 模型 为单位的数据
     * @param clazz 模型的类
     * @param excelTypeEnum Excel的格式(XLS或XLSX)
     */
    public static void writeExcelWithModel(OutputStream outputStream, List<? extends BaseRowModel> data,
                                           Class<? extends BaseRowModel> clazz, ExcelTypeEnum excelTypeEnum)  {
        //这里指定需要表头，因为model通常包含表头信息
        ExcelWriter writer = new ExcelWriter(outputStream, excelTypeEnum,true);
        //写第一个sheet, sheet1  数据全是List<String> 无模型映射关系
        Sheet sheet1 = new Sheet(1, 0, clazz);
        writer.write(data, sheet1);
        writer.finish();
    }
}

