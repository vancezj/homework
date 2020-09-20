package com.eason.test7;

import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.message.MessageExt;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Auther: Eason
 * @Date: 2020/8/31 - 08 - 31 - 21:36
 * @Description: com.eason.test7
 * @version: 1.0
 */
public class IdempotentConsumer {
    private static Map<String, Object> logMap = new HashMap<>();

    public static void main(String[] args) throws MQClientException {
        DefaultMQPushConsumer consumer=new DefaultMQPushConsumer("rmq-group");
            consumer.setNamesrvAddr("192.168.79.130:9876");
        consumer.setInstanceName("consumer");
        consumer.subscribe("Retry-Topic","TagA");

        //监听消息
        consumer.registerMessageListener(new MessageListenerConcurrently() {
            @Override
            public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> list, ConsumeConcurrentlyContext consumeConcurrentlyContext) {
                String key = null;
                String msgId = null;

                for (MessageExt messageExt : list) {
                    key = messageExt.getKeys();
                    if (logMap.containsKey(key)) {
                        System.out.println("key: " + key + ",已经消费，无需重试...");
                        return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
                    }
                    // RocketMQ由于是集群环境，所以产生的消息ID可能会重复
                    msgId = messageExt.getMsgId();
                    System.out.println("key:" + key + ",msgid:" + msgId + "---" + new String(messageExt.getBody()));
                    //将当前key保存在redis中
                    logMap.put(messageExt.getKeys(), messageExt);
                }
                try {
                    int i=5/0;
                } catch (Exception e) {
                    e.printStackTrace();
                    return ConsumeConcurrentlyStatus.RECONSUME_LATER;
                }
                return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
            }
        });
        //启动消费者
        consumer.start();
        System.out.println("Consumer Started!");
    }
}
