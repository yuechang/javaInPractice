/*!
 * Copyright(c) 2017 Yue Chang
 * MIT Licensed
 */
package com.yc.zookeeper.application.distributedLock;

/**
 * @author Yue Chang
 * @ClassName: LockNode
 * @Description: 锁节点类
 * @date 2018/4/1 16:28
 */
public class LockNode implements Comparable{

    private String name;

    public LockNode(){}

    public LockNode(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    /**
     * 根据字符串排序，从而获得有序的各个节点
     *
     * @param object 比较对象
     * @return 比较结果
     */
    @Override
    public int compareTo(Object object) {

        if (object instanceof LockNode){
            LockNode lockNode = (LockNode) object;
            return this.name.compareTo(lockNode.getName());
        }
        return 0;
    }
}

