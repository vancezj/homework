package eason.mq.active.test9;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

/**
 * @Auther: Eason
 * @Date: 2020/8/24 - 08 - 24 - 23:09
 * @Description: eason.mq.active.test9
 * @version: 1.0
 */
public class SenderQueueCorrelationID {
    public static void main(String[] args) throws Exception {
        // 1.获取连接工厂
        ActiveMQConnectionFactory factory = new ActiveMQConnectionFactory("vm://localhost?marshal=false&broker.persistent=false");
        Connection connection = factory.createConnection();
        connection.start();
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        Queue queue = session.createQueue("xxoo");
        MessageProducer producer = session.createProducer(queue);
        // 发送一条消息给指定消费者
        Message message = session.createTextMessage("Message from ServerA xxx" );
        // 粒度 细 筛选
        message.setJMSCorrelationID("xiaomovie");
        producer.send(message);
        System.out.println("System exit....");
    }
}
