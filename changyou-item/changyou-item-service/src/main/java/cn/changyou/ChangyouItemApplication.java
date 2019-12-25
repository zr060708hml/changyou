package cn.changyou;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import tk.mybatis.spring.annotation.MapperScan;

/**
 * @author xgl
 * @create 2019-12-07 18:44
 */
@SpringBootApplication
@EnableDiscoveryClient
@MapperScan("cn.changyou.item.mapper")
public class ChangyouItemApplication {
    public static void main(String[] args) {
        SpringApplication.run(ChangyouItemApplication.class);
    }
}
