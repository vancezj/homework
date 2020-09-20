package com.eason.test8;

import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.MessageQueueSelector;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageQueue;

import java.util.List;

/**
 * @Auther: Eason
 * @Date: 2020/8/28 - 08 - 28 - 18:34
 * @Description:
 * @version: 1.0 TAG 分组消息
 */
public class Producer2 {
    public static void main(String[] args) throws Exception {
        DefaultMQProducer producer = new DefaultMQProducer("EasonGroup03");
        producer.setNamesrvAddr("192.168.79.130:9876");
        producer.start();
        System.out.println("Producer 连接开启...");
        for (int i = 1; i <= 10; i++) {
            Message message = new Message("myTopic04", "TAG-A", ("send Producer1 msg -->" + i).getBytes());
            // message --> 要发的那条消息
            // MessageQueueSelector --> queue 选择器，向topic中的哪个queue去写消息
            // arg --> 自定义参数
            producer.send(message, new MessageQueueSelector() {
                // 手动 选择一个queue
                // mqs --> 当前topic 里面包含的所有queue
                // msg --> 具体要发的那条消息
                // arg --> 自定义参数 对应到 send() 里的 args
                public MessageQueue select(List<MessageQueue> mqs, Message msg, Object arg) {
                    // 向固定的一个queue里写消息
                    MessageQueue queue = mqs.get(0);
                    // 选好的 queue
                    return queue;
                }
            },0, 2000);
        }
        producer.shutdown();
        System.out.println("Producer 连接关闭...");
    }
}
