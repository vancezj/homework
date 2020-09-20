package com.eason.test7;

import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.message.MessageExt;

import java.util.List;

/**
 * @Auther: Eason
 * @Date: 2020/8/31 - 08 - 31 - 21:21
 * @Description: com.eason.test7
 * @version: 1.0
 */
public class RetryConsumer {
    public static void main(String[] args) throws MQClientException {
        DefaultMQPushConsumer consumer=new DefaultMQPushConsumer("rmq-group");
        consumer.setNamesrvAddr("192.168.79.130:9876");
        consumer.setInstanceName("consumer");
        consumer.subscribe("Retry-Topic","TagA");
        //监听消息
        consumer.registerMessageListener(new MessageListenerConcurrently() {
            @Override
            public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> list,
                                                            ConsumeConcurrentlyContext consumeConcurrentlyContext) {
                //获取消息
                for (MessageExt messageExt:list){
                    System.out.println(messageExt.getMsgId() + " --- " + new String(messageExt.getBody()));
                }
                try {
                    //模拟错误
                    int i = 5/0;
                }catch (Exception e){
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

