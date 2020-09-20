package eason.mq.active.test8;

import org.apache.activemq.*;
import org.apache.activemq.command.ActiveMQTopic;

import javax.jms.*;
import javax.jms.Message;

/**
 * @Auther: Eason
 * @Date: 2020/8/24 - 08 - 24 - 21:53
 * @Description: eason.mq.active.test8
 * @version: 1.0
 */
public class TopicTest {
    public static void main(String[] args) throws Exception {
        ActiveMQConnectionFactory factory = new ActiveMQConnectionFactory("vm://localhost?marshal=false&broker.persistent=false");
        Connection connection = factory.createConnection();
        connection.start();
        //创建一个Topic
        Topic topic= new ActiveMQTopic("testTopic");
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

        //注册消费者1
        MessageConsumer comsumer1 = session.createConsumer(topic);
        comsumer1.setMessageListener(new MessageListener(){
            public void onMessage(Message m) {
                try {
                    System.out.println("Consumer1 get " + ((TextMessage)m).getText());

                } catch (JMSException e) {
                    e.printStackTrace();
                }
            }
        });
        //注册消费者2
        MessageConsumer comsumer2 = session.createConsumer(topic);
        comsumer2.setMessageListener(new MessageListener(){
            public void onMessage(Message m) {
                try {
                    System.out.println("Consumer2 get " + ((TextMessage)m).getText());
                } catch (JMSException e) {
                    e.printStackTrace();
                }
            }
        });
        //创建一个生产者，然后发送多个消息。
        MessageProducer producer = session.createProducer(topic);
        for(int i=0; i < 10; i++) {
            producer.send(session.createTextMessage("Message:" + i));
        }
    }
}
