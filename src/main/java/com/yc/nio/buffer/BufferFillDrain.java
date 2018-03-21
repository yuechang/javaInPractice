/*!
 * Copyright(c) 2017 Yue Chang
 * MIT Licensed
 */
package com.yc.nio.buffer;

import java.nio.CharBuffer;

/**
 * @author Yue Chang
 * @ClassName: BufferFillDrain
 * @date 2018/3/20 21:56
 * @Description: 缓冲区基础演示
 *
### 1、缓冲区并不是多线程安全的。
### 2、属性（容量、上界、位置、标记）
 - capacity
 - limit  第一个不能被读或写的元素
 - position  下一个要被读或写的元素索引
 - mark   一个备忘位置
### 3、方法操作
#### 3.1、翻转
``` java
buffer.flip()     //等同于 buffer.limit(buffer.position()).position(0)
```
#### 3.2、释放
``` java
buffer.flip();
for(int i=0; buffer.hasRemaining(); i++)
｛
    myByteArray[i] = buffer.get();
｝
```
#### 3.3、压缩
释放一部分数据，而不是全部，然后重新填充。调用 compact()的作用是丢弃已经释放的数据，保留未释放的数据，并使缓冲区对重新填充容量准备就绪。
``` java
buffer.compact()
for(int i=0; buffer.hasRemaining(); i++)
｛
    afterBuffer[i] = buffer.get();
｝
```
这一缓冲区工具在复制数据时要比您使用 get()和 put()函数高效得多。

#### 3.4、标记、重置
mark()   标记position为备忘位置
reset()  重置position为备忘位置

#### 3.5、比较
 - 两个对象类型相同。包含不同数据类型的 buffer 永远不会相等，而且 buffer绝不会等于非buffer对象。
 - 两个对象都剩余同样数量的元素。Buffer 的容量不需要相同，而且缓冲区中剩余数据的索引也不必相同。但每个缓冲区中剩余元素的数目（从位置到上界）必须相同。
 - 在每个缓冲区中应被Get()函数返回的剩余数据元素序列必须一致。

#### 3.6、批量移动
一次移动一个数据元素，并不高效。buffer API 提供了向缓冲区内外批量移动数据元素的函数。
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
            // buffer.limit(buffer.position()).position(0);
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

