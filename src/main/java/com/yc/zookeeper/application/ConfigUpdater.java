/*!
 * Copyright(c) 2017 Yue Chang
 * MIT Licensed
 */
package com.yc.zookeeper.application;

import org.apache.zookeeper.KeeperException;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * @author Yue Chang
 * @ClassName: ConfigUpdater
 * @Description: 配置服务更新配置信息
 * @date 2018/3/31 11:52
 */
public class ConfigUpdater {

    public static final String PATH = "/config";
    private ActiveKeyValueStore store;
    private Random random = new Random();


    public ConfigUpdater(String hosts) throws IOException, InterruptedException {

        store = new ActiveKeyValueStore();
        store.connect(hosts);
    }

    public void run() throws InterruptedException, UnsupportedEncodingException, KeeperException {

        while (true){
            String value = random.nextInt(100) + "";
            store.write(PATH,value);
            System.out.printf("Set %s to %s\n", PATH, value);
            TimeUnit.SECONDS.sleep(random.nextInt(10));
        }
    }

    public static void main(String[] args) throws IOException, InterruptedException, KeeperException {

        while (true){
            try{
                ConfigUpdater configUpdater = new ConfigUpdater(args[0]);
                configUpdater.run();
            } catch (KeeperException.SessionExpiredException e){
                e.printStackTrace();
            } catch (KeeperException e){
                e.printStackTrace();
                break;
            }
        }
    }
}
