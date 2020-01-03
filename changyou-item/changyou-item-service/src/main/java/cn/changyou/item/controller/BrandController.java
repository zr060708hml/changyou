package cn.changyou.item.controller;

import cn.changyou.common.pojo.PageResult;
import cn.changyou.item.pojo.Brand;
import cn.changyou.item.service.BrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author GM
 * @create 2019-12-26 17:12
 */
@Controller
@RequestMapping("/brand")
public class BrandController {

    @Autowired
    private BrandService brandservice;

    public void setBrandservice(BrandService brandservice) {
        this.brandservice = brandservice;
    }

    @GetMapping("page")
    public ResponseEntity<PageResult<Brand>> queryBrandsBypage(@RequestParam(value = "key", required = false) String key, @RequestParam(value = "page", defaultValue = "1") Integer page, @RequestParam(value = "rows", defaultValue = "5") Integer rows, @RequestParam(value = "sortBy", required = false) String sortBy, @RequestParam(value = "desc", required = false) Boolean desc) {

        PageResult<Brand> result = this.brandservice.querySpuByPage(key, desc, page, rows, sortBy);
        if (CollectionUtils.isEmpty(result.getItems())) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(result);
    }

    @PostMapping
    public ResponseEntity<Void> saveBrand(Brand brand, @RequestParam("cids") List<Long> cids) {

        int i = this.brandservice.saveBrand(brand, cids);
        if (i <= 0) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PutMapping
    public ResponseEntity<Void> updateBrand(@RequestBody Brand brand, @RequestParam("cids") List<Long> cids) {
        int i = this.brandservice.updateBrand(brand, cids);
        if (i < 0) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteBrand(@RequestParam("bid") Long bid) {
        int i = this.brandservice.deleteBrand(bid);
        if (i < 0) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
