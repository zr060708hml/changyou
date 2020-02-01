package cn.changyou.comment.client;

import cn.changyou.item.api.GoodsApi;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * @author GM
 * @create 2020-01-13 10:30
 */
@FeignClient("item-service")
public interface SpuClient extends GoodsApi {
}
