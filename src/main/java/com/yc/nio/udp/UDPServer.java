/* Copyright(c) 2022 Yue Chang and/or its affiliates. All right reserved. */
package com.yc.nio.udp;


import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.nio.channels.SelectableChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.util.Iterator;

/**
 * @author Yue Chang
 * @ClassName: UDPServer
 * @Description: UDP 服务器
 * @date 2018/3/22 14:45
 */
public class UDPServer {

    DatagramChannel channel;

    Selector selector;

    public void work(int port){

        try {
            // 打开一个UDP channel
            channel = DatagramChannel.open();
            // 设置为非阻塞通道
            channel.configureBlocking(false);
            // 绑定端口
            channel.socket().bind(new InetSocketAddress(port));
            // 打开一个选择器
            selector = Selector.open();
            // 给通道注册选择器
            channel.register(selector, SelectionKey.OP_READ);
        } catch (Exception e){
            e.printStackTrace();
        }

        ByteBuffer byteBuffer = ByteBuffer.allocate(65536);
        while (true){
            
            try {
                //进行选择
                if(selector.select() > 0){

                    //获取一选择的键的集合
                    Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();

                    while (iterator.hasNext()){

                        SelectionKey key = iterator.next();

                        //手动删除
                        iterator.remove();
                        if (key.isReadable()){
                            DatagramChannel datagramChannel = (DatagramChannel) key.channel();

                            byteBuffer.clear();
                            // 读取
                            InetSocketAddress address = (InetSocketAddress) datagramChannel.receive(byteBuffer);

                            System.out.println(new String(byteBuffer.array(),0,byteBuffer.position()));

                            // 删除缓存区中的数据
                            byteBuffer.clear();

                            String message = "data come from server";

                            byteBuffer.put(message.getBytes());

                            byteBuffer.flip();
                            // 发送数据
                            datagramChannel.send(byteBuffer,address);
                        }
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        new UDPServer().work(8080);
    }


}
