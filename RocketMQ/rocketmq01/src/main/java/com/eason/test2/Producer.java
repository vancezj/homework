package com.eason.test2;

import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;

import java.util.ArrayList;

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
        Message message = new Message("myTopic01", "second Async rocker mq message".getBytes());

        // 在异步模式下声明发送失败之前，内部执行的最大重试次数
        producer.setRetryTimesWhenSendAsyncFailed(3);
        // 异步可靠消息, 不会阻塞等待 Borker 确认
        // 采用事件监听机制
        producer.send(message, new SendCallback() {
            @Override
            public void onSuccess(SendResult sendResult) {
                System.out.println("消息发送成功.....");
                System.out.println(sendResult);
            }
            @Override
            public void onException(Throwable e) {
                // 如果发生异常, 可以尝试 try catch, 进行重新投递
                // 或者修改业务逻辑
                System.out.println("消息发送异常.....");
                e.printStackTrace();
            }
        });

        // 异步执行不可以关闭连接, shutdown 会在回调之前执行
        // producer.shutdown();
        System.out.println("Producer 连接关闭...");
    }
}
