package com.eason.config;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;

@SpringBootApplication
@EnableConfigServer
public class ConfigService1App {
    public static void main(String[] args) {
        SpringApplication.run(ConfigService1App.class, args);
    }
}
