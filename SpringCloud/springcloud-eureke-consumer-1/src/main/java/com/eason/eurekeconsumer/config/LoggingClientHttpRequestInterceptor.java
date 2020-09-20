package com.eason.eurekeconsumer.config;

import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;

import java.io.IOException;

/**
 * @Auther: Eason
 * @Date: 2020/9/15 - 09 - 15 - 23:12
 * @Description: com.eason.eurekeconsumer.config
 * @version: 1.0
 */
//拦截 RestTemplate 请求
public class LoggingClientHttpRequestInterceptor implements ClientHttpRequestInterceptor {
    @Override
    public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution)
            throws IOException {
        System.out.println("拦截啦！！！");
        System.out.println(request.getURI());
        ClientHttpResponse response = execution.execute(request, body);
        System.out.println(response.getHeaders());
        return response;
    }
}
