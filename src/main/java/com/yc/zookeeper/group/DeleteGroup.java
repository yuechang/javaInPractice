/* Copyright(c) 2022 Yue Chang and/or its affiliates. All right reserved. */
package com.yc.zookeeper.group;

import org.apache.zookeeper.KeeperException;

import java.io.IOException;
import java.util.List;

/**
 * @author Yue Chang
 * @ClassName: DeleteGroup
 * @Description: zookeeper删除组实例
 * @date 2018/3/28 23:05
 */
public class DeleteGroup extends ConnectionWatcher{

    /**
     * 输入znode的path和版本号（version number）来删除想要删除的znode，版本设置为-1，忽略版本检查
     * @param groupName
     * @throws KeeperException
     * @throws InterruptedException
     */
    public void delete(String groupName) throws KeeperException, InterruptedException {

        String path = "/" + groupName;
        try {

            List<String> children = zooKeeper.getChildren(path, false);
            if (null == children) {
                return;
            }

            // 删除一个znode之前，我们需要先删除它的子节点
            for (String child : children){
                zooKeeper.delete(path + "/" + child,-1);
                System.out.println("delete node :" +  path + "/" + child + " done ");
            }
            zooKeeper.delete(path,-1);
            System.out.println("delete node :" +  path +  " done ");
        } catch (KeeperException.NoNodeException e){
            System.out.printf("Group %s does not exist\n",groupName);
            System.exit(1);
        }
    }

    public static void main(String[] args) throws IOException, InterruptedException, KeeperException {

        DeleteGroup deleteGroup = new DeleteGroup();
        deleteGroup.connect(args[0]);
        deleteGroup.delete(args[1]);
        deleteGroup.close();
    }
}