package com.eason.test3;

import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;

/**
 * @Auther: Eason
 * @Date: 2020/8/28 - 08 - 28 - 18:34
 * @Description:
 * @version: 1.0 异步发送消息
 */
public class Producer {
    public static void main(String[] args) throws Exception {
        // 默认的 group -> MixAll.DEFAULT_PRODUCER_GROUP, 必须要设置group
        DefaultMQProducer producer = new DefaultMQProducer("EasonGroup01");

        // Producer 不可以直接连接 Borker, 是根据 name server 指定
        producer.setNamesrvAddr("192.168.79.130:9876");
        producer.start();
        System.out.println("Producer 连接开启...");
        // topic 消息发送的地址
        // body 消息体
        Message message = new Message("myTopic01", "send sendOneway rocker mq message".getBytes());
        // 单向消息 不保证发送成功
        producer.sendOneway(message);
        // producer.shutdown();
        System.out.println("Producer 连接关闭...");
    }
}
