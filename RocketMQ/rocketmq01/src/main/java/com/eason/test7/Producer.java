package com.eason.test7;

import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.common.message.Message;

/**
 * @Auther: Eason
 * @Date: 2020/8/28 - 08 - 28 - 18:34
 * @Description:
 * @version: 1.0 selector 过滤消息
 */
public class Producer {
    public static void main(String[] args) throws Exception {
        DefaultMQProducer producer = new DefaultMQProducer("rmq-group");
        producer.setNamesrvAddr("192.168.79.130:9876");
        producer.start();
        System.out.println("Producer 连接开启...");
        Message message = new Message("Retry-Topic", "TagA", "Retry Producer".getBytes());
        producer.send(message);
        // producer.shutdown();
        System.out.println("Producer 连接关闭...");
    }
}
