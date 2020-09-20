package com.eason.openfeign.controller;

import com.eason.openfeign.beans.User;
import com.eason.openfeign.service.UserService;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

/**
 * @Auther: Eason
 * @Date: 2020/9/16 - 09 - 16 - 23:07
 * @Description: com.eason.openfeign.controller
 * @version: 1.0
 */
@RestController
public class UserController {

    @Autowired
    private RestTemplate restTemplate;

    @HystrixCommand(fallbackMethod = "back")
    @GetMapping("/alive2")
    public String alive2() {
        String url ="http://openfeign-provider/user/isAlive";
        String object = restTemplate.getForObject(url, String.class);
        return object;
    }

    public String back() {
        return "请求失败...xxoo...";
    }

    @Autowired
    private UserService userService;

    @GetMapping("/alive")
    public String alive() {
        String msg = userService.isAlive();
        System.out.println(">>>>> openfeign-consumer-1 -->>>>> " + msg);
        return "openfeign-consumer-1 >>>>> " + msg;
    }

    @GetMapping("/user1")
    public User getUser1(@RequestParam("id") String id){
        return userService.getUser1(id);
    }

    @GetMapping("/user2")
    public User getUser2(@RequestParam("id") String id, @RequestParam("name") String name) {
        return userService.getUser2(id, name);
    }

    @GetMapping("/user3/{id}")
    public User getUser3(@PathVariable("id") String id) {
        return userService.getUser3(id);
    }

    @GetMapping("/user4")
    public Map<String, String> getMap2(@RequestParam("id") String id) {
        return userService.getMap2(id);
    }

    @PostMapping("/save")
    public String saveUser(@RequestBody User user){
        System.out.println("user ---> " + user);
        return userService.saveUser(user);
    }
}
