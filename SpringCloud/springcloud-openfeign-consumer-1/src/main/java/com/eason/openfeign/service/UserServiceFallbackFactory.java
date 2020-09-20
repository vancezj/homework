package com.eason.openfeign.service;

import com.eason.openfeign.beans.User;
import com.netflix.client.ClientException;
import feign.hystrix.FallbackFactory;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @Auther: Eason
 * @Date: 2020/9/18 - 09 - 18 - 17:18
 * @Description: com.eason.openfeign.service
 * @version: 1.0
 */
@Component
public class UserServiceFallbackFactory implements FallbackFactory<UserService> {
    @Override
    public UserService create(Throwable cause) {
        return new UserService() {
            @Override
            public String isAlive() {
                //  针对不同异常返回响应
                System.out.println(cause.getLocalizedMessage());
                cause.printStackTrace();
                if (cause instanceof ClientException) {
                    System.out.println("InternalServerError");
                    return "远程服务报错";
                } else if(cause instanceof RuntimeException) {
                    return "请求时异常：" + cause;
                } else {
                    return ToStringBuilder.reflectionToString(cause);
                }
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
        };
    }
}
