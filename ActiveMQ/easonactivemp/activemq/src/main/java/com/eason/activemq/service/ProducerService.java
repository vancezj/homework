package com.eason.activemq.service;

import org.apache.activemq.command.ActiveMQQueue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Service;

import javax.jms.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ProducerService {
    //简化版
    @Autowired
    private JmsMessagingTemplate jmsMessagingTemplate;

    //原始版
    @Autowired
    private JmsTemplate jmsTemplate;

    public void send1(String destination) {
        //ActiveMQQueue queue = new ActiveMQQueue(destination);
        System.out.println("test send1 (jmsMessagingTemplate) --> Text message>>>");
        jmsMessagingTemplate.convertAndSend(destination, "hello~1");

        ArrayList<String> list = new ArrayList<>();
        list.add("shuang");
        list.add("tuantuan");
        list.add("feifei");
        jmsMessagingTemplate.convertAndSend(destination, list);
        System.out.println("test send1 (jmsMessagingTemplate) --> List message>>>");

        /*
        Map<String,String> map = new HashMap<>();
        map.put("shuang", "shuang--Map");
        map.put("tuantuan", "tuantuan--Map");
        map.put("feifei", "feifei--Map");
        jmsMessagingTemplate.convertAndSend(queue, map);
        System.out.println("test send1 (jmsMessagingTemplate) --> Map message>>>");


        Girl meimei = new Girl("meimei", 22, 398.00);
        jmsMessagingTemplate.convertAndSend(queue, meimei);
        System.out.println("test send1 (jmsMessagingTemplate) --> Object message>>>");

         */
    }

    public void send2(String destination) {
        ConnectionFactory factory = jmsMessagingTemplate.getConnectionFactory();
        try {
            Connection connection = factory.createConnection();
            connection.start();
            Session session = connection.createSession(true, Session.SESSION_TRANSACTED);
            Queue queue = session.createQueue(destination);
            MessageProducer producer = session.createProducer(queue);
            TextMessage textMessage = session.createTextMessage("test send2 ---> message22222222");
            producer.send(textMessage);
            session.commit();
        } catch (JMSException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public void send3(String destination) {
        ActiveMQQueue queue = new ActiveMQQueue(destination);
        jmsTemplate.send(queue, new MessageCreator() {
            @Override
            public Message createMessage(Session session) throws JMSException {
                TextMessage textMessage = session.createTextMessage("test send3 ---> message3333333");
                textMessage.setStringProperty("haha", "enen");
                return textMessage;
            }
       });
    }
}
