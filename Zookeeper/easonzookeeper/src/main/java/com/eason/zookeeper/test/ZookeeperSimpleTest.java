package com.eason.zookeeper.test;

import com.sun.org.glassfish.external.statistics.Stats;
import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;
import org.apache.zookeeper.proto.WatcherEvent;

import java.util.concurrent.CountDownLatch;

/**
 * @Auther: Eason
 * @Date: 2020/8/13 - 08 - 13 - 21:45
 * @Description: com.eason.zookeeper.test
 * @version: 1.0
 */
public class ZookeeperSimpleTest {

    /** zookeeper地址 */
    private static final String ipAddress = "192.168.79.130:2181,192.168.79.131:2181,192.168.79.132:2181,192.168.79.133:2181";
    /** session超时时间 */
    private static final int SESSION_OUTTIME = 30000;//ms
    /** 信号量，阻塞程序执行，用于等待zookeeper连接成功，发送成功信号 */
    private static final CountDownLatch countDown = new CountDownLatch(1);

    public static void main(String[] args) throws Exception {
        // new zk 时候，传入的watch，这个watch，session级别的，跟path 、node没有关系。
        ZooKeeper zk = new ZooKeeper(ipAddress, SESSION_OUTTIME, new Watcher() {
            @Override
            public void process(WatchedEvent event) {
                String path = event.getPath();
                // 获取事件的状态
                Event.KeeperState state = event.getState();
                Event.EventType type = event.getType();
                WatcherEvent wrapper = event.getWrapper();
                System.out.println("new zk watch ---> path = " + path);
                System.out.println("new zk watch ---> state = " + state.toString());
                System.out.println("new zk watch ---> type = " + type.toString());
                System.out.println("new zk watch:---> wrapper = " + wrapper.toString());
                System.out.println("new zk watch:---> event = " + event.toString());

                //如果是建立连接
                System.out.println(">>>>>>>> CONNECTING >>>>>> CONNECTING >>>>>>>>>");
                if (event.getState() == Event.KeeperState.SyncConnected) {
                    System.err.println("eventType:" + event.getType());
                    if (event.getType() == Event.EventType.None) {
                        // 如果建立连接成功，则发送信号量，让后续阻塞程序向下执行
                        countDown.countDown();
                        System.out.println(">>>>>>>> zk 建立连接");
                    } else if (event.getType() == Event.EventType.NodeCreated) {
                        System.out.println("listen:节点创建");
                    } else if (event.getType() == Event.EventType.NodeChildrenChanged) {
                        System.out.println("listen:子节点修改");
                    }
                }
            }
        });
        countDown.await();

        ZooKeeper.States state = zk.getState();
        if (state.equals(ZooKeeper.States.CONNECTING)) {
            System.out.println(">>>>>>> zk ing......");
        }
        if (state.equals(ZooKeeper.States.CONNECTED)) {
            System.out.println(">>>>>>> zk ed........");
        }

        //***********************************************************//
        // TEST 1
        //***********************************************************//

        // 同步创建
        String path = zk.create("/xxoo", "olddata".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL);
        System.out.println("create path >>>>> " + path);
        //Thread.sleep(5000);

        byte[] data1 = zk.getData("/xxoo", true, null);
        System.out.println("没有watch -- >get数据是:" + new String(data1));

        final Stat stat = new Stat();
        byte[] data2 = zk.getData("/xxoo", new Watcher() {
            @Override
            public void process(WatchedEvent event) {
                System.out.println("getData watch: " + event.toString());
                try {
                    // true --> default Watch 被重新注册(new zk 的那个 watch),** this --> 当前的 new Watcher
                    zk.getData("/xxoo",this, stat);
                } catch (KeeperException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, stat);

        //触发回调
        Stat stat1 = zk.setData("/xxoo", "newdata".getBytes(), -1);
        System.out.println("触发回调 1 stat1 >>>>> " + stat1.toString());
        System.out.println("触发回调 1 data2 >>>>> " + new String(data2));
        Thread.sleep(5000);

        //还会触发吗？
        Stat stat2 = zk.setData("/xxoo", "newdata01".getBytes(), stat1.getVersion());

        System.out.println("-------async start----------");
        zk.getData("/xxoo", false, new AsyncCallback.DataCallback() {
            @Override
            public void processResult(int rc, String path, Object ctx, byte[] data, Stat stat) {
                System.out.println("-------async call back----------");
                System.out.println("ctx >>>>> " + ctx.toString());
                System.out.println("data >>>> " + new String(data));
            }

        },"abc");
        System.out.println("触发回调 2 stat2 >>>>> " + stat2.toString());
        System.out.println("-------async over----------");

        System.out.println("-------sleep start----------");
        Thread.sleep(5000);
        System.out.println("-------sleep end ------------");


        //***********************************************************//
        // TEST 2
        //***********************************************************//
        //创建父节点
        zk.create("/testRoot", "testRoot".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);

        //创建子节点
        zk.create("/testRoot/children", "children data".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);

        //获取节点洗信息
        byte[] testData1 = zk.getData("/testRoot", false, null);
        System.out.println("testData1 >>>>>> " + new String(testData1));
        System.out.println(zk.getChildren("/testRoot", false));

        //修改节点的值
        zk.setData("/testRoot", "modify data root".getBytes(), -1);
        byte[] testData2 = zk.getData("/testRoot", false, null);
        System.out.println("testData2 >>>>>> " + new String(testData2));

        //判断节点是否存在
        System.out.println(zk.exists("/testRoot/children", false));

        //删除节点
        zk.delete("/testRoot/children", -1);
        zk.delete("/testRoot", -1);

        zk.close();
    }
}
