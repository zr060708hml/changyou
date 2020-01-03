package cn.changyou.item.service;

import cn.changyou.item.mapper.CategoryMapper;
import cn.changyou.item.pojo.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

/**
 * @author xgl
 * @create 2019-12-12 17:36
 */
@Service
public class CategoryService {
    @Autowired
    private CategoryMapper categoryMapper;
    /**
     * 根据parentId查询子类目
     *  @param pid
     *  @return
     */
    public List<Category> queryCategoriesByPid(Long pid) {
        Category record = new Category();
        record.setParentId(pid);
        return this.categoryMapper.select(record);
    }
    /**
     * 新增商品分类
     * @param category
     */
    public void saveCategory(Category category){
        this.categoryMapper.insertSelective(category);
    }
    /**
     * 删除商品分类
     * @param id
     */
    public void deleteCategory(Long id){
        this.categoryMapper.deleteByPrimaryKey(id);
    }
    /**
     * 修改商品分类
     * @param category
     */
    public void updataCategory(Category category){
        this.categoryMapper.updateByPrimaryKeySelective(category);
    }

    public Category getCategory(Long id) {
        return this.categoryMapper.selectByPrimaryKey(id);
    }
}


