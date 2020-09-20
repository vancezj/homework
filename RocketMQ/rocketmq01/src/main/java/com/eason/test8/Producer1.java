package com.eason.test8;

import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.common.message.Message;

/**
 * @Auther: Eason
 * @Date: 2020/8/28 - 08 - 28 - 18:34
 * @Description:
 * @version: 1.0 TAG 分组消息
 */
public class Producer1 {
    public static void main(String[] args) throws Exception {
        DefaultMQProducer producer = new DefaultMQProducer("EasonGroup03");
        producer.setNamesrvAddr("192.168.79.130:9876");
        producer.start();
        System.out.println("Producer 连接开启...");
        for (int i = 1; i <= 10; i++) {
            Message message = new Message("myTopic04", "TAG-A", ("send Producer1 msg -->" + i).getBytes());
            producer.send(message);
        }
        producer.shutdown();
        System.out.println("Producer 连接关闭...");
    }
}
