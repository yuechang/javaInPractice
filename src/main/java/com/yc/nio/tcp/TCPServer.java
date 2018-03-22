/*!
 * Copyright(c) 2017 Yue Chang
 * MIT Licensed
 */
package com.yc.nio.tcp;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

/**
 * @author Yue Chang
 * @ClassName: TCPServer
 * @Description: 服务器端
 * @date 2018/3/21 18:08
 */
public class TCPServer {

    public static void main(String[] args) throws IOException {
        // 通过open()方法找到Selector
        Selector selector = Selector.open();
        TCPServer server = new TCPServer(8080,selector);
        server.listen(selector);
    }

    // 接收和发送数据缓存区
    private ByteBuffer send = ByteBuffer.allocate(1024);
    private ByteBuffer receive = ByteBuffer.allocate(1024);

    public TCPServer(int port, Selector selector) throws IOException {

        // 打开服务器套接字通道
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        // 服务器配置为非阻塞
        serverSocketChannel.configureBlocking(false);
        // 检索与此通道关联的服务器套接字
        ServerSocket serverSocket = serverSocketChannel.socket();
        // 进行服务的绑定
        serverSocket.bind(new InetSocketAddress(port));

        // 注册到selector，等待连接
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
        System.out.println("Server start on port " + port );

        // 向发送缓存区加入数据
        send.put("data come from server".getBytes());
    }

    // 监听
    private void listen(Selector selector) throws IOException{
        while(true){

            // 选择一组键，并且相应的通道已经打开
            selector.select();
            // 返回此选择器的已选择的键集
            Set<SelectionKey> selectionKeySet = selector.selectedKeys();
            Iterator<SelectionKey> iterator = selectionKeySet.iterator();
            while (iterator.hasNext()){
                SelectionKey selectionKey = iterator.next();
                // 这里记得手动的把他remove掉，不然selector中的selectedKys集合不会自动去除
                iterator.remove();
                dealKey(selectionKey,selector);
            }
        }
    }

    // 处理请求
    private void dealKey(SelectionKey selectionKey,Selector selector) throws IOException {

        ServerSocketChannel server = null;
        SocketChannel client = null;

        // 测试此键的通道是否已准备好接受新的套接字连接
        if(selectionKey.isAcceptable()){

            // 返回位置创建此键的通道
            server = (ServerSocketChannel) selectionKey.channel();

            // 此方法返回的套接字通道(如果有)将处于阻塞模式
            client = server.accept();
            // 配置为非阻塞模式
            client.configureBlocking(false);
            // 注册到selector，等待连接
            client.register(selector,SelectionKey.OP_READ | SelectionKey.OP_WRITE);
        }else if(selectionKey.isReadable()){
            // 返回位置创建此键的通道
            client = (SocketChannel) selectionKey.channel();
            // 将缓存区清空以备下次读取
            receive.clear();
            // 读取服务器发送来的数据到缓存区
            client.read(receive);

            System.out.println(new String(receive.array()));

            selectionKey.interestOps(SelectionKey.OP_WRITE);
        }else if (selectionKey.isWritable()){

            // 将缓存区清空以备下次写入
            send.flip();
            // 返回位置创建此键的通道
            client  = (SocketChannel) selectionKey.channel();

            // 输出到通道
            client.write(send);
            // 服务端向客户端发送数据
            selectionKey.interestOps(SelectionKey.OP_READ);
        }
    }
}
