package cn.changyou.item.mapper;

import cn.changyou.item.pojo.SpecParam;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import sun.security.provider.certpath.CertId;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * @author hashen
 * @create 2020-01-03 12:24
 */
public interface SpecParamMapper extends Mapper<SpecParam> {
    @Select("SELECT cy_spec_param.cid,cy_spec_param.group_id,cy_spec_param.`name`,cy_spec_param.`numeric`,cy_spec_param.unit,cy_spec_param.generic,cy_spec_param.searching,cy_spec_param.id FROM cy_spec_param WHERE cid = #{cid} ")
    List<SpecParam> queryParamByCid(@Param("cid")Long cid);
}
