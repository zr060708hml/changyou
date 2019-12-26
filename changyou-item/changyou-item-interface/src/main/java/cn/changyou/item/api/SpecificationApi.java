package cn.changyou.item.api;

import cn.changyou.item.pojo.SpecParam;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @author xgl
 * @create 2019-12-19 22:57
 */
@RequestMapping("spec")
public interface SpecificationApi {


    /**
     * 通过组id查询具体的参数列表
     * @param gid
     * @return
     */
    @GetMapping("params")
    public List<SpecParam> queryParamsByGid(
            @RequestParam(value = "gid", required = false) Long gid,
            @RequestParam(value = "cid", required = false) Long cid,
            @RequestParam(value = "generic", required = false) Boolean generic,
            @RequestParam(value = "searching", required = false) Boolean searching
    );
}
