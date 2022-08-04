/* Copyright(c) 2022 Yue Chang and/or its affiliates. All right reserved. */
package com.yc.zookeeper.group;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.ZooDefs;

import java.io.IOException;

/**
 * @author Yue Chang
 * @ClassName: JoinGroup
 * @Description: zookeeper加入组实例
 * @date 2018/3/28 22:10
 */
public class JoinGroup extends ConnectionWatcher{

    public void join(String groupName,String memberName) throws KeeperException, InterruptedException {

        String path = "/" + groupName + "/" + memberName;
        String createPath = zooKeeper.create(path,null, ZooDefs.Ids.OPEN_ACL_UNSAFE,
                CreateMode.EPHEMERAL);
        System.out.println("Created:" + createPath);

    }

    public static void main(String[] args) throws IOException, InterruptedException, KeeperException {

        JoinGroup joinGroup = new JoinGroup();
        joinGroup.connect(args[0]);
        joinGroup.join(args[1],args[2]);

        // 让进程一直存在着
        Thread.sleep(Long.MAX_VALUE);
    }

}

