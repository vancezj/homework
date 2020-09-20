package eason.mq.active.test3;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @Auther: Eason
 * @Date: 2020/8/17 - 08 - 17 - 3:11
 * @Description: 测试多种数据类型 超时消息 死信队列
 * @version: 1.0
 */
public class Consumer3 {
    public static void main(String[] args) throws Exception{
        // 获取连接工厂
        ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory();
        connectionFactory.setPassword(ActiveMQConnectionFactory.DEFAULT_USER);
        connectionFactory.setPassword(ActiveMQConnectionFactory.DEFAULT_PASSWORD);
        connectionFactory.setBrokerURL("tcp://localhost:61616");

        // 创建信任列表
        List<String> arrayList = new ArrayList<>();
        arrayList.add(Girl.class.getPackage().getName());
        System.out.println(Girl.class.getPackage().getName());
        connectionFactory.setTrustedPackages(arrayList);

        // 创建 connection
        Connection connection = connectionFactory.createConnection();
        connection.start();
        // 获取 session
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        // 创建 destination 消息的目的地
        Destination queue = session.createQueue("user");
        // 创建消息的消费者
        MessageConsumer consumer = session.createConsumer(queue);
        // 接收消息
        consumer.setMessageListener(new MessageListener() {
            @Override
            public void onMessage(Message message) {
                if (message instanceof ObjectMessage) {
                    ObjectMessage objectMessage = (ObjectMessage) message;
                    // System.out.println(">>> objectMessage = " + objectMessage.toString());
                    try {
                        Girl girl = (Girl)objectMessage.getObject();
                        System.out.println(">>> objectMessage = " + girl.toString());
                    } catch (JMSException e) {
                        e.printStackTrace();
                    }
                } else if (message instanceof TextMessage) {
                    TextMessage textMessage = (TextMessage) message;
                    try {
                        System.out.println(">>> textMessage = " + textMessage.getText());
                    } catch (JMSException e) {
                        e.printStackTrace();
                    }
                } else if (message instanceof BytesMessage) {
                    BytesMessage bytesMessage = (BytesMessage) message;
                    try {
                        //System.out.println(">>> bytesMessage = " + bytesMessage.readUTF());
                        FileOutputStream out = null;
                        try {
                            out = new FileOutputStream("E:/dev/aa.txt");
                        } catch (FileNotFoundException e2) {
                            e2.printStackTrace();
                        }
                        byte[] by = new byte[1024];
                        int len = 0 ;
                        try {
                            while((len = bytesMessage.readBytes(by))!= -1){
                                out.write(by,0,len);
                            }
                        } catch (Exception e1) {
                            e1.printStackTrace();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else if (message instanceof MapMessage) {
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
        // 创建 DLQ destination -- 死信队列
        Destination queueDLQ = session.createQueue("ActiveMQ.DLQxxoo");
        MessageConsumer consumerDLQ = session.createConsumer(queueDLQ);
        consumerDLQ.setMessageListener(new MessageListener() {
            @Override
            public void onMessage(Message message) {
                if (message instanceof MapMessage) {
                    MapMessage mapMessage = (MapMessage) message;
                    try {
                        System.out.println(">>> mapMessage DLQxxoo = " + mapMessage.toString());
                        System.out.println(">>> mapMessage DLQxxoo --> name = " + mapMessage.getString("name"));
                        System.out.println(">>> mapMessage DLQxxoo --> age = " + mapMessage.getInt("age"));
                        System.out.println(">>> mapMessage DLQxxoo --> price = " + mapMessage.getDouble("price"));
                    } catch (JMSException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }
}
