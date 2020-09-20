package eason.mq.active.test2;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

/**
 * @Auther: Eason
 * @Date: 2020/8/17 - 08 - 17 - 3:11
 * @Description: 增加事务测试
 * @version: 1.0
 */
public class Provider2 {

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
        Session session = connection.createSession(true, Session.SESSION_TRANSACTED);
        // 创建 destination 消息的目的地
        Queue queue = session.createQueue("user");
        // 创建消息的创建者
        MessageProducer producer = session.createProducer(queue);

        for(int i = 0 ; i < 10; i++) {
            // 创建消息
            TextMessage textMessage = session.createTextMessage("hello Eason... index = " + i);
            // 发送消息
            // producer.setPriority(8);
            producer.setTimeToLive(1000 * 10);
            // Thread.sleep(1000);
            producer.send(textMessage);
            session.commit();
        }
        // 关闭连接
        connection.close();
    }
}
