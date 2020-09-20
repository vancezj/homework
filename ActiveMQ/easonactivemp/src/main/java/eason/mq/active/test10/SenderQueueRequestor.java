package eason.mq.active.test10;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

/**
 * @Auther: Eason
 * @Date: 2020/8/24 - 08 - 24 - 23:13
 * @Description: eason.mq.active.test10
 * @version: 1.0
 */
public class SenderQueueRequestor {
    public static void main(String[] args) throws Exception{
        String url = "tcp://localhost:61616";
        String user = ActiveMQConnectionFactory.DEFAULT_USER;
        String password = ActiveMQConnectionFactory.DEFAULT_PASSWORD;
        ActiveMQConnectionFactory factory = new ActiveMQConnectionFactory(user, password, url);
        QueueConnection connection = factory.createQueueConnection();
        connection.start();

        QueueSession session = connection.createQueueSession(false, Session.AUTO_ACKNOWLEDGE);
        Queue queue = session.createQueue("xxoo");
        QueueRequestor queueRequestor = new QueueRequestor(session, queue);

        // 向 broker发送请求，等待响应(同步阻塞)
        System.out.println("=== 准备发请求");
        TextMessage responseMsg = (TextMessage)queueRequestor.request(session.createTextMessage("xxx"));
        System.out.println("=== 请求发完了");
        System.out.println("responseMsg:" + responseMsg.getText());
        System.out.println("System exit....");
    }
}
