package com.eason.eurekeconsumer.controller;

import com.eason.eurekeconsumer.beans.Person;
import com.netflix.appinfo.InstanceInfo;
import com.netflix.discovery.EurekaClient;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.cloud.netflix.eureka.EurekaDiscoveryClient;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletResponse;
import java.net.URI;
import java.util.*;

/**
 * @Auther: Eason
 * @Date: 2020/9/13 - 09 - 13 - 1:52
 * @Description: com.eason.eureka.controller
 * @version: 1.0
 */

//Test RestTemplate
@RestController
public class TestController1 {

    @Autowired
    private RestTemplate restTemplate;

    @GetMapping("/getObj1")
    public Object client1() {
        String url = "http://eureka-provider/getperson";
        Person response = restTemplate.getForObject(url, Person.class);
        System.out.println("response ---> " + response);
        return response;
    }

    @GetMapping("/getObj2")
    public Object client2() {
        String url = "http://eureka-provider/getperson2?name={name}";
        Map<String, String> map = new HashMap<>();
        map.put("name", "xiaojianjian666");
        Person response = restTemplate.getForObject(url, Person.class, map);
        System.out.println("response ---> " + response);
        return response;
    }

    @GetMapping("/getObj3")
    public Object client3() {
        String url = "http://eureka-provider/getperson2?name={1}";
        Person response = restTemplate.getForObject(url, Person.class, "dajianjian222");
        System.out.println("response ---> " + response);
        return response;
    }

    //资源重定向
    @GetMapping("/test1")
    public void client4(HttpServletResponse response) throws Exception{
        String url = "http://eureka-provider/postLocation";
        // Map<String, String> map = new HashMap<>();
        // map.put("name", "buyao,buyao,buyao");
        Person person = new Person("1002", "buyaoting");
        URI location = restTemplate.postForLocation(url, person, Person.class);
        System.out.println("location ---> " + location);
        response.sendRedirect(location.toURL().toString());
    }
}
