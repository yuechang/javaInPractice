/*!
 * Copyright(c) 2017 Yue Chang
 * MIT Licensed
 */
package com.yc.zookeeper.application.serverList;

import org.apache.zookeeper.*;

import java.io.IOException;

/**
 * @author Yue Chang
 * @ClassName: AppServer
 * @Description: App服务器
 * @date 2018/4/3 16:35
 */
public class AppServer {

    // 客户端连接服务器session的超时时间
    private static final int SESSION_TIMEOUT = 5000;

    private final String groupNode = "sgroup";
    private final String subNode = "sub";

    /**
     * 连接zookeeper
     * @param hosts
     * @param data
     * @throws IOException
     * @throws KeeperException
     * @throws InterruptedException
     */
    public void connectZookeeper(String hosts, String data) throws IOException, KeeperException, InterruptedException {

        ZooKeeper zooKeeper = new ZooKeeper(hosts, SESSION_TIMEOUT, new Watcher() {
            public void process(WatchedEvent watchedEvent) {
                // 不做处理
            }
        });

        if (null == zooKeeper.exists("/" + groupNode + "/" + subNode, false)){
            // 在"/sgroup"下创建子节点
            // 子节点类型设置为EPHEMERAL_SEQUENTIAL,表明这是一个临时节点，且在子节点的名称后面加上一串数字后缀
            // 将server的地址数据关联到新创建的子节点上
            String createPath = zooKeeper.create("/" + groupNode + "/" + subNode, data.getBytes("utf-8"),
                    ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL_SEQUENTIAL);
            System.out.println("createPath:" + createPath);
        }
    }

    /**
     * server的工作逻辑写在这个方法里面
     * 此处不做任何处理，只让server sleep
     * @throws InterruptedException
     */
    public void handle() throws InterruptedException {
        Thread.sleep(Long.MAX_VALUE);
    }

    public static void main(String[] args) throws InterruptedException, IOException, KeeperException {

        // 参数中输入
        if (args.length == 0){
            System.err.println("请输入地址以及数据信息");
            System.exit(1);
        }
        AppServer appServer = new AppServer();
        appServer.connectZookeeper(args[0],args[1]);
        appServer.handle();
    }




}
