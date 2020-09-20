package eason.mq.active.test12;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;
import java.util.Properties;

/**
 * @Auther: Eason
 * @Date: 2020/8/17 - 08 - 17 - 3:11
 * @Description: 基本测试
 * @version: 1.0
 */
public class Consumer6 {
    public static void main(String[] args) throws Exception{
        // 获取连接工厂
        ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory();
        connectionFactory.setPassword(ActiveMQConnectionFactory.DEFAULT_USER);
        connectionFactory.setPassword(ActiveMQConnectionFactory.DEFAULT_PASSWORD);
        connectionFactory.setBrokerURL("tcp://localhost:61616");

        Properties props = new Properties();
        props.setProperty("prefetchPolicy.queuePrefetch", "1000");
        props.setProperty("prefetchPolicy.queueBrowserPrefetch", "500");
        props.setProperty("prefetchPolicy.durableTopicPrefetch", "100");
        props.setProperty("prefetchPolicy.topicPrefetch", "32766");

        connectionFactory.setProperties(props);
        // 创建 connection
        Connection connection = connectionFactory.createConnection();
        connection.start();
        // 获取 session
        Session session = connection.createSession(false, Session.CLIENT_ACKNOWLEDGE);
        // 创建 destination 消息的目的地
        Queue queue = session.createQueue("xxoo");
        // 创建消息的消费者
        MessageConsumer consumer = session.createConsumer(queue);
        // 接收消息
        consumer.setMessageListener(new MessageListener() {
            @Override
            public void onMessage(Message message) {
                if (message instanceof TextMessage) {
                    TextMessage textMessage = (TextMessage) message;
                    try {
                        // float f = 1 / 0;
                        System.out.println("Receive Message --> " + textMessage.getText());
                        textMessage.acknowledge();
                    } catch (JMSException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }
}
