/* Copyright(c) 2022 Yue Chang and/or its affiliates. All right reserved. */
package com.yc.base.sort;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * @author Yue Chang
 * @ClassName: ExternalSort
 * @Description: 外部排序
 * @date 2018/9/20 9:44
 */
public class ExternalSort {

    public static final String FILE_PATH = "D:/sort";

    public static void main(String[] args) throws IOException{

        // 准备数据
        prepareFile();
        // 外部排序
        externalSort();
    }

    /**
     * 准备数据
     * 文件不是很大，也就107M左右，当然我们可以修改numCount来让这个文件变得更大。
     * @throws IOException
     */
    public static void prepareFile() throws IOException {

        File file=new File(FILE_PATH.concat("/source.txt"));

        int numCount=10000000;
        Random r=new Random();
        if(file.exists()) {
            file.delete();
        }
        FileWriter fw = new FileWriter(file);
        try {
            for(int i = 0; i < numCount; i++) {
                fw.write(r.nextInt()+"\n");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            fw.close();
        }
    }

    private static void externalSort() throws IOException {
        File file = new File(FILE_PATH.concat("/source.txt"));
        BufferedReader fr = new BufferedReader(new FileReader(file));//源数据文件读取。
        final int SIZE = 10000;//这里是定义我们将源文件中以10000条记录作为单位进行分割。
        int[] nums = new int[SIZE];//临时存放分割时的记录
        List<String> fileNames = new ArrayList<>();//保存所有分割文件的名称
        int index = 0;
        while(true) {
            String num = fr.readLine();//从原文件中读取一条记录
            if(num == null){//如果读取完毕后，进行一次排序并保存
                fileNames.add(sortAndSave(nums,index));
                break;
            }
            nums[index] = Integer.valueOf(num);
            index++;
            if(index == SIZE) {//当nums里面读的数字到达长度边界时，排序，存储
                fileNames.add(sortAndSave(nums,index));//sortAndSave是将nums中前index条记录先快速排序，然后存入文件，最好将文件名返回。
                index = 0;//重置index
            }
        }
        fr.close();
        mergeSort(fileNames);//将所有fileNames的文件进行合并
    }

    /**
     * sortAndSave是将nums中前index条记录先快速排序，然后存入文件，最好将文件名返回
     * @param nums
     * @param size
     * @return
     * @throws IOException
     */
    public static String sortAndSave(int[] nums, int size) throws IOException{
        QuickSort.sort(nums,0, size-1);
        String fileName = FILE_PATH.concat("/sort").concat(System.nanoTime()+"").concat(".txt");
        File rf = new File(fileName);
        BufferedWriter bw = new BufferedWriter(new FileWriter(rf));
        for(int i = 0; i < nums.length; i++) {
            bw.write(nums[i]+ "\n");
        }
        bw.close();
        return fileName;
    }

    /**
     * 合并排序
     * @param fileNames
     * @throws IOException
     */
    public static void mergeSort(List<String> fileNames) throws IOException{
        List<String> tempFileNames = new ArrayList<>();
        for(int i = 0; i < fileNames.size(); i++){
            String resultFileName = FILE_PATH.concat("/sort").concat(System.nanoTime()+"").concat(".txt");
            File resultFile = new File(resultFileName);
            tempFileNames.add(resultFileName);
            BufferedWriter bw = new BufferedWriter(new FileWriter(resultFile));

            File file1 = new File(fileNames.get(i++));
            BufferedReader br1 = new BufferedReader(new FileReader(file1));
            if(i < fileNames.size()){
                File file2 = new File(fileNames.get(i));
                BufferedReader br2 = new BufferedReader(new FileReader(file2));
                int num1 = 0;
                int num2 = 0;
                boolean isFirst = true;
                boolean firstNext = true;
                String numVal1 = null, numVal2 = null;
                // 归并file1和file2
                for(;;){
                    // 如果file1和file2均为头次取数据
                    if(isFirst) {
                        numVal1 = br1.readLine();
                        numVal2 = br2.readLine();
                        num1 = Integer.valueOf(numVal1);
                        num2 = Integer.valueOf(numVal2);
                        isFirst = false;
                    }
                    // 从file1里面取新数据
                    else if(firstNext) {
                        numVal1 = br1.readLine();
                    }
                    // 从file2里面取新数据
                    else {
                        numVal2 = br2.readLine();
                    }

                    // 两个文件中均有值
                    if(numVal1 != null && numVal2 != null){

                        // 根据标志位转换对应的数据
                        if(firstNext){
                            num1 = Integer.valueOf(numVal1);
                        }else{
                            num2 = Integer.valueOf(numVal2);
                        }
                        // 比较数据并写入小的数值，并设置标志位
                        if(num1 < num2){
                            bw.write(num1+"\n");
                            firstNext = true;
                        }else{
                            bw.write(num2+"\n");
                            firstNext = false;
                        }
                    } else{ //如果只剩下一个文件中有值
                        if(numVal1 != null) {
                            bw.write(numVal1+"\n");
                        }
                        if(numVal2 != null) {
                            bw.write(numVal2+"\n");
                        }
                        break;
                    }
                }
                // 如果file2中还存在数据，将数据写入结果集中
                while(true){
                    numVal2 = br2.readLine();;
                    if(numVal2 != null) {
                        bw.write(numVal2+"\n");
                    } else {
                        break;
                    }
                }
                br2.close();
                file2.delete();
            }
            // 如果file1中还存在数据，将数据写入结果集中
            while(true){
                String numVal1 = br1.readLine();
                if(numVal1 != null){
                    bw.write(numVal1+"\n");
                }
                else {
                    break;
                }
            }
            br1.close();
            file1.delete();
            bw.close();
        }

        int size = tempFileNames.size();
        // 如果还存在多个文件，则继续合并
        if(size > 1) {
            mergeSort(tempFileNames);
        }else if (size == 1) {
            File file = new File(tempFileNames.get(0));
            file.renameTo(new File(FILE_PATH.concat("/result.txt")));
        }
    }
}