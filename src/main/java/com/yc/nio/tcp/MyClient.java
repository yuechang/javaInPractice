/*!
 * Copyright(c) 2017 Yue Chang
 * MIT Licensed
 */
package com.yc.nio.tcp;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author Yue Chang
 * @ClassName: MyClient
 * @Description: 客户端
 * @date 2018/3/22 9:25
 */
public class MyClient {

    public static void main(String[] args) throws IOException {

        final ExecutorService service = Executors.newCachedThreadPool();

        Runnable runnableClient1 = new Runnable() {
            public void run() {
                MyClient client = new MyClient();
                try {
                    client.work(8080," client1...");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        };

        Runnable runnableClient2 = new Runnable() {
            public void run() {
                MyClient client2 = new MyClient();
                try {
                    client2.work(8080,"client2...");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        };

        service.submit(runnableClient1);
        service.submit(runnableClient2);
    }

    public void work(int port,String message) throws IOException {

        SocketChannel sc = null;
        Selector selector = null;

        // 发送接收缓存区
        ByteBuffer send = ByteBuffer.wrap(message.getBytes());
        ByteBuffer receive = ByteBuffer.allocate(1024);

        try {
            sc = SocketChannel.open();
            selector = Selector.open();

            // 注册为非阻塞通道
            sc.configureBlocking(false);

            sc.connect(new InetSocketAddress("localhost",port));

            sc.register(selector, SelectionKey.OP_CONNECT | SelectionKey.OP_READ | SelectionKey.OP_WRITE);

        } catch (IOException e) {
            e.printStackTrace();
        }

        while(true){
            // 选择
            if(selector.select() == 0){
                continue;
            }
            Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
            while (iterator.hasNext()){

                SelectionKey key = iterator.next();

                // 必须有程序员手动操作
                iterator.remove();;

                sc = (SocketChannel) key.channel();

                if(key.isConnectable()){
                    // 结束连接，以完成整改连接过程
                    sc.finishConnect();
                    System.out.println("connect completely");
                    try {
                        sc.write(send);
                    } catch (IOException e){
                        e.printStackTrace();
                    }

                }else if (key.isReadable()){
                    try {
                        receive.clear();
                        sc.read(receive);
                        System.out.println(new String(receive.array()));
                    } catch (IOException e){
                        e.printStackTrace();
                    }
                }else if(key.isWritable()){
                    receive.flip();
                    try {
                        send.flip();
                        sc.write(send);
                    } catch (IOException e){
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}
