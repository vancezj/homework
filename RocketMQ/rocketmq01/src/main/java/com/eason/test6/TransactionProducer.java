package com.eason.test6;

import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.client.producer.TransactionMQProducer;
import org.apache.rocketmq.common.message.Message;

/**
 * @Auther: Eason
 * @Date: 2020/8/28 - 08 - 28 - 18:34
 * @Description:
 * @version: 1.0 selector 过滤消息
 */
public class TransactionProducer {
    public static void main(String[] args) throws Exception {
        TransactionMQProducer producer = new TransactionMQProducer("TXGroup001");
        producer.setNamesrvAddr("192.168.79.130:9876");
        producer.setTransactionListener(new MyTransactionListener());
        producer.start();

        System.out.println("Producer 连接开启...");
        for (int i = 1; i < 6; i++) {
            Message message = new Message("TXTopic001", "TXTopic001Test",
                    "TX001-msg-" + i, ("Hello XT 001" + ":" +  i).getBytes());
            try {
                SendResult result = producer.sendMessageInTransaction(message, "Hello XT arg" + ":" +  i);
                System.out.printf("Topic:%s send success, misId is:%s%n", message.getTopic(), result.getMsgId());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        Thread.sleep(Integer.MAX_VALUE);
        producer.shutdown();
        System.out.println("Producer 连接关闭...");
    }
}
