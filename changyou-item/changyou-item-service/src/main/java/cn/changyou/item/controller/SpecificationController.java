package cn.changyou.item.controller;

import cn.changyou.item.pojo.SpecGroup;
import cn.changyou.item.pojo.SpecParam;
import cn.changyou.item.service.SpecificationSevice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author hashen
 * @create 2020-01-02 15:32
 */
@RestController
@RequestMapping("spec")
public class SpecificationController {
    @Autowired
    private SpecificationSevice specificationSevice;
    /**
     * 根据商品分类id查询分组
     * @param cid
     * @return
     */
    @GetMapping("groups/{cid}")
    public ResponseEntity<List<SpecGroup>> queryGroupById(@PathVariable("cid")Long cid) {
        List<SpecGroup> groups = this.specificationSevice.queryGroupById(cid);
        if (CollectionUtils.isEmpty(groups)) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(groups);
    }
    /**
     * 新增规格组specGroup
     * @param specGroup
     */
    @PostMapping
    public ResponseEntity<Void> saveSpecGroup(@RequestBody SpecGroup specGroup){
        specificationSevice.saveSpecGroup(specGroup);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
    /**
     * 根据规格组Id删除分组
     * @param gid
     */
    @DeleteMapping
    public ResponseEntity<Void> deleteSpecGroup(@RequestParam("gid")long gid){
        specificationSevice.deleteSpecGroup(gid);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
    /**
     * 修改规格组信息
     * @param specGroup
     */
    @PutMapping
    public ResponseEntity<Void> updateSpecGroup(@RequestBody SpecGroup specGroup){
        specificationSevice.updatespecGroup(specGroup);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    /**
     * 根据规格组的id查询具体参数
     * @param gid
     * @return
     */
    @GetMapping("params")
    public ResponseEntity<List<SpecParam>> queryParamById(@RequestParam("gid")Long gid) {
        List<SpecParam> params = this.specificationSevice.queryParamById(gid);
        if(CollectionUtils.isEmpty(params)){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(params);
    }
    /**
     * 新增参数信息
     * @param specParam
     */
    @PostMapping("params")
    public ResponseEntity<Void> saveSpecParam(@RequestBody SpecParam specParam){
        specificationSevice.saveSpecParam(specParam);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
    /**
     * 修改参数信息
     * @param specParam
     */
    @PutMapping("params")
    public ResponseEntity<Void> updateSpecparam(@RequestBody SpecParam specParam){
        specificationSevice.updateSpecParam(specParam);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
    /**
     * 删除参数信息
     * @param id
     */
    @DeleteMapping("params")
    public ResponseEntity<Void> deleteSpecParam(@RequestParam("id")long id){
        specificationSevice.deleteSpecParam(id);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
