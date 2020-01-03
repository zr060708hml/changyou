package cn.changyou.item.service;

import cn.changyou.common.pojo.PageResult;
import cn.changyou.item.mapper.BrandMapper;
import cn.changyou.item.pojo.Brand;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

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

    public PageResult<Brand> querySpuByPage(String key, Boolean desc, Integer page, Integer rows, String sortBy) {
        Example example = new Example(Brand.class);
        Example.Criteria criteria = example.createCriteria();

        if (StringUtils.isEmpty(key)) {
            criteria.andLike("name", "%" + key + "%").orEqualTo("letter", key);
        }
        PageHelper.startPage(page, rows);
        if (StringUtils.isNotBlank(sortBy)) {
            example.setOrderByClause(sortBy + " " + (desc ? "desc" : "asc"));
        }
        List<Brand> brands = brandMapper.selectByExample(example);
        PageInfo<Brand> pageInfo = new PageInfo<>(brands);
        return new PageResult<>(pageInfo.getTotal(), pageInfo.getList());
    }

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

    public int updateBrand(Brand brand, List<Long> cids) {
        brandMapper.updateByPrimaryKeySelective(brand);
        int i = -1;
        brandMapper.deleteBrandAndCategroy(brand.getId());
        for (Long cid : cids) {
            i = brandMapper.insertBrandAndCategory(cid, brand.getId());
        }
        return i;
    }

    public int deleteBrand(Long bid) {
        int result1 = brandMapper.deleteByPrimaryKey(bid);
        int result2 = brandMapper.deleteBrandAndCategroy(bid);
        if (result1 < 0 || result2 < 0) {
            return -1;
        }
        return 1;
    }
}