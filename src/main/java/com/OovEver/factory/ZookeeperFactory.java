package com.OovEver.factory;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;

/**
 * Zookeeper Client建立工厂
 * @author OovEver
 * 2018/7/9 1:06
 */
public class ZookeeperFactory {
//    zookeeper客户端建立
    public static CuratorFramework client;

    /**
     *
     * @return zookeeper client对象
     */
    public static CuratorFramework create() {
//        重试机制，1秒重试一次
        RetryPolicy retryPolicy = new ExponentialBackoffRetry(1000, 3);
        client = CuratorFrameworkFactory.newClient("localhost:2181", retryPolicy);
        client.start();
        return client;
    }

    /**
     * 测试程序 在zookeeper 建立/netty路径
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {
        CuratorFramework client = create();
        client.create().forPath("/netty");
    }
}
