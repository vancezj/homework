package com.eason.test8;

import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.common.protocol.heartbeat.MessageModel;

import java.util.List;

/**
 * @Auther: Eason
 * @Date: 2020/8/28 - 08 - 28 - 18:34
 * @Description: com.eason.test1
 * @version: 1.0
 */
public class Consumer11 {
    public static void main(String[] args) throws Exception {
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer("EasonCsmGroup03");
        consumer.setNamesrvAddr("192.168.79.130:9876");
        consumer.subscribe("myTopic04", "*");
        // 并发消费 / 开多个线程
        consumer.registerMessageListener(new MessageListenerConcurrently() {
            @Override
            public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs, ConsumeConcurrentlyContext context) {
                for (MessageExt ext: msgs) {
                    String threadId = Thread.currentThread().getName();
                    int queueId = ext.getQueueId();
                    String msgId = ext.getMsgId();
                    String body = new String(ext.getBody());
                    System.out.println("Thread = " + threadId + "||queueId = " + queueId +
                            "|| msgId = " + msgId + "|| body = " + body);
                }
                return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
            }
        });
        consumer.start();
        System.out.println("Consumer 连接开启...");
    }
}
