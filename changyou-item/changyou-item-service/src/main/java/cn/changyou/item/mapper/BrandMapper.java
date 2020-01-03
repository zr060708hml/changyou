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

    @Insert("INSERT INTO cy_category_brand(category_id, brand_id) VALUES (#{cid},#{bid})")
    int insertBrandAndCategory(@Param("cid") Long cid, @Param("bid") Long bid);

    @Update("UPDATE cy_category_brand SET category_id = #{cid2}  WHERE category_id = #{cid1} and brand_id = #{bid}")
    int updateBrandAndCategory(@Param("cid1") Long cid1,@Param("cid2")Long cid2, @Param("bid") Long bid);

    @Delete("DELETE FROM cy_category_brand WHERE brand_id = #{bid}")
    int deleteBrandAndCategroy(@Param("bid") Long bid);

}
