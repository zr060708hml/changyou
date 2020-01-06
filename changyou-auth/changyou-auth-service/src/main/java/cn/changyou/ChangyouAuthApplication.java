package cn.changyou;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @author xgl
 * @create 2020-01-03 22:54
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
public class ChangyouAuthApplication {
    public static void main(String[] args) {
        SpringApplication.run(ChangyouAuthApplication.class);
    }
}
