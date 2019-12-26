package cn.changyou.item.api;

import cn.changyou.common.pojo.PageResult;
import cn.changyou.item.bo.SpuBo;
import cn.changyou.item.pojo.Sku;
import cn.changyou.item.pojo.SpuDetail;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @author xgl
 * @create 2019-12-25 15:19
 */
public interface GoodsApi {
    /**
     * 通过spuid查询spudetail,用于编辑时的商品回显
     * @param spuId
     * @return
     */
    @GetMapping("spu/detail/{spuId}")
    SpuDetail querySpuDetailBySpuID(@PathVariable("spuId") Long spuId);
    /**
     * 商品列表分页展示
     * @return
     */
    @GetMapping("spu/page")
    PageResult<SpuBo> querySpuByPage(
            @RequestParam(value = "key", required = false) String key,
            @RequestParam(value = "saleable", required = false) Boolean saleable,
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            @RequestParam(value = "rows", defaultValue = "5") Integer rows
    );
    /**
     * 通过spuid查询下面所有的具体商品
     * @param spuId
     * @return
     */
    @GetMapping("sku/list")
    List<Sku> querySkusBySpuId(@RequestParam("id") Long spuId);
}
