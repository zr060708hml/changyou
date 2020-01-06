package cn.changyou.cart.service;

import cn.changyou.cart.client.GoodsClient;
import cn.changyou.cart.interceptor.LoginInterceptor;
import cn.changyou.cart.pojo.Cart;
import cn.changyou.common.pojo.UserInfo;
import cn.changyou.common.utils.JsonUtils;
import cn.changyou.item.pojo.Sku;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.BoundHashOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.stream.Collectors;


/**
 * @author xgl
 * @create 2020-01-05 20:31
 */
@Service
public class CartService {
    @Autowired
    private StringRedisTemplate redisTemplate;

    @Autowired
    private GoodsClient goodsClient;

    private Logger log = LoggerFactory.getLogger(CartService.class);

    private static final String KEY_PREFIX = "user:cart:";

    /**
     * 添加购物车
     *
     * @param cart
     * @return
     */
    public void addCart(Cart cart) {
        //0.获取用户信息
        UserInfo userInfo = LoginInterceptor.getUserInfo();
        //1.查询购物车记录
        BoundHashOperations<String, Object, Object> hash = redisTemplate.boundHashOps(KEY_PREFIX + userInfo.getId());

        Integer num = cart.getNum();

        //2.判断当前商品是否在购物车中
        if (hash.hasKey(cart.getSkuId().toString())) {
            //如果在的话,更新数量
            String cartJson = hash.get(cart.getSkuId().toString()).toString();
            cart = JsonUtils.parse(cartJson, Cart.class);
            cart.setNum(cart.getNum() + num);
        } else {
            //新增购物车
            Sku sku = goodsClient.querySkuBySkuId(cart.getSkuId());

            cart.setUserId(userInfo.getId());
            cart.setTitle(sku.getTitle());
            cart.setOwnSpec(sku.getOwnSpec());
            cart.setImage(StringUtils.isBlank(sku.getImages()) ? "" : StringUtils.split(sku.getImages(), ",")[0]);
            cart.setPrice(sku.getPrice());

        }
        hash.put(cart.getSkuId().toString(), JsonUtils.serialize(cart));
    }

    /**
     * 查询购物车
     *
     * @return
     */
    public List<Cart> queryCarts() {
        //1.获取用户信息
        UserInfo userInfo = LoginInterceptor.getUserInfo();
        //2.获取用户的购物车
        //判断用户是否有购物车记录
        if (!redisTemplate.hasKey(KEY_PREFIX + userInfo.getId())) {
            return null;
        }
        BoundHashOperations<String, Object, Object> hashOps = redisTemplate.boundHashOps(KEY_PREFIX + userInfo.getId());
        //获取购物车map中的所有Cart值集合
        List<Object> cartsJson = hashOps.values();
        //如果购物车集合为空,返回null
        if (CollectionUtils.isEmpty(cartsJson)) {
            return null;
        }
        //把list<Object>集合转成List<Cart>集合
        return cartsJson.stream().map(cartJson -> JsonUtils.parse(cartJson.toString(), Cart.class)).collect(Collectors.toList());
    }

    /**
     * 更新购物车数量
     *
     * @param cart
     * @return
     */
    public void updateNum(Cart cart) {
        UserInfo userInfo = LoginInterceptor.getUserInfo();
        //2.获取用户的购物车
        //判断用户是否有购物车记录
        if (!redisTemplate.hasKey(KEY_PREFIX + userInfo.getId())) {
            return;
        }
        Integer num = cart.getNum();
        BoundHashOperations<String, Object, Object> hashOps = redisTemplate.boundHashOps(KEY_PREFIX + userInfo.getId());
        String cartJson = hashOps.get(cart.getSkuId().toString()).toString();
        cart = JsonUtils.parse(cartJson, Cart.class);
        cart.setNum(num);
        hashOps.put(cart.getSkuId().toString(), JsonUtils.serialize(cart));
    }

    /**
     * 删除购物车
     *
     * @param skuId
     */
    public void deleteCart(String skuId) {
        //获取登录用户
        UserInfo userInfo = LoginInterceptor.getUserInfo();
        BoundHashOperations<String, Object, Object> hashOps = redisTemplate.boundHashOps(KEY_PREFIX + userInfo.getId());
        hashOps.delete(skuId);
    }

    /**
     * 合并购物车
     *
     * @param carts
     * @return
     */
    public void mergeCart(List<Cart> carts) {
        //1.获取登录用户
        UserInfo userInfo = LoginInterceptor.getUserInfo();
        //2.获取当前用户的购物车
        BoundHashOperations<String, Object, Object> hashOps = redisTemplate.boundHashOps(KEY_PREFIX + userInfo.getId());
        //3.判断redis中是否有浏览器本地的购物车记录,如果有,数量相加,如果没有,添加到redis
        carts.forEach(cart -> {
            if (hashOps.hasKey(cart.getSkuId().toString())) {
                log.info("此时,redis中有对应商品的购物车记录,开始数量相加");
                String cartJson = hashOps.get(cart.getSkuId().toString()).toString();
                Integer num = cart.getNum();
                cart = JsonUtils.parse(cartJson, Cart.class);
                cart.setNum(cart.getNum() + num);
            }
            log.info("此时,redis中没有对应商品的购物车记录,添加到redis");
            hashOps.put(cart.getSkuId().toString(),JsonUtils.serialize(cart));
        });
    }
}
