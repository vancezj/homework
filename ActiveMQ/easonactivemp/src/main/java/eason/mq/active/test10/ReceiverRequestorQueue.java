package eason.mq.active.test10;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.command.ActiveMQQueue;

import javax.jms.*;

/**
 * @Auther: Eason
 * @Date: 2020/8/24 - 08 - 24 - 23:13
 * @Description: eason.mq.active.test10
 * @version: 1.0
 */
public class ReceiverRequestorQueue {
    public static void main(String[] args) throws Exception{
        String url = "tcp://localhost:61616";
        String user = ActiveMQConnectionFactory.DEFAULT_USER;
        String password = ActiveMQConnectionFactory.DEFAULT_PASSWORD;
        ActiveMQConnectionFactory factory = new ActiveMQConnectionFactory(user, password, url);
        QueueConnection connection = factory.createQueueConnection();
        connection.start();

        System.out.println("ReceiverQueue-1 Started");
        // 3.获取session
        final Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        // 4. 找目的地，获取destination，消费端，也会从这个目的地取消息
        MessageConsumer consumer = session.createConsumer(new ActiveMQQueue("xxoo"));
        consumer.setMessageListener(new MessageListener() {
            public void onMessage(Message message) {
                try {
                    TextMessage textMessage = (TextMessage) message;
                    System.out.println("接到一条消息 >>>>> " + textMessage.getText());
                    System.out.println("开始发送确认消息.....");
                    Destination replyTo = message.getJMSReplyTo();
                    System.out.println("replyTo:" + replyTo);
                    MessageProducer producer = session.createProducer(replyTo);
                    producer.send(session.createTextMessage("replyTo xxxx..."));

                } catch (JMSException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
