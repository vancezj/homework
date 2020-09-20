package com.eason.activemq.controller;

import com.eason.activemq.service.ConsumerService;
import com.eason.activemq.service.ProducerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {
    @Autowired
    ProducerService producerService;

    @Autowired
    ConsumerService consumerService;

    @GetMapping("/send")
    public String testSend() {
        producerService.send1("user");
        producerService.send2("user");
        producerService.send3("user");

        return "OK";
    }

}
