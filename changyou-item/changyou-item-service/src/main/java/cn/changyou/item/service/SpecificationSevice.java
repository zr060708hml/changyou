package cn.changyou.item.service;

import cn.changyou.item.mapper.SpecGroupMapper;
import cn.changyou.item.mapper.SpecParamMapper;
import cn.changyou.item.pojo.SpecGroup;
import cn.changyou.item.pojo.SpecParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PutMapping;

import java.util.List;
import java.util.function.LongConsumer;

/**
 * @author hashen
 * @create 2020-01-02 15:33
 */
@Service
public class SpecificationSevice {
    @Autowired
    private SpecGroupMapper specGroupMapper;
    @Autowired
    private SpecParamMapper specParamMapper;

    /**
     * 根据商品分类id查询分组
     *
     * @param cid
     * @return
     */
    public List<SpecGroup> queryGroupById(long cid) {
        SpecGroup specGroup = new SpecGroup();
        specGroup.setCid(cid);
        return this.specGroupMapper.select(specGroup);
    }

    /**
     * 新增规格组
     *
     * @param specGroup
     */
    public void saveSpecGroup(SpecGroup specGroup) {
        this.specGroupMapper.insertSelective(specGroup);
    }

    /**
     * 根据规格组Id删除分组
     *
     * @param gid
     */
    public void deleteSpecGroup(long gid) {
        this.specGroupMapper.deleteByPrimaryKey(gid);
    }

    /**
     * 修改规格组信息
     *
     * @param specGroup
     */
    public void updatespecGroup(SpecGroup specGroup) {
        this.specGroupMapper.updateByPrimaryKeySelective(specGroup);
    }
    /**
     * 根据规格组的id查询具体参数
     *
     * @param gid
     * @return
     */
    public List<SpecParam> queryParamById(Long gid, Long cid) {
        SpecParam specParam = new SpecParam();
        specParam.setGroupId(gid);
        specParam.setCid(cid);
        return this.specParamMapper.select(specParam);
    }

    /**
     * 新增参数信息
     *
     * @param specParam
     */
    public void saveSpecParam(SpecParam specParam) {
        this.specParamMapper.insertSelective(specParam);
    }

    /**
     * 修改参数信息
     *
     * @param specParam
     */
    public void updateSpecParam(SpecParam specParam) {
        this.specParamMapper.updateByPrimaryKey(specParam);
    }

    /**
     * 删除参数信息
     *
     * @param id
     */
    public void deleteSpecParam(Long id) {
        this.specParamMapper.deleteByPrimaryKey(id);
    }

}
