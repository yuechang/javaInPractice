/*!
 * Copyright(c) 2017 Yue Chang
 * MIT Licensed
 */
package com.yc.concurrency.simplePool;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @author Yue Chang
 * @ClassName: SimpleHttpServer
 * @Description: 基于线程池技术的简单Web服务器
 * @date 2018/4/27 10:25
 */
public class SimpleHttpServer {

    // 处理HttpRequest的线程池
    static ThreadPool<HttpRequestHandler> threadPool = new DefaultThreadPool<HttpRequestHandler>(1);
    // SimpleHttpServer的跟地址
    static String basePath;
    static ServerSocket serverSocket;
    // 服务监听端口
    static int port = 8080;

    public static void setBasePath(String basePath) {
        if (basePath != null && new File(basePath).exists() && new File(basePath).isDirectory()){
            SimpleHttpServer.basePath = basePath;
        }
    }

    public static void setPort(int port) {
        if (port > 0){
            SimpleHttpServer.port = port;
        }
    }

    // 启动SimpleHttpServer
    public static void start() throws IOException {
        serverSocket = new ServerSocket(port);
        Socket socket = null;
        while ((socket = serverSocket.accept()) != null) {
            // 接收一个客户端Socket，生成一个HttpRequestHandler，放入线程池执行
            threadPool.execute(new HttpRequestHandler(socket));
        }
        serverSocket.close();
    }


    static class HttpRequestHandler implements Runnable{

        private Socket socket;

        public HttpRequestHandler(Socket socket) {
            this.socket = socket;
        }

        @Override
        public void run() {

            String line = null;
            BufferedReader br = null;
            BufferedReader reader = null;
            PrintWriter out = null;
            InputStream in = null;
            try {
                reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                String header = reader.readLine();
                // 由相对路径计算出绝对路径
                String filePath = basePath + header.split(" ")[1];
                out = new PrintWriter(socket.getOutputStream());
                // 如果请求资源的后缀为jpg或者ico，则读取资源并输出
                if (filePath.endsWith("jpg") || filePath.endsWith("ico")){
                    in = new FileInputStream(filePath);
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    int i = 0;
                    while ((i = in.read()) != -1){
                        baos.write(i);
                    }
                    byte[] array = baos.toByteArray();

                    out.println("HTTP/1.1 200 OK");
                    out.println("Server: Molly");
                    out.println("Content-Type: image/jpeg");
                    out.println("Content-Length: " + array.length);
                    out.println("");

                    socket.getOutputStream().write(array, 0 ,array.length);
                } else {
                    br = new BufferedReader(new InputStreamReader(new FileInputStream(filePath)));
                    out = new PrintWriter(socket.getOutputStream());

                    out.println("HTTP/1.1 200 OK");
                    out.println("Server: Molly");
                    out.println("Content-Type: image/html; charset=UTF-8");
                    out.println("");
                    while ((line = br.readLine()) != null){
                        out.println(line);
                    }
                }
                out.flush();
            } catch (Exception e){
                out.println("HTTP/1.1 500");
                out.println("");
                out.flush();
            } finally {
                close(br, in, reader, out, socket);
            }
        }

        // 关闭流或者Socket
        private void close(Closeable... closeables) {
            if (closeables != null){
                for (Closeable closeable : closeables) {
                    try {
                        if (closeable != null) {
                            closeable.close();
                        }

                    } catch (Exception e){
                        e.printStackTrace();
                    }
                }
            }
        }

        public static void main(String[] args) throws IOException {
            SimpleHttpServer.setBasePath("C:\\Users\\Administrator\\Pictures\\");
            SimpleHttpServer.start();
        }
    }
}
/*
，随着线程池中线程数量的增加，SimpleHttpServer的吞吐量不断增大，响应时间
不断变小，线程池的作用非常明显。

但是，线程池中线程数量并不是越多越好，具体的数量需要评估每个任务的处理时间，以
及当前计算机的处理器能力和数量。使用的线程过少，无法发挥处理器的性能；使用的线程过
多，将会增加系统的无故开销，起到相反的作用。


 */