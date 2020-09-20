package eason.mq.active.test1;

import org.apache.activemq.ActiveMQConnectionFactory;
import javax.jms.*;

/**
 * @Auther: Eason
 * @Date: 2020/8/17 - 08 - 17 - 3:11
 * @Description: 基本测试
 * @version: 1.0
 */
public class Provider1 {

    public static void main(String[] args) throws Exception{
        // 获取连接工厂
        ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory();
        connectionFactory.setPassword(ActiveMQConnectionFactory.DEFAULT_USER);
        connectionFactory.setPassword(ActiveMQConnectionFactory.DEFAULT_PASSWORD);
        connectionFactory.setBrokerURL("tcp://localhost:61616");
        // 创建 connection
        System.out.println(">>> Active MQ >>> 1 --> create connection >>>");
        Connection connection = connectionFactory.createConnection();
        connection.start();
        // 获取 session
        System.out.println(">>> Active MQ >>> 2 --> create session >>>");
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        // 创建 destination 消息的目的地
        System.out.println(">>> Active MQ >>> 3 --> create destination >>>");
        Queue queue = session.createQueue("user");
        // 创建消息的创建者
        System.out.println(">>> Active MQ >>> 4 --> create producer >>>");
        MessageProducer producer = session.createProducer(queue);
        for(int i = 0 ; i < 1000; i++) {
            // 创建消息
            System.out.println(">>> Active MQ >>> 5 --> create TextMessage >>>");
            TextMessage textMessage = session.createTextMessage("hello Eason... index = " + i);
            // 发送消息
            System.out.println(">>> Active MQ >>> 6 --> send TextMessage >>>");
            Thread.sleep(1000);
            producer.send(textMessage);
        }
        // 关闭连接
        System.out.println(">>> Active MQ >>> 7 --> close connection >>>");
        connection.close();
    }
}
