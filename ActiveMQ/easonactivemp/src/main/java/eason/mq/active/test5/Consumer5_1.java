package eason.mq.active.test5;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

/**
 * @Auther: Eason
 * @Date: 2020/8/17 - 08 - 17 - 3:11
 * @Description: 测试分组消息发送和消费
 * @version: 1.0
 */
public class Consumer5_1 {
    public static void main(String[] args) throws Exception{
        // 获取连接工厂
        ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory();
        connectionFactory.setPassword(ActiveMQConnectionFactory.DEFAULT_USER);
        connectionFactory.setPassword(ActiveMQConnectionFactory.DEFAULT_PASSWORD);
        connectionFactory.setBrokerURL("tcp://localhost:61616");

        // 创建 connection
        Connection connection = connectionFactory.createConnection();
        connection.start();
        System.out.println(">>>>> Consumer5_1 Started >>>>>");
        // 获取 session
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        // 测试 分组消费
        Destination queue = session.createQueue("user");
        // 测试分组消费
        MessageConsumer consumer = session.createConsumer(queue, "week=1");
        // 接收消息
        consumer.setMessageListener(new MessageListener() {
            @Override
            public void onMessage(Message message) {
                if (message instanceof MapMessage) {
                    MapMessage mapMessage = (MapMessage) message;
                    try {
                        System.out.println(">>> mapMessage = " + mapMessage.toString());
                        System.out.println(">>> mapMessage --> name = " + mapMessage.getString("name"));
                        System.out.println(">>> mapMessage --> age = " + mapMessage.getInt("age"));
                        System.out.println(">>> mapMessage --> price = " + mapMessage.getDouble("price"));
                    } catch (JMSException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }
}
