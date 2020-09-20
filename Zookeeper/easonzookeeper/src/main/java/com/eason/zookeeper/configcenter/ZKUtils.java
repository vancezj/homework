package com.eason.zookeeper.configcenter;

import org.apache.zookeeper.ZooKeeper;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

/**
 * @Auther: Eason
 * @Date: 2020/8/15 - 08 - 15 - 10:35
 * @Description: com.eason.zookeeper.configcenter
 * @version: 1.0
 */
public class ZKUtils {
    private ZooKeeper zk;
    private ZKConfig zkConfig;
    private ZKWatch zkWatch;
    private CountDownLatch cdl = new CountDownLatch(1);

    /**
     * ractive模式编程 练习
     * 创建连接成功时, 需要阻塞, 等待client访问
     */
    public ZooKeeper connection() {
        System.out.println(">>>>> ZKUtils >> start connection >>>>>>>");
        try {
            zk = new ZooKeeper(zkConfig.getAddress(), zkConfig.getSessionTimeout(), zkWatch);
            cdl.await();
            System.out.println(">>>>> ZKUtils >> waiting start >>>>>>>>");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            System.out.println(">>>>> ZKUtils >> finally connection >>>>>>>");
        }
        return zk;
    }

    public void close() {
        if (zk != null) {
            try {
                System.out.println(">>>>> ZKUtils >> close >>>>>>>");
                zk.close();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void setZkConfig(ZKConfig zkConfig) {
        this.zkConfig = zkConfig;
    }

    public void setZkWatch(ZKWatch zkWatch) {
        zkWatch.setCdl(cdl);
        this.zkWatch = zkWatch;
    }
}
