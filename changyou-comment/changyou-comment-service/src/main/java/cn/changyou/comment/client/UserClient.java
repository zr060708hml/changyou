package cn.changyou.comment.client;

import cn.changyou.user.api.QureyApi;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * @author GM
 * @create 2020-01-12 16:03
 */
@FeignClient("user-service")
public interface UserClient extends QureyApi {
}
