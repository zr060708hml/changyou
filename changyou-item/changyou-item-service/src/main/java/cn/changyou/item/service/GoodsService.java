package cn.changyou.item.service;

import cn.changyou.common.pojo.PageResult;
import cn.changyou.item.bo.SpuBo;
import cn.changyou.item.mapper.*;
import cn.changyou.item.pojo.Sku;
import cn.changyou.item.pojo.Spu;
import cn.changyou.item.pojo.Stock;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * @author GM
 * @create 2020-01-03 14:54
 */

@Service
public class GoodsService {

    @Autowired
    private SpuMapper spuMapper;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private BrandMapper brandMapper;

    @Autowired
    private SkuMapper skuMapper;

    @Autowired
    private StockMapper stockMapper;

    @Autowired
    private SpuDetailMapper spuDetailMapper;

    /**
     * 分页查询所有的商品
     *
     * @param key      要搜索的商品
     * @param saleable 是否上架
     * @param page     当前页码,默认是1
     * @param rows     每页显示多少行
     * @return 商品信息
     */
    public PageResult<SpuBo> querySpuBoByPage(String key, Boolean saleable, Integer page, Integer rows) {

        Example example = new Example(Spu.class);
        Example.Criteria criteria = example.createCriteria();
        if (StringUtils.isNotBlank(key)) {
            criteria.andLike("title", "%" + key + "%");
        }
        if (saleable != null) {
            criteria.andEqualTo("saleable", saleable);
        }
        PageHelper.startPage(page, rows);

        List<Spu> spus = this.spuMapper.selectByExample(example);
        PageInfo<Spu> pageInfo = new PageInfo<>(spus);

        List<SpuBo> spuBos = new ArrayList<>();
        spus.forEach(spu -> {
            SpuBo spuBo = new SpuBo();
            BeanUtils.copyProperties(spu, spuBo);
            List<String> names = this.categoryService.queryNamesByIds(Arrays.asList(spu.getCid1(), spu.getCid2(), spu.getCid3()));
            spuBo.setCname(StringUtils.join(names, "/"));

            spuBo.setBname(this.brandMapper.selectByPrimaryKey(spu.getBrandId()).getName());

            spuBos.add(spuBo);
        });

        return new PageResult<>(pageInfo.getTotal(), spuBos);

    }

    /**
     * 新增商品
     *
     * @param spu SpuBo的实体类
     */
    public void save(SpuBo spu) {
        // 保存spu
        spu.setSaleable(true);
        spu.setValid(true);
        spu.setCreateTime(new Date());
        spu.setLastUpdateTime(spu.getCreateTime());
        this.spuMapper.insert(spu);
        // 保存spu详情
        spu.getSpuDetail().setSpuId(spu.getId());
        this.spuDetailMapper.insert(spu.getSpuDetail());
        saveSkuAndStock(spu.getSkus(), spu.getId());
    }

    /**
     * 保存sku和库存信息
     *
     * @param skus  sku商品集合
     * @param spuId spuid
     */
    private void saveSkuAndStock(List<Sku> skus, Long spuId) {
        for (Sku sku : skus) {
            if (!sku.getEnable()) {
                continue;
            }
            // 保存sku
            sku.setSpuId(spuId);
            // 初始化时间
            sku.setCreateTime(new Date());
            sku.setLastUpdateTime(sku.getCreateTime());
            this.skuMapper.insert(sku);

            // 保存库存信息
            Stock stock = new Stock();
            stock.setSkuId(sku.getId());
            stock.setStock(sku.getStock());
            this.stockMapper.insert(stock);
        }
    }

    /**
     * 删除商品信息
     * @param spuid spu表的商品id
     * @return 受影响行数
     */
    public int deleteSpu(Long spuid) {
        int i = spuMapper.deleteByPrimaryKey(spuid);
        int i1 = spuDetailMapper.deleteByPrimaryKey(spuid);
        Sku sku = new Sku();
        sku.setSpuId(spuid);
        List<Sku> select = skuMapper.select(sku);
        int i2 = skuMapper.deleteByExample(spuid);
        int i3 = -1;
        for (Sku sku1 : select) {
            Long id = sku1.getId();
            i3 = stockMapper.deleteByPrimaryKey(id);
        }
        if (i > 0 && i1 > 0 && i2 > 0 && i3 > 0){
            return 1;
        }
        return -1;
    }

    public List<Sku> querySkuBySpuId(Long spuId){
        Sku record = new Sku();
        record.setSpuId(spuId);
        List<Sku> skus = this.skuMapper.select(record);
        for (Sku sku : skus) {
            // 同时查询出库存
            sku.setStock(this.stockMapper.selectByPrimaryKey(sku.getId()).getStock());
        }
        return skus;
    }




    public int updateGoods(SpuBo spu, Long spuid){



        return 1;
    }





    public void setSpuMapper(SpuMapper spuMapper) {
        this.spuMapper = spuMapper;
    }

    public void setCategoryService(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    public void setBrandMapper(BrandMapper brandMapper) {
        this.brandMapper = brandMapper;
    }

    public void setSkuMapper(SkuMapper skuMapper) {
        this.skuMapper = skuMapper;
    }

    public void setStockMapper(StockMapper stockMapper) {
        this.stockMapper = stockMapper;
    }

    public void setSpuDetailMapper(SpuDetailMapper spuDetailMapper) {
        this.spuDetailMapper = spuDetailMapper;
    }
}
