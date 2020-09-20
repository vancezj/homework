package com.eason.zookeeper.configcenter;

import org.apache.zookeeper.ZKUtil;
import org.apache.zookeeper.ZooKeeper;

/**
 * @Auther: Eason
 * @Date: 2020/8/16 - 08 - 16 - 23:57
 * @Description: com.eason.zookeeper.configcenter
 * @version: 1.0
 */
public class TestZK {
    private ZooKeeper zooKeeper;
    private ZKConfig zkConfig;
    private ZKUtils zkUtils;
    private ZKWatch zkWatch;

    public TestZK() {
        String address = "192.168.79.130:2181/testNode,192.168.79.131:2181/testNode,192.168.79.132:2181/testNode,192.168.79.133:2181/testNode";
        zkConfig = new ZKConfig();
        zkConfig.setAddress(address);
        zkConfig.setSessionTimeout(5000);

        zkWatch = new ZKWatch();
        zkUtils = new ZKUtils();
        zkUtils.setZkConfig(zkConfig);
        zkUtils.setZkWatch(zkWatch);
    }

    public void testZKConnection() throws Exception {
        zooKeeper = zkUtils.connection();
        byte[] testNode = zooKeeper.getData("/testNode", false, null);
        System.out.println(">>>>> /testNode Data >>>>>> " + new String(testNode));
    }

    public static void main(String[] args) throws Exception{
        TestZK testZK = new TestZK();
        testZK.testZKConnection();
        Thread.sleep(100000);
    }
}
