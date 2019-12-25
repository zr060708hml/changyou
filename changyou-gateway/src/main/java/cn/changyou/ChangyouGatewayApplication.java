package cn.changyou;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

/**
 * @author xgl
 * @create 2019-12-07 18:20
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableZuulProxy
public class ChangyouGatewayApplication {
    public static void main(String[] args) {
        SpringApplication.run(ChangyouGatewayApplication.class);
    }
}
