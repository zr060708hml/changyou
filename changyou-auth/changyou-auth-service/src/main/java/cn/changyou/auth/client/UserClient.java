package cn.changyou.auth.client;

import cn.changyou.user.api.UserApi;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * @author xgl
 * @create 2020-01-03 23:48
 */
@FeignClient("user-service")
public interface UserClient extends UserApi {
}
