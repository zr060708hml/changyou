package cn.changyou.cart.client;

import cn.changyou.item.api.GoodsApi;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * @author xgl
 * @create 2020-01-05 20:54
 */
@FeignClient("item-service")
public interface GoodsClient extends GoodsApi {
}
