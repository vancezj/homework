package com.eason.openfeign.api;

import com.eason.openfeign.beans.User;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * @Auther: Eason
 * @Date: 2020/9/16 - 09 - 16 - 22:48
 * @Description: com.eason.openfeign.api
 * @version: 1.0
 */

// @RequestMapping("/user")
public interface UserApi {

    @GetMapping("/user/isAlive")
    String isAlive();

    @GetMapping("/user1")
    User getUser1(@RequestParam("id") String id);

    @GetMapping("/user2")
    User getUser2(@RequestParam("id") String id, @RequestParam("name") String name);

    @GetMapping("/user3/{id}")
    User getUser3(@PathVariable("id") String id);

    @GetMapping("/getMap1")
    Map<String, String> getMap2(@RequestParam("id") String id);

    @PostMapping("/save")
    String saveUser(@RequestBody User user);
}
