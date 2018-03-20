/*!
 * Copyright(c) 2017 Yue Chang
 * MIT Licensed
 */
package com.yc.nio.buffer;

import java.nio.CharBuffer;

/**
 * @author Yue Chang
 * @ClassName: BufferFillDrain
 * @Description: TODO
 * @date 2018/3/20 21:56
 */
public class BufferFillDrain{

    private static int index = 0;

    private static String[] strings =
            { "A random string value", "The product of an infinite number of monkeys",
                    "Hey hey we're the Monkees",
                    "Opening act for the Monkees: Jimi Hendrix",
                    "'Scuse me while I kiss this fly", // Sorry Jimi ;-)
                    "Help Me!  Help Me!", };


    public static void main(String[] args) {
        CharBuffer buffer = CharBuffer.allocate(100);

        while (fillBuffer(buffer)){

            buffer.flip();
            //buffer.limit(buffer.position()).position(0);
            drainBuffer(buffer);
            buffer.clear();
        }
    }

    private static void drainBuffer(CharBuffer buffer) {
        while(buffer.hasRemaining()){
            System.out.print(buffer.get());
        }
        System.out.println("");
    }

    private static boolean fillBuffer(CharBuffer buffer) {

        if (index >= strings.length){
            return false;
        }
        String string = strings[index++];
        for (int i = 0; i < string.length(); i++){
            buffer.put(string.charAt(i));
        }
        return true;
    }
}

