package com.eason.test5;

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
        // 默认的 group -> MixAll.DEFAULT_PRODUCER_GROUP, 必须要设置group
        DefaultMQProducer producer = new DefaultMQProducer("EasonGroup03");

        // Producer 不可以直接连接 Borker, 是根据 name server 指定
        producer.setNamesrvAddr("192.168.79.130:9876");

        producer.start();
        System.out.println("Producer 连接开启...");
        // topic 消息发送的地址
        // body 消息体
        // for (int i = 1; i <= 100; i++) {
            Message message = new Message("myTopic03", "TAG-B","KEY-BB", ("xxooxx:setDelayTimeLevel 3").getBytes());
            // message.putUserProperty("age", String.valueOf(1));
            message.setDelayTimeLevel(4);
            producer.send(message);
        //}
        producer.shutdown();
        System.out.println("Producer 连接关闭...");
    }
}
