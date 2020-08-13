/*!
 * Copyright(c) 2017 Yue Chang
 * MIT Licensed
 */
package com.yc.zookeeper.application.configurationService;

import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

/**
 * @author Yue Chang
 * @ClassName: ConfigWatcher
 * @Description: 配置服务 观察配置变化
 * @date 2018/3/31 12:00
 */
public class ConfigWatcher implements Watcher {

    private ActiveKeyValueStore store;

    public ConfigWatcher(String hosts) throws IOException, InterruptedException {
        store = new ActiveKeyValueStore();
        store.connect(hosts);
    }

    public void displayConfig() throws InterruptedException, UnsupportedEncodingException, KeeperException {

        String value = store.read(ConfigUpdater.PATH, this);
        System.out.printf("Read %s as %s \n",ConfigUpdater.PATH,value);
    }

    @Override
    public void process(WatchedEvent watchedEvent) {

        if (watchedEvent.getType() == Event.EventType.NodeDataChanged){

            try {
                displayConfig();
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            } catch (InterruptedException e){
                System.err.println("Interrupted. Exiting");
                Thread.currentThread().interrupt();
            } catch (KeeperException e){
                System.err.printf("KeeperException: %s. Exiting.\n",e);
            }
        }
    }


    public static void main(String[] args) throws IOException, InterruptedException, KeeperException {
        ConfigWatcher configWatcher = new ConfigWatcher(args[0]);
        configWatcher.displayConfig();

        Thread.sleep(Long.MAX_VALUE);
    }
}
