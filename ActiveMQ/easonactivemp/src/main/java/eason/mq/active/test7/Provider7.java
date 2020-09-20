package eason.mq.active.test7;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.ScheduledMessage;
import org.apache.activemq.command.ActiveMQQueue;

import javax.jms.*;

/**
 * @Auther: Eason
 * @Date: 2020/8/17 - 08 - 17 - 3:11
 * @Description: 基本测试
 * @version: 1.0
 */
public class Provider7 {
    public static void main(String[] args) throws Exception{
        // 获取连接工厂
        ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory();
        connectionFactory.setPassword(ActiveMQConnectionFactory.DEFAULT_USER);
        connectionFactory.setPassword(ActiveMQConnectionFactory.DEFAULT_PASSWORD);
        connectionFactory.setBrokerURL("tcp://localhost:61616");
        // 创建 connection
        Connection connection = connectionFactory.createConnection();
        connection.start();
        final Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        // Test JMSReplyTo

        //消息发送到这个Queue
        Queue queue = new ActiveMQQueue("testQueue");
        //消息回复到这个Queue
        Queue replyQueue = new ActiveMQQueue("replyQueue");

        // 创建消息的创建者
        MessageProducer producer = session.createProducer(queue);
        // 设置消息的保存模式：
        producer.setDeliveryMode( DeliveryMode.PERSISTENT);

        Message message = session.createTextMessage("Shuang.");
        message.setJMSReplyTo(replyQueue);
        producer.send(message);
        System.out.println(  "生产消息已发送成功。");

        //这个用来接收回复的消息
        MessageConsumer consumer = session.createConsumer(replyQueue);
        consumer.setMessageListener(new MessageListener(){
            public void onMessage(Message message) {
                if (message instanceof TextMessage) {
                    TextMessage textMessage = (TextMessage) message;
                    try {
                        System.out.println("收到接受者的回复信息>>>> = " + textMessage.getText());
                    } catch (JMSException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }
}
