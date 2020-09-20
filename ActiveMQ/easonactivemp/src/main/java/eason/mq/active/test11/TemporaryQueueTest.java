package eason.mq.active.test11;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.command.ActiveMQQueue;

import javax.jms.*;

/**
 * @Auther: Eason
 * @Date: 2020/8/25 - 08 - 25 - 19:31
 * @Description: eason.mq.active.test11
 * @version: 1.0
 */
public class TemporaryQueueTest {
    public static void main(String[] args) throws Exception {
        String url = "tcp://localhost:61616";
        String user = ActiveMQConnectionFactory.DEFAULT_USER;
        String password = ActiveMQConnectionFactory.DEFAULT_PASSWORD;
        ActiveMQConnectionFactory factory = new ActiveMQConnectionFactory(user, password, url);
        Connection connection = factory.createConnection();
        connection.start();

        final Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        Queue queue = new ActiveMQQueue("testQueue2");

        // 使用session创建一个TemporaryQueue。
        TemporaryQueue replyQueue = session.createTemporaryQueue();

        // 接收消息，并回复到指定的Queue中（即replyQueue）
        MessageConsumer comsumer = session.createConsumer(queue);
        comsumer.setMessageListener(new MessageListener() {
            public void onMessage(Message m) {
                try {
                    System.out.println("session Get Message: " + ((TextMessage) m).getText());
                    MessageProducer producer = session.createProducer(m.getJMSReplyTo());
                    producer.send(session.createTextMessage("ReplyMessage"));
                } catch (JMSException e) {
                }
            }
        });

        // 使用同一个Connection创建另一个Session，来读取replyQueue上的消息。
        Session session2 = connection.createSession(true, Session.AUTO_ACKNOWLEDGE);
        MessageConsumer replyComsumer = session2.createConsumer(replyQueue);
        replyComsumer.setMessageListener(new MessageListener() {
            public void onMessage(Message m) {
                try {
                    System.out.println("session2 Get reply: " + ((TextMessage) m).getText());
                } catch (JMSException e) {
                }
            }
        });

        // 消息发送
        MessageProducer producer = session.createProducer(queue);
        TextMessage message = session.createTextMessage("SimpleMessage");
        message.setJMSReplyTo(replyQueue);
        producer.send(message);
    }
}
