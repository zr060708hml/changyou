package cn.changyou.item.service;

import cn.changyou.common.pojo.PageResult;
import cn.changyou.item.mapper.BrandMapper;
import cn.changyou.item.pojo.Brand;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.sun.corba.se.spi.orbutil.fsm.Guard;
import com.sun.net.httpserver.Authenticator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author GM
 * @create 2019-12-26 17:11
 */
@Service
public class BrandService {

    @Autowired
    private BrandMapper brandMapper;

    public void setBrandMapper(BrandMapper brandMapper) {
        this.brandMapper = brandMapper;
    }

    /**
     * 查询品牌
     *
     * @param page   第几页,默认是1
     * @param rows   每页显示多少行,默认是5
     * @return 分页后的数据
     */
    public PageResult<Brand> querySpuByPage(Integer page, Integer rows) {
        PageHelper.startPage(page, rows);
        List<Brand> brands = brandMapper.selectAll();
        PageInfo<Brand> result = new PageInfo<>(brands);

        return new PageResult<>(result.getTotal(), brands);
    }

    /**
     * 新增品牌
     *
     * @param brand 品牌实体
     * @param cids  分类id的集合
     * @return 受影响行数
     */
    @Transactional
    public int saveBrand(Brand brand, List<Long> cids) {
        int result1 = brandMapper.insert(brand);
        int result2 = -1;
        for (Long cid : cids) {
            result2 = brandMapper.insertBrandAndCategory(cid, brand.getId());
        }
        if (result1 < 0 || result2 < 0) {
            return -1;
        }
        return 1;
    }

    /**
     * 修改品牌信息
     *
     * @param brand 品牌实体类
     * @param cids  分类id的集合
     * @return 受影响行数
     */
    public int updateBrand(Brand brand, List<Long> cids) {
        brandMapper.updateByPrimaryKeySelective(brand);
        int i = -1;
        brandMapper.deleteBrandAndCategroy(brand.getId());
        for (Long cid : cids) {
            i = brandMapper.insertBrandAndCategory(cid, brand.getId());
        }
        return i;
    }

    /**
     * 删除品牌
     *
     * @param bids 品牌id的集合
     * @return 受影响行数
     */
    public int deleteBrand(List<Long> bids) {
        int result1 = -1;
        int result2 = -1;
        for (Long bid : bids) {
            result1 = brandMapper.deleteByPrimaryKey(bid);
            result2 = brandMapper.deleteBrandAndCategroy(bid);
        }
        if (result1 < 0 || result2 < 0) {
            return -1;
        }
        return 1;
    }

    /**
     * 查询品牌名称
     *
     * @param cid 分类id
     * @return 品牌分类
     */
    public List<Brand> queryBrandById(Long cid) {
        List<Brand> brands = brandMapper.queryBrandById(cid);
        return brands;
    }
}