package cn.changyou;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @author xgl
 * @create 2020-01-04 16:22
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
public class ChangyouCartApplication {
    public static void main(String[] args) {
        SpringApplication.run(ChangyouCartApplication.class);
    }
}
