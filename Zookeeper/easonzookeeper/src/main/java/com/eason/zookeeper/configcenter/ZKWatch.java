package com.eason.zookeeper.configcenter;

import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;

import java.util.concurrent.CountDownLatch;

/**
 * @Auther: Eason
 * @Date: 2020/8/15 - 08 - 15 - 10:43
 * @Description: com.eason.zookeeper.configcenter
 * @version: 1.0
 */

// 完成watcher回调函数，所有回调都是异步执行
public class ZKWatch implements Watcher {
    private CountDownLatch cdl;

    public CountDownLatch getCdl() {
        return cdl;
    }

    public void setCdl(CountDownLatch cdl) {
        this.cdl = cdl;
    }

    /**
     * 监控节点的回调函数
     * 这个锁是 创建连接时创建的。
     * 当 watcher 的 status 是 SyncConnected，释放锁
     * 当 watcher 的 status 是 失去连接时，阻塞
     * @param event
     */
    @Override
    public void process(WatchedEvent event) {
        System.out.println(">>>>> Wather Event >>>>>> " + event.toString());
        switch (event.getState()) {
            case SyncConnected:
                System.out.println(">>>>>> ZK Connected...is...ok...");
                this.cdl.countDown();
                break;
            case Disconnected:
                System.out.println(">>>>>> ZK Disconnect...is...ok...");
                break;
            case Expired:
                System.out.println(">>>>>> ZK Expired...is...ok...");
                break;
            case AuthFailed:
                System.out.println(">>>>>> ZK AuthFailed...is...ok...");
                break;
        }
    }
}
