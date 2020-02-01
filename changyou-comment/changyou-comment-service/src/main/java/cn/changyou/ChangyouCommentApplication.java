package cn.changyou;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import tk.mybatis.spring.annotation.MapperScan;

/**
 * @author GM
 * @create 2019-12-07 18:44
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
@MapperScan("cn.changyou.comment.mapper")
public class ChangyouCommentApplication {
    public static void main(String[] args) {
        SpringApplication.run(ChangyouCommentApplication.class);
    }
}
