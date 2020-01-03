package cn.changyou.item.mapper;

import cn.changyou.item.pojo.Category;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * @author xgl
 * @create 2019-12-12 17:32
 */
public interface CategoryMapper extends Mapper<Category> {
    @Select("SELECT * FROM cy_category WHERE id IN (SELECT category_id FROM cy_category_brand WHERE brand_id = #{bid})")
    List<Category> queryByBrandId(Long bid);
}
