package com.eason.admin;

import de.codecentric.boot.admin.server.config.EnableAdminServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableAdminServer
public class AdminService1App {
    public static void main(String[] args) {
        SpringApplication.run(AdminService1App.class, args);
    }
}
