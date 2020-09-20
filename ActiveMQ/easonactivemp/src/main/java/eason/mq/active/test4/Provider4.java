package eason.mq.active.test4;

import eason.mq.active.test3.Girl;
import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

/**
 * @Auther: Eason
 * @Date: 2020/8/17 - 08 - 17 - 3:11
 * @Description: 测试独占消费者
 * @version: 1.0
 */
public class Provider4 {

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
        Destination queue = session.createQueue("user");
        // 创建消息的创建者
        MessageProducer producer = session.createProducer(queue);

        for (int i = 0 ; i < 100; i++) {
            // send Map
            MapMessage mapMessage = session.createMapMessage();
            mapMessage.setString("name", "shuangYY");
            mapMessage.setInt("age", 21);
            mapMessage.setDouble("price", 298.0);
            producer.send(mapMessage);
            Thread.sleep(1000);
        }
        // 关闭连接
        connection.close();
        System.out.println(">>>>> send finish >>>>>");
    }
}
