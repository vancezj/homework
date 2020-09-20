package com.eason.test2;

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

        // 消息类型是由consumer决定的
        // 默认是点对点, 只能被一个消费一次, 但是可以设置为广播模式
        // MessageModel.CLUSTERING ---> 点对点 消费状态是 borker 维护 因为有重投机制
        // MessageModel.BROADCASTING --> 广播 consumer 维护消费状态, 不会重投
        // consumer.setMessageModel(MessageModel.BROADCASTING);
        consumer.start();
        System.out.println("Consumer 连接开启...");
    }
}
