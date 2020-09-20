package com.eason.eureka;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@EnableEurekaServer
@SpringBootApplication
public class EurekaServer1App {

    public static void main(String[] args) {
        SpringApplication.run(EurekaServer1App.class, args);
    }

}
