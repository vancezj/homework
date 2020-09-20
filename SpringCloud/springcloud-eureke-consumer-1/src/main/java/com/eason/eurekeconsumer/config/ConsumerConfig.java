package com.eason.eurekeconsumer.config;

import com.netflix.loadbalancer.IRule;
import com.netflix.loadbalancer.RandomRule;
import com.netflix.loadbalancer.RetryRule;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.Charset;
import java.util.Base64;

/**
 * @Auther: Eason
 * @Date: 2020/9/14 - 09 - 14 - 23:29
 * @Description: com.eason.eurekeconsumer.config
 * @version: 1.0
 */
@Configuration
public class ConsumerConfig {

    // 要进行一个Http头信息配置
    @Bean
    public HttpHeaders getHeaders() {
        // 定义一个HTTP的头信息
        HttpHeaders headers = new HttpHeaders();
        // 认证的原始信息
        String auth = "admin:admin";
        // 进行一个加密的处理
        byte[] encodedAuth = Base64.getEncoder().encode(auth.getBytes(Charset.forName("US-ASCII")));
        // 在进行授权的头信息内容配置的时候加密的信息一定要与“Basic”之间有一个空格
        String authHeader = "Basic " + new String(encodedAuth);
        headers.set("Authorization", authHeader);
        return headers;
    }

    @Bean
    @LoadBalanced
    public RestTemplate getRestTemplate() {
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getInterceptors().add(new LoggingClientHttpRequestInterceptor());
        return restTemplate;
    }

    /**
    // 简单 切换负载均衡策略
    @Bean
    public IRule myRule() {
        //return new RoundRobinRule();
        //return new RandomRule();
        return new RandomRule();
    }
    */
}
