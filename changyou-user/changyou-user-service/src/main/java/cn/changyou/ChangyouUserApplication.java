package cn.changyou;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import tk.mybatis.spring.annotation.MapperScan;

/**
 * @author xgl
 * @create 2019-12-27 0:26
 */
@SpringBootApplication
@EnableDiscoveryClient
@MapperScan("cn.changyou.user.mapper")
public class ChangyouUserApplication {
    public static void main(String[] args) {
        SpringApplication.run(ChangyouUserApplication.class,args);
    }
}
