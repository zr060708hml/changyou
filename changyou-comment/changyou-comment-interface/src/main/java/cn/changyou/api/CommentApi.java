package cn.changyou.api;

import cn.changyou.common.pojo.PageResult;
import cn.changyou.pojo.Comment;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author GM
 * @create 2020-01-08 14:08
 */
@RequestMapping("/comment")
public interface CommentApi {

    @PostMapping("publish")
    ResponseEntity<Void> PublishComment(@RequestBody Comment comment);

    @GetMapping("get/{id}")
    List<Comment> queryCommentById(@PathVariable("id")  Long spuId,@RequestParam(value = "page", defaultValue = "1") int page,@RequestParam(value = "rows", defaultValue = "25")int rows,@RequestParam("parent") Boolean parent,@RequestParam(value = "parentId",required = false) Long parentId);


    @DeleteMapping("delete")
    ResponseEntity<Void> deleteCommentById(@RequestParam("id")Long remakeId);
}
