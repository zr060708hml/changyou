package cn.changyou.comment.controller;

import cn.changyou.comment.service.CommentService;
import cn.changyou.common.pojo.PageResult;
import cn.changyou.pojo.Comment;
import cn.changyou.user.api.QureyApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import java.util.List;

/**
 * @author GM
 * @create 2020-01-08 14:29
 */
@Controller
@RequestMapping("/comment")
public class CommentController {
    @Autowired
    private CommentService commentService;


    /**
     * 发布评论
     *
     * @param comment 一条评论的信息
     * @return 状态码
     */
    @PostMapping("publish")
    public ResponseEntity<Void> PublishComment(@RequestBody Comment comment) {
        Boolean i = commentService.addRemake(comment);
        if (!i){
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok().build();
    }

    /**
     * 查看某个商品的评论
     *
     * @param spuId 商品spuid
     * @return 评论及回复的集合
     */
    @GetMapping("get/{id}")
    public ResponseEntity<PageResult<List<Comment>>> queryCommentById(@PathVariable("id")  Long spuId,@RequestParam(value = "page", defaultValue = "1") int page,@RequestParam(value = "rows", defaultValue = "25")int rows,@RequestParam("parent") Boolean parent,@RequestParam(value = "parentId",required = false) Long parentId) {
        PageResult result = commentService.queryCommentByIds(spuId, page, rows, parent, parentId);
        return ResponseEntity.ok(result);
    }

    /**
     * 删除某条评论
     * @param remakeId
     * @return
     */
    @DeleteMapping("delete")
    public ResponseEntity<Void> deleteCommentById(@RequestParam("id")Long remakeId){
        commentService.deleteCommentById(remakeId);
        return ResponseEntity.ok().build();
    }

}
