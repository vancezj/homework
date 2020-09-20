package com.eason.dubbo.controller;

import com.eason.dubbo.service.DemoService;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Auther: Eason
 * @Date: 2020/7/19 - 07 - 19 - 0:17
 * @Description: com.eason.dubbo.controller
 * @version: 1.0
 */
@RestController
@RequestMapping("/api")
public class TestController {

    @Reference(version = "1.0.0")
    DemoService demoService;

    @RequestMapping("/say")
    public String say() {
        return demoService.sayHello("zhangsan");
    }
}
