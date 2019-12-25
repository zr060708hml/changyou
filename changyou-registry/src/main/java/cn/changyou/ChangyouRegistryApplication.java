package cn.changyou;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * @author xgl
 * @create 2019-12-07 18:13
 */
@SpringBootApplication
@EnableEurekaServer
public class ChangyouRegistryApplication {
    public static void main(String[] args) {
        SpringApplication.run(ChangyouRegistryApplication.class);
    }
}
