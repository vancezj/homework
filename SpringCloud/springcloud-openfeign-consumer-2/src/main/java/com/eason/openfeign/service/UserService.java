package com.eason.openfeign.service;

import com.eason.openfeign.api.UserApi;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * @Auther: Eason
 * @Date: 2020/9/16 - 09 - 16 - 23:04
 * @Description: com.eason.openfeign.service
 * @version: 1.0
 */

@FeignClient(name = "openfeign-provider", fallbackFactory = UserServiceFallbackFactory.class)
public interface UserService extends UserApi {
}
