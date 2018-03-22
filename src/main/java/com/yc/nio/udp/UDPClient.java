/*!
 * Copyright(c) 2017 Yue Chang
 * MIT Licensed
 */
package com.yc.nio.udp;


import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.DatagramChannel;
import java.nio.channels.SelectableChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.charset.Charset;
import java.util.Iterator;

/**
 * @author Yue Chang
 * @ClassName: UDPClient
 * @Description: UDP 客户端
 * @date 2018/3/22 15:23
 */
public class UDPClient {

    DatagramChannel channel;
    Selector selector;

    public void work(int port){

        try {
            // 开启一个通道
            channel = DatagramChannel.open();
            // 设置为非阻塞
            channel.configureBlocking(false);

            InetSocketAddress address = new InetSocketAddress("localhost", port);

            channel.connect(address);
        } catch (Exception e){
            e.printStackTrace();
        }

        try {
            selector = Selector.open();
            channel.register(selector, SelectionKey.OP_READ);
            channel.write(Charset.defaultCharset().encode("data from client"));

        } catch (Exception e){
            e.printStackTrace();
        }

        ByteBuffer byteBuffer = ByteBuffer.allocate(100);
        while (true){

            try {
                if (selector.select() > 0){
                    Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
                    while (iterator.hasNext()){
                        SelectionKey key = iterator.next();

                        iterator.remove();
                        if (key.isReadable()){
                            channel = (DatagramChannel) key.channel();
                            // 清理已接收数据
                            byteBuffer.clear();
                            // 接收数据
                            channel.read(byteBuffer);

                            System.out.println(new String(byteBuffer.array()));

                            byteBuffer.clear();

                            channel.write(Charset.defaultCharset().encode("data from client "));
                        }
                    }
                }
            } catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        new UDPClient().work(8080);
    }
}
