package cn.changyou;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @author xgl
 * @create 2019-12-16 18:51
 */
@SpringBootApplication
@EnableDiscoveryClient
public class ChangyouUploadApplication {
    public static void main(String[] args) {
        SpringApplication.run(ChangyouUploadApplication.class);
    }
}
