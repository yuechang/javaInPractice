/* Copyright(c) 2022 Yue Chang and/or its affiliates. All right reserved. */
package com.yc.nio.buffer;

import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.CharBuffer;

/**
 * @author Yue Chang
 * @ClassName: BufferCharView
 * @Description: 视图缓冲区 字符流与字节流的转换
 * @date 2018/3/21 16:42
 */
public class BufferCharView {

    public static void main(String[] args) {

        ByteBuffer byteBuffer = ByteBuffer.allocate(7)
                .order(ByteOrder.BIG_ENDIAN);
        CharBuffer charBuffer = byteBuffer.asCharBuffer();

        byteBuffer.put(0,(byte)0);
        byteBuffer.put(1,(byte) 'H');
        byteBuffer.put(2,(byte)0);
        byteBuffer.put(3,(byte) 'i');
        byteBuffer.put(4,(byte)0);
        byteBuffer.put(5,(byte) '!');
        byteBuffer.put(6,(byte)0);

        println(byteBuffer);
        println(charBuffer);
    }

    private static void println(Buffer buffer) {
        System.out.println("position:" + buffer.position()
                + ",limit:" + buffer.limit()
                + ",capacity:" + buffer.capacity()
                + ",:'" + buffer.toString() + "'");
    }
}
