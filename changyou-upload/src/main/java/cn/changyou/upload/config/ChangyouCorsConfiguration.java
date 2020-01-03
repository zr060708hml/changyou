package cn.changyou.upload.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

/**
 * @author xgl
 * @create 2019-12-12 19:50
 */
@Configuration
public class ChangyouCorsConfiguration {
    @Bean
    public CorsFilter corsFilter(){
        //初始化cors配置对象
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        //设置允许跨域的域名,如果要携带cookie,则不能写成*
        corsConfiguration.addAllowedOrigin("http://manage.changyou.xgl6.cn");
        corsConfiguration.setAllowCredentials(true);
        //*代表所有请求方法,get,post,put,delete...
        corsConfiguration.addAllowedMethod("*");
        //允许携带任何头信息
        corsConfiguration.addAllowedHeader("*");
        //初始化cors配置源对象
        UrlBasedCorsConfigurationSource configurationSource = new UrlBasedCorsConfigurationSource();
        configurationSource.registerCorsConfiguration("/**",corsConfiguration);
        //返回corsFilter实例,参数:cors配置源对象
        return new CorsFilter(configurationSource);
    }
}
