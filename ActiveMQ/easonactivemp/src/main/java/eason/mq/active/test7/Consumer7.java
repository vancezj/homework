package eason.mq.active.test7;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.command.ActiveMQQueue;

import javax.jms.*;

/**
 * @Auther: Eason
 * @Date: 2020/8/17 - 08 - 17 - 3:11
 * @Description: 基本测试
 * @version: 1.0
 */
public class Consumer7 {
    public static void main(String[] args) throws Exception{
        // 获取连接工厂
        ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory();
        connectionFactory.setPassword(ActiveMQConnectionFactory.DEFAULT_USER);
        connectionFactory.setPassword(ActiveMQConnectionFactory.DEFAULT_PASSWORD);
        connectionFactory.setBrokerURL("tcp://localhost:61616");

        // 创建 connection
        Connection connection = connectionFactory.createConnection();
        connection.start();
        // 获取 session
        final Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        // 创建 destination 消息的目的地
        Queue queue = new ActiveMQQueue("testQueue");
        // 创建消息的消费者
        MessageConsumer comsumer = session.createConsumer(queue);
        // 接收消息
        comsumer.setMessageListener(new MessageListener() {
            @Override
            public void onMessage(Message message) {
                if (message instanceof TextMessage) {
                    TextMessage textMessage = (TextMessage) message;
                    try {
                        System.out.println( "接收到的生产消息： " + textMessage.getText() );
                        System.out.println( "回复的目标地址：  " + message.getJMSReplyTo());
                        // 创建一个新的MessageProducer来发送一个回复消息。
                        MessageProducer producer = session.createProducer(message.getJMSReplyTo());
                        producer.send(session.createTextMessage("Hello " + textMessage.getText()));
                        System.out.println("回复生产者成功....");
                    } catch (JMSException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }
}
