package cn.changyou.comment.service;

import cn.changyou.comment.client.SpuClient;
import cn.changyou.comment.client.UserClient;
import cn.changyou.comment.mapper.CommentMapper;
import cn.changyou.common.pojo.PageResult;
import cn.changyou.item.pojo.Spu;
import cn.changyou.order.service.OrderService;
import cn.changyou.pojo.Comment;
import cn.changyou.user.pojo.User;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.*;

import java.util.ArrayList;
import java.util.List;

/**
 * @author GM
 * @create 2020-01-08 14:29
 */
@Service
public class CommentService {
    @Autowired
    private CommentMapper commentMapper;

    @Autowired
    private SpuClient spuClient;

    @Autowired
    private UserClient userClient;

    @Autowired
    private OrderService orderService;

    /**
     * 发布评论和回复
     *
     * @param comment 用户输入的评论或回复
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public Boolean addRemake(Comment comment) {
        if (comment.getParent()) {
            Example example = new Example(Comment.class);
            Criteria criteria = example.createCriteria();
            criteria.andEqualTo("userId", comment.getUserId());
            criteria.andEqualTo("spuId", comment.getSpuId());
            criteria.andEqualTo("orderId", comment.getOrderId());
            Comment comment2 = commentMapper.selectOneByExample(example);
            if (comment2 != null) {
                return false;
            }
        }
//        修改订单状态
        orderService.updateOrderStatusById(comment.getOrderId());
//        计算某条评论的回复数量
        if (!comment.getParent()) {
            Long replyNum = commentMapper.selectById(comment.getParentId());
            Long l = replyNum + 1;
            Comment comment1 = new Comment();
            comment1.setId(comment.getParentId());
            comment1.setReplyNum(l);
            commentMapper.updateByPrimaryKeySelective(comment1);
        }
        int i = commentMapper.insert(comment);
        //计算某个商品的评论数量
        if (i > 0) {
            Spu spu = new Spu();
            spu.setId(comment.getSpuId());
            spuClient.updateByRemakNum(spu);
        }
        return true;
    }


    /**
     * 查看某个商品的评论或者回复
     *
     * @param spuId 商品spuid
     * @return 评论或回复的集合
     */
    public PageResult queryCommentByIds(Long spuId, int page, int rows, Boolean parent, Long parentId) {
        PageHelper.startPage(page, rows);
        Comment comment = new Comment();
        comment.setSpuId(spuId);
        comment.setParent(parent);
        comment.setParentId(parentId);
        Example example = new Example(Comment.class);
        Criteria criteria = example.createCriteria();
        criteria.andEqualTo(comment);
        List<Comment> comments = commentMapper.selectByExample(example);
        List<Long> ids = new ArrayList<>();
        for (Comment comment1 : comments) {
            Long id = comment1.getUserId();
            ids.add(id);
        }
        System.out.println("我是ids" + ids);
        List<User> userList = userClient.queryById(ids);
        System.out.println(userList);
        for (User u : userList) {
            System.out.println(u);
            for (Comment comment1 : comments) {
                if (comment1.getUserId().equals(u.getId())) {
                    comment1.setNickName(u.getUsername());
                    comment1.setRemakeImg(u.getImg());
                }
            }
        }
        PageInfo<Comment> pageInfo = new PageInfo<>(comments);
        return new PageResult(pageInfo.getTotal(),comments);
    }

    /**
     * 通过用户id查询该用户的评论
     *
     * @param id
     * @return
     */
    public PageResult queryCommentById(Long id) {

        return null;
    }

    /**
     * 删除评论
     *
     * @param remakeId
     */
    public void deleteCommentById(Long remakeId) {
        Example example = new Example(Comment.class);
        Criteria criteria = example.createCriteria();
        criteria.andEqualTo("parentId",remakeId);
        Comment comment2 = new Comment();
        comment2.setId(remakeId);
        Comment comment = commentMapper.selectByPrimaryKey(comment2);
        System.out.println(comment);
        if (comment.getParent()){
            Long spuId = comment.getSpuId();
            spuClient.deleteCommentById(spuId);
        } else {
            Long replyNum = comment.getReplyNum() - 1;
            Comment comment1 = new Comment();
            comment1.setId(comment.getId());
            comment1.setReplyNum(replyNum);
            commentMapper.updateByPrimaryKeySelective(comment1);
        }
        commentMapper.deleteByExample(example);
        int i = commentMapper.deleteByPrimaryKey(remakeId);
    }
}
