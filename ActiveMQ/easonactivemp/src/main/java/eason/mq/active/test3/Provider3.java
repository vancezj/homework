package eason.mq.active.test3;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @Auther: Eason
 * @Date: 2020/8/17 - 08 - 17 - 3:11
 * @Description: 测试多种数据类型 超时消息 死信队列
 * @version: 1.0
 */
public class Provider3 {

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
        MessageProducer producer2 = session.createProducer(queue);

        // send text
        TextMessage textMessage = session.createTextMessage("hello Eason... index");
        producer.send(textMessage);

        // send 对象
        Girl lili = new Girl("lili", 22, 398.00);
        ObjectMessage objectMessage = session.createObjectMessage(lili);
        producer.send(objectMessage);

        // send 字节类 - 图标
        BytesMessage bytesMessage = session.createBytesMessage();
        bytesMessage.writeUTF("Hi UTF~~!");
        bytesMessage.writeBytes("test test test".getBytes());
        producer.send(bytesMessage);

        // send Map
        MapMessage mapMessage = session.createMapMessage();
        mapMessage.setString("name", "xiaoqin");
        mapMessage.setInt("age", 21);
        mapMessage.setDouble("price", 298.0);
        producer.send(mapMessage);

        // 测试消息超时 - 死信队列
        // 只有持久化的消息在过期后 会放置在死信队列
        // 不持久化的消息是不会存在死信队列中的，会被丢弃
        MapMessage mapMessage2 = session.createMapMessage();
        mapMessage2.setString("name", "shuangYY");
        mapMessage2.setInt("age", 28);
        mapMessage2.setDouble("price", 398.0);
        producer2.setTimeToLive(1000);
        // producer2.setDeliveryMode(DeliveryMode.NON_PERSISTENT);
        producer2.send(mapMessage2);

        // 关闭连接
        connection.close();
        System.out.println(">>>>> send finish >>>>>");
    }
}
