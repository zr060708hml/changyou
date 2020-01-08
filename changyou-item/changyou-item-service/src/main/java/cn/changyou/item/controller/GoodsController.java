package cn.changyou.item.controller;

import cn.changyou.common.pojo.PageResult;
import cn.changyou.item.bo.SpuBo;
import cn.changyou.item.pojo.Sku;
import cn.changyou.item.pojo.SpuDetail;
import cn.changyou.item.service.GoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author GM
 * @create 2020-01-03 14:50
 */
@Controller
public class GoodsController {

    @Autowired
    private GoodsService goodsService;

    /**
     * 查询商品信息
     * @param saleable 商品是否上下架
     * @param page 当前页码
     * @param rows 显示行数
     * @return spu商品信息
     */
    @GetMapping("spu/page")
    public ResponseEntity<PageResult<SpuBo>> querySpuBoByPage(@RequestParam(value = "saleable", required = false) Boolean saleable, @RequestParam(value = "page", defaultValue = "1") Integer page, @RequestParam(value = "rows", defaultValue = "5") Integer rows) {
        PageResult<SpuBo> pageResult = this.goodsService.querySpuBoByPage(saleable, page, rows);
        if (CollectionUtils.isEmpty(pageResult.getItems())) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(pageResult);
    }

    /**
     * 查询商品详细信息 SKU
     * @param id spu表的商品id
     * @return 状态码
     */
    @GetMapping("sku/list")
    public ResponseEntity<List<Sku>> querySkuBySpuId(@RequestParam("id") Long id) {
        List<Sku> skus = this.goodsService.querySkuBySpuId(id);
        if (skus == null || skus.size() == 0) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(skus);
    }

    /**
     * 新增商品
     * @param spu 商品整体信息
     * @return 状态码
     */
    @PostMapping("goods")
    public ResponseEntity<Void> saveGoods(@RequestBody SpuBo spu) {
        int i = this.goodsService.save(spu);
        if (i < 0) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok().build();
    }

    /**
     *删除与spuid对应的sku商品信息
     * @param spuid
     * @return
     */
    @DeleteMapping("goods/{id}")
    public ResponseEntity<Void> deleteGoods(@PathVariable("id") Long spuid) {
        int i = goodsService.deleteSpu(spuid);
        if (i < 0) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok().build();
    }

    /**
     * 通过spuid查询商品详情
     * @param id spuid
     * @return 某个商品的详情
     */
    @GetMapping("/spu/detail/{id}")
    public ResponseEntity<SpuDetail> querySpuDetailById(@PathVariable("id") Long id){
        SpuDetail spuDetail = goodsService.querySpuDetailById(id);
        return ResponseEntity.ok(spuDetail);
    }

    /**
     * 通过skuid获取sku
     * @param skuId
     * @return
     */
    @GetMapping("sku/{skuId}")
    public ResponseEntity<Sku> querySkuBySkuId(@PathVariable("skuId") Long skuId){
        Sku sku = goodsService.querySkuBySkuId(skuId);
        return ResponseEntity.ok(sku);
    }

    /**
     * 修改商品信息
     * @param spuBo 商品详情信息
     * @return  状态码
     */
    @PutMapping("goods")
    public ResponseEntity<Void> updateGoods(@RequestBody SpuBo spuBo){
        goodsService.updateGoods(spuBo);
        return ResponseEntity.ok().build();
    }

    /**
     * 商品上下架
     * @param spuId 商品id
     * @param type  上架或者下架
     * @return 状态码
     */
    @PutMapping("spu/out/{id}/{type}")
    public ResponseEntity<Void> updateStand(@PathVariable("id") Long spuId,@PathVariable("type")Boolean type){
        goodsService.updateStand(spuId,type);
        return ResponseEntity.noContent().build();
    }
}
