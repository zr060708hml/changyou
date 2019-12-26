package cn.changyou.item.api;

import cn.changyou.item.pojo.Brand;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author xgl
 * @create 2019-12-25 15:15
 */
@RequestMapping("brand")
public interface BrandApi {
    @GetMapping("{id}")
    public Brand queryBrandById(@PathVariable("id") Long id);
}
