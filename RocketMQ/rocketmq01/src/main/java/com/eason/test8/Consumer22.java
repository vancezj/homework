package com.eason.test8;

import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeOrderlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeOrderlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerOrderly;
import org.apache.rocketmq.common.message.MessageExt;

import java.util.List;

/**
 * @Auther: Eason
 * @Date: 2020/8/28 - 08 - 28 - 18:34
 * @Description: com.eason.test1
 * @version: 1.0
 */
public class Consumer22 {
    public static void main(String[] args) throws Exception {
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer("EasonCsmGroup03");
        consumer.setNamesrvAddr("192.168.79.130:9876");
        consumer.subscribe("myTopic04", "*");
        // 最大开启消费线程数
        consumer.setConsumeThreadMax(5);
        // 最小线程数
        consumer.setConsumeThreadMin(3);
        // 顺序消费, 对一个queue开启一个线程，多个queue开多个线程
        consumer.registerMessageListener(new MessageListenerOrderly() {
            @Override
            public ConsumeOrderlyStatus consumeMessage(List<MessageExt> msgs, ConsumeOrderlyContext context) {
                for (MessageExt ext: msgs) {
                    String threadId = Thread.currentThread().getName();
                    int queueId = ext.getQueueId();
                    String msgId = ext.getMsgId();
                    String body = new String(ext.getBody());
                    System.out.println("Thread = " + threadId + "||queueId = " + queueId +
                            "|| msgId = " + msgId + "|| body = " + body);
                }
                return ConsumeOrderlyStatus.SUCCESS;
            }
        });
        consumer.start();
        System.out.println("Consumer 连接开启...");
    }
}
