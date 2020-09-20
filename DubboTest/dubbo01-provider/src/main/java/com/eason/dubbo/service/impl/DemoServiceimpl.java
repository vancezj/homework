package com.eason.dubbo.service.impl;

import com.eason.dubbo.service.DemoService;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.stereotype.Component;

/**
 * @Auther: Eason
 * @Date: 2020/7/18 - 07 - 18 - 23:53
 * @Description: com.eason.dubbo.service.impl
 * @version: 1.0
 */
@Service(version = "1.0.0", interfaceClass = DemoService.class, timeout = 10000)
@Component
public class DemoServiceimpl implements DemoService {

    @Override
    public String sayHello(String name) {
        System.out.println("Hi! .~" + name);
        return "Hi! .~" + name;
    }
}
