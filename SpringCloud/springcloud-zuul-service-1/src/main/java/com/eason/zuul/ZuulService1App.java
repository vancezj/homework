package com.eason.zuul;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

@SpringBootApplication
@EnableZuulProxy
public class ZuulService1App {
    public static void main(String[] args) {
        SpringApplication.run(ZuulService1App.class, args);
    }
}
