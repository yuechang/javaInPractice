/* Copyright(c) 2022 Yue Chang and/or its affiliates. All right reserved. */
package com.yc.utils.easyexcel;

import com.alibaba.excel.support.ExcelTypeEnum;
import org.junit.Test;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Yue Chang
 * @ClassName: EasyExcelUtilsTest
 * @Description: 工具测试类
 * @date 2019/8/13 23:51
 */
public class EasyExcelUtilsTest {

    @Test
    public void writeExcel() throws FileNotFoundException {
        List<ExcelModel> excelModelList = new ArrayList<>();
        for (int i = 0; i < 1000000; i++) {
            ExcelModel excelModel = new ExcelModel();
            excelModel.setAddress("address" + i);
            excelModel.setAge(i + "");
            excelModel.setEmail("email" + i);
            excelModel.setHeigh("heigh" + i);
            excelModel.setLast("last" + i);
            excelModel.setName("name" + i);
            excelModel.setSax("sax" + i);
            excelModelList.add(excelModel);
        }

        long beginTime = System.currentTimeMillis();
        OutputStream out = new FileOutputStream("D:/aaa.xlsx");
        EasyExcelUtils.writeExcelWithModel(out, excelModelList, ExcelModel.class, ExcelTypeEnum.XLSX);
        long endTime = System.currentTimeMillis();
        System.out.println(String.format("总共耗时 %s 毫秒", (endTime - beginTime)));

        excelModelList = null;
    }

    @Test
    public void readExcel() throws FileNotFoundException {
        try {
            InputStream inputStream=new FileInputStream("D:/aaa.xlsx");
            //读入文件，每一行对应一个 Model，获取 Model 列表
            List<ExcelModel> objectList = EasyExcelUtils.readExcelWithModel(inputStream, ExcelModel.class, true);
            for(ExcelModel excelModel: objectList) {
                System.out.println(excelModel);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

