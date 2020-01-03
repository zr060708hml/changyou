package cn.changyou.item.mapper;

import cn.changyou.item.pojo.Brand;
import org.apache.ibatis.annotations.*;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * @author GM
 * @create 2019-12-26 17:11
 */
public interface BrandMapper extends Mapper<Brand> {
    /**
     * 在品牌和分类的中间表中添加数据
     *
     * @param cid 分类id
     * @param bid 品牌id
     * @return 受影响行数
     */
    @Insert("INSERT INTO cy_category_brand(category_id, brand_id) VALUES (#{cid},#{bid})")
    int insertBrandAndCategory(@Param("cid") Long cid, @Param("bid") Long bid);

    /**
     * 删除分类表中数据
     * @param bid
     * @return
     */
    @Delete("DELETE FROM cy_category_brand WHERE brand_id = #{bid}")
    int deleteBrandAndCategroy(@Param("bid") Long bid);
}
