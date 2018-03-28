/*!
 * Copyright(c) 2017 Yue Chang
 * MIT Licensed
 */
package com.yc.zookeeper.group;

import org.apache.zookeeper.KeeperException;

import java.io.IOException;
import java.util.List;

/**
 * @author Yue Chang
 * @ClassName: ListGroup
 * @Description: zookeeper组节点列表
 * @date 2018/3/28 22:19
 */
public class ListGroup extends ConnectionWatcher {

    public void list(String groupName) throws KeeperException, InterruptedException {

        String path = "/" + groupName;
        try {

            List<String> children = zooKeeper.getChildren(path, false);
            if (children.isEmpty()){
                System.out.printf("No member in group group %s\n",groupName);
                System.exit(1);
            }
            for (String child:children){
                System.out.println(child);
            }
        }catch (KeeperException.NoNodeException e){
            System.out.printf("Group %s does not exits\n",groupName);
            System.exit(1);
        }
    }

    public static void main(String[] args) throws IOException, InterruptedException, KeeperException {
        ListGroup listGroup = new ListGroup();
        listGroup.connect(args[0]);
        listGroup.list(args[1]);
        listGroup.close();
    }
}

