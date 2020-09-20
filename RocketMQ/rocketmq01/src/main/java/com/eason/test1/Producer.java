package com.eason.test1;

import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageQueue;

import java.util.ArrayList;

/**
 * @Auther: Eason
 * @Date: 2020/8/28 - 08 - 28 - 18:34
 * @Description:
 * @version: 1.0 同步发送消息
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
        Message message = new Message("myTopic01", "first rocker mq message".getBytes());

        // send 是同步阻塞发送, 要等待 Borker 的自动确认,
        SendResult result1 = producer.send(message);
        System.out.println(result1);

        // 也可以发送 list
        // http://rocketmq.apache.org/docs/batch-example/
        ArrayList<Message> msgs = new ArrayList<>();
        Message message1 = new Message("myTopic01", "list 111 rocker mq message".getBytes());
        Message message2 = new Message("myTopic01", "list 222 rocker mq message".getBytes());
        Message message3 = new Message("myTopic01", "list 333 rocker mq message".getBytes());
        msgs.add(message1);
        msgs.add(message2);
        msgs.add(message3);

        MessageQueue mq = new MessageQueue("myTopic01","borker-a",0);
        SendResult result2 = producer.send(msgs, mq);
        System.out.println(result2);

        // 关闭连接, 如果不关闭就是阻塞运行
        producer.shutdown();
        System.out.println("Producer 连接关闭...");
    }
}
