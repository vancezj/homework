package com.eason.eureka.controller;

import com.eason.eureka.beans.Person;
import com.eason.eureka.service.HealthStatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.net.URI;

/**
 * @Auther: Eason
 * @Date: 2020/9/13 - 09 - 13 - 1:52
 * @Description: com.eason.eureka.controller
 * @version: 1.0
 */
@RestController
public class TestController {

    @Autowired
    HealthStatusService healthStatusService;

    @GetMapping("/gethi")
    public String getHello() {
        return "Hi~~good see you again！, i am provider 111";
    }

    @GetMapping("/health")
    public String health(@RequestParam("status") Boolean status) {
        healthStatusService.setStatus(status);
        return healthStatusService.getStatus();
    }

    @GetMapping("/getperson")
    public Person getPerson() {
        Person person = new Person("1001", "xiaomei");
        return person;
    }

    @GetMapping("/getperson2")
    public Person getPerson(String name) {
        Person person = new Person("1001", name);
        return person;
    }

    // 测试资源重定向
    @PostMapping("/postLocation")
    public URI testPostForLocation(@RequestBody Person person, HttpServletResponse response) throws Exception {
        // provider 返回重定向后的uri
        URI uri = new URI("https://www.baidu.com/s?wd=" + person.getName().trim());
        response.setHeader("Location", uri.toString());
        return uri;
    }
}
