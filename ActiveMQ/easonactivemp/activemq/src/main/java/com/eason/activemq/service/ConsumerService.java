package com.eason.activemq.service;

import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ConsumerService {
    @JmsListener(destination = "user",containerFactory = "jmsListenerContainerQueue")
    public void receiveStringQueue(String msg) {
        System.out.println("user Queue --> String 接收到消息...." + msg);
    }

    @JmsListener(destination = "user",containerFactory = "jmsListenerContainerQueue")
    public void receiveListQueue(List msg) {
        System.out.println("user Queue --> List 接收到消息...." + msg.toString());
    }

    @JmsListener(destination = "ooo",containerFactory = "jmsListenerContainerTopic")
    public void receiveStringTopic(String msg) {
        System.out.println("ooo Topic --> 接收到消息...." + msg);
    }

    @JmsListener(destination = "user",containerFactory = "jmsListenerContainerTopic")
    public void receiveStringTopic2(String msg) {
        System.out.println("user Topic --> 接收到消息...." + msg);
    }
}
