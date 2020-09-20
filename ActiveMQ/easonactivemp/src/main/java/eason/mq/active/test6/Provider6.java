package eason.mq.active.test6;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.ScheduledMessage;

import javax.jms.*;

/**
 * @Auther: Eason
 * @Date: 2020/8/17 - 08 - 17 - 3:11
 * @Description: 基本测试
 * @version: 1.0
 */
public class Provider6 {

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
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        // 创建 destination 消息的目的地
        Queue queue = session.createQueue("delayuser");
        // 创建消息的创建者
        MessageProducer producer = session.createProducer(queue);

        // send text
        TextMessage textMessage = session.createTextMessage("hello ShuangYY...");
        long delay = 5 * 1000;
        long period = 2 * 1000;
        int repeat = 9;
        textMessage.setLongProperty(ScheduledMessage.AMQ_SCHEDULED_DELAY, delay);
        textMessage.setLongProperty(ScheduledMessage.AMQ_SCHEDULED_PERIOD, period);
        textMessage.setIntProperty(ScheduledMessage.AMQ_SCHEDULED_REPEAT, repeat);
        producer.send(textMessage);

        // 关闭连接
        System.out.println(">>> Active MQ >>> 7 --> close connection >>>");
        connection.close();
    }
}
