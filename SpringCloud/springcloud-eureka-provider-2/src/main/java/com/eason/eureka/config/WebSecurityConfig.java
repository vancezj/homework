package com.eason.eureka.config;

import org.springframework.context.annotation.Configuration;

/**
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;


 * @Auther: Eason
 * @Date: 2020/9/14 - 09 - 14 - 21:21
 * @Description: com.eason.eureka.config
 * @version: 1.0

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();
        super.configure(http);
    }
}
 */