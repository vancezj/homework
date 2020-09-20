package com.eason.config.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Auther: Eason
 * @Date: 2020/9/21 - 09 - 21 - 2:19
 * @Description: com.eason.config.controller
 * @version: 1.0
 */
@RestController
public class TestController {

    @Value("${eason.config.test}")
    public String testMsg;

    @GetMapping("/config/test")
    public String getTestMeg() {
        return testMsg;
    }
}
