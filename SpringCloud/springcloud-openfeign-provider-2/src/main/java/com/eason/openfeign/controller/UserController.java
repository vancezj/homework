package com.eason.openfeign.controller;

import com.eason.openfeign.api.UserApi;
import com.eason.openfeign.beans.User;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * @Auther: Eason
 * @Date: 2020/9/16 - 09 - 16 - 23:00
 * @Description: com.eason.openfeign.controller
 * @version: 1.0
 */
@RestController
public class UserController implements UserApi {
    private static final String KEY = "openfeign-provider-2 ";

    @Override
    public String isAlive() {
        return KEY + "ok...";
    }

    @Override
    public User getUser1(String id) {
        System.out.println(KEY + "------> getUser1 ------->");
        User user = new User(id, "zhangsan");
        return user;
    }

    @Override
    public User getUser2(String id, String name) {
        System.out.println(KEY +"------> getUser2 ------->");
        User user = new User(id, name);
        return user;
    }

    @Override
    public User getUser3(String id) {
        System.out.println(KEY + "------> getUser3 ------->");
        User user = new User(id, "dadiao");
        return user;
    }

    @Override
    public Map<String, String> getMap2(String id) {
        System.out.println(KEY + "------> getMap2 ------->");
        Map<String, String>  userMap = new HashMap<>();
        userMap.put(id, "xxooxx");
        return userMap;
    }

    @Override
    public String saveUser(User user) {
        System.out.println(KEY + "------> saveUser ------->");
        User user1 = user;
        System.out.println(user);
        return "save user ok";
    }
}
