package com.eason.openfeign.service;

import com.eason.openfeign.beans.User;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @Auther: Eason
 * @Date: 2020/9/18 - 09 - 18 - 16:30
 * @Description: com.eason.openfeign.service
 * @version: 1.0
 */
@Component
public class UserServiceFallBack implements UserService {
    @Override
    public String isAlive() {
        return "isAlive --> FallBack";
    }

    @Override
    public User getUser1(String id) {
        return null;
    }

    @Override
    public User getUser2(String id, String name) {
        return null;
    }

    @Override
    public User getUser3(String id) {
        return null;
    }

    @Override
    public Map<String, String> getMap2(String id) {
        return null;
    }

    @Override
    public String saveUser(User user) {
        return null;
    }
}
