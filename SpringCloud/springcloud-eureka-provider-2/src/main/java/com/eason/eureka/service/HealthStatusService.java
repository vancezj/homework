package com.eason.eureka.service;

import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Service;

/**
 * @Auther: Eason
 * @Date: 2020/9/14 - 09 - 14 - 20:47
 * @Description: com.eason.eureka.service
 * @version: 1.0
 */
@Service
public class HealthStatusService implements HealthIndicator {

    private Boolean status = true;

    @Override
    public Health getHealth(boolean includeDetails) {
        return null;
    }

    @Override
    public Health health() {
        if(status) {
            return new Health.Builder().up().build();
        }
        return new Health.Builder().down().build();
    }

    public void setStatus(Boolean status) {
        this.status  = status;
    }

    public String getStatus() {
        return this.status.toString();
    }
}
