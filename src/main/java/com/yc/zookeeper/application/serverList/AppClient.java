/*!
 * Copyright(c) 2017 Yue Chang
 * MIT Licensed
 */
package com.yc.zookeeper.application.serverList;

import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Yue Chang
 * @ClassName: AppClient
 * @Description: 客户端
 * @date 2018/4/3 19:47
 */
public class AppClient {

    // 客户端连接服务器session的超时时间
    private static final int SESSION_TIMEOUT = 5000;
    private String groupNode = "sgroup";
    private ZooKeeper zooKeeper;
    private Stat stat = new Stat();
    private volatile List<String> serverList;

    public void connectZookeeper(String hosts) throws Exception {

        zooKeeper = new ZooKeeper(hosts, SESSION_TIMEOUT, new Watcher() {

            @Override
            public void process(WatchedEvent watchedEvent) {
                String path = watchedEvent.getPath();
                System.out.println("path:" + path);

                // 如果发生了"sgroup"节点下的子节点变化事件，更新server列表，并重新注册监听
                if (watchedEvent.getType() == Event.EventType.NodeChildrenChanged
                        && ("/" + groupNode).equals(watchedEvent.getPath())){
                    try {
                        updateServerList();
                    } catch (Exception e){
                        e.printStackTrace();
                    }
                }
            }
        });
        updateServerList();
    }

    /**
     * 更新server列表
     * @throws Exception
     */
    private void updateServerList() throws Exception {
        List<String> newServerList = new ArrayList<String>();
        /*
        获取并监听groupNode的子节点变化
        watch参数为true，表示监听子节点变化事件
        每次都需要重新注册监听，因为一次注册，只能监听一次事件
        如果还想继续保持监听，必须重新注册
        获取每个子节点下关联的server地址
         */
        List<String> children = zooKeeper.getChildren("/" + groupNode, true);
        for (String child : children){
            // 获得子节点关联的服务器地址
            byte[] data = zooKeeper.getData("/" + groupNode + "/" + child,
                    false, stat);
            newServerList.add(new String(data,"utf-8"));
        }

        // 替换server列表
        serverList = newServerList;
        System.out.println("服务器列表已更新：" + serverList);
    }

    /**
     * client的工作逻辑写在这里
     * 这里只做sleep
     * @throws InterruptedException
     */
    public void handle() throws InterruptedException {
        Thread.sleep(Long.MAX_VALUE);
    }

    public static void main(String[] args) throws Exception {
        if (args.length == 0){
            System.err.println("请输入地址");
            System.exit(1);
        }
        AppClient appClient = new AppClient();
        appClient.connectZookeeper(args[0]);

        appClient.handle();
    }
}
