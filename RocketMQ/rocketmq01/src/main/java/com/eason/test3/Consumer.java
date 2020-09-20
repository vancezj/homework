package com.eason.test3;

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
public class Consumer {
    public static void main(String[] args) throws Exception {
        // 默认的 group -> MixAll.DEFAULT_PRODUCER_GROUP, 必须要设置group
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer("EasonCsmGroup01");
        // Producer 不可以直接连接 Borker, 是根据 name server 指定
        consumer.setNamesrvAddr("192.168.79.130:9876");

        // 每一个 Consumer 必须关注一个 topic
        // subExpression 表示过滤器, * 表示不过滤
        consumer.subscribe("myTopic01", "*");

        // 监听消息 也是阻塞方法
        consumer.registerMessageListener(new MessageListenerConcurrently() {
            @Override
            public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs, ConsumeConcurrentlyContext context) {
                // 消息为字节数组
                for (MessageExt ext: msgs) {
                    System.out.println("MsgId = " + ext.getMsgId());
                    System.out.println("Body = " + new String(ext.getBody()));
                }
                // 默认 一条消息会被一个 Consumer 消费 --> 点对点
                // 获取消息后需要做消息确认 ack
                return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
            }
        });

        consumer.setMessageModel(MessageModel.CLUSTERING);
        consumer.start();
        System.out.println("Consumer 连接开启...");
    }
}
