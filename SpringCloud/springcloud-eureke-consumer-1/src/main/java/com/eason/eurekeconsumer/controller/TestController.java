package com.eason.eurekeconsumer.controller;

import com.netflix.appinfo.InstanceInfo;
import com.netflix.discovery.EurekaClient;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.cloud.netflix.eureka.EurekaDiscoveryClient;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

/**
 * @Auther: Eason
 * @Date: 2020/9/13 - 09 - 13 - 1:52
 * @Description: com.eason.eureka.controller
 * @version: 1.0
 */
@RestController
public class TestController {

    @Autowired
    private DiscoveryClient discoveryClient;

    @Autowired
    private EurekaDiscoveryClient eurekaDiscoveryClient;

    @Autowired
    private EurekaClient eurekaClient;

    @Autowired
    private LoadBalancerClient loadBalancerClient;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private HttpHeaders headers;

    @GetMapping("/client1")
    public Object client1() {
        List<String> services = discoveryClient.getServices();
        for (String service : services) {
            System.out.println("service --> " + service);
        }
        System.out.println("------------------------------------");

        List<ServiceInstance> eurekaprovider = discoveryClient.getInstances("eureka-provider");
        for (ServiceInstance ins : eurekaprovider) {
            System.out.println("ins.getHost() -     -> " + ins.getHost());
            System.out.println("ins.getInstanceId()--> " + ins.getInstanceId());
            System.out.println("ins.getScheme()    --> " + ins.getScheme());
            System.out.println("ins.getServiceId() --> " + ins.getServiceId());
            System.out.println("ins.getPort()      --> " + ins.getPort());
            System.out.println("ins.getUri()       --> " + ins.getUri());
            Map<String, String> metadata = ins.getMetadata();
            for(String key : metadata.keySet()){
                String value = metadata.get(key);
                System.out.println("metadata --> " + key + ":" + value);
            }

            System.out.println(ToStringBuilder.reflectionToString(ins));
            System.out.println("################################################");
        }
        return eurekaprovider;
    }

    @GetMapping("/client2")
    public Object client2() {
        List<String> services = eurekaDiscoveryClient.getServices();
        for (String service : services) {
            System.out.println("service --> " + service);
        }
        List<ServiceInstance> eurekaprovider = eurekaDiscoveryClient.getInstances("eureka-provider");
        return eurekaprovider;
    }

    @GetMapping("/client3")
    public Object client3() {
        // 指定具体服务
        List<InstanceInfo> instanceInfos1 = eurekaClient.getInstancesById("192.168.58.1:eureka-provider:7011");
        for (InstanceInfo ins : instanceInfos1) {
            System.out.println("//-------------//");
            System.out.println(ToStringBuilder.reflectionToString(ins));
        }
        // 根据服务名 获取所有列表信息
        List<InstanceInfo> instanceInfos2 = eurekaClient.getInstancesByVipAddress("eureka-provider", false);
        for (InstanceInfo ins : instanceInfos2) {
            System.out.println("//-------------//");
            System.out.println(ToStringBuilder.reflectionToString(ins));
        }
        if (instanceInfos2.size() > 0) {
            for (InstanceInfo ins : instanceInfos2) {
                // 获取已经上线的服务，根据服务信息拼接restful调用的url
                if (InstanceInfo.InstanceStatus.UP.equals(ins.getStatus())) {
                    String ip = ins.getIPAddr();
                    System.out.println("ip --> " + ip);
                    String url = "http://" + ins.getHostName() + ":" + ins.getPort() + "/gethi";
                    System.out.println("url ---> " + url);

                    // 调用具体服务
                    RestTemplate restTemplate = new RestTemplate();
                    String response = restTemplate.getForObject(url, String.class);
                    System.out.println("response ---> " + response);
                    System.out.println("//----------------------------//");
                }
            }
        }
        return "xxoo";
    }

    @GetMapping("/client4")
    public Object client4() {
        //简陋的 ribbon 完成客户端的负载均衡, 会过滤掉down的节点
        ServiceInstance ins = loadBalancerClient.choose("eureka-provider");
        String url = "http://" + ins.getHost() + ":" + ins.getPort() + "/gethi";
        System.out.println("url ---> " + url);

        // 调用具体服务
        // String response = restTemplate.getForObject(url, String.class);
        String response = restTemplate.exchange(url, HttpMethod.GET, new HttpEntity<Object>(this.headers), String.class)
                .getBody();
        System.out.println("response ---> " + response);
        return response;
    }

    // 自定义负载均衡策略
    @GetMapping("/client5")
    public Object client5() {
        String url = "http://eureka-provider/gethi";
        String response = restTemplate.getForObject(url, String.class);
        System.out.println("response ---> " + response);
        return response;
    }
}
