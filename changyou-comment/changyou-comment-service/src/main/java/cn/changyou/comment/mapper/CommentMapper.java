package cn.changyou.comment.mapper;

import cn.changyou.pojo.Comment;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;

/**
 * @author GM
 * @create 2020-01-08 14:27
 */
public interface CommentMapper extends Mapper<Comment>{

    @Select("SELECT cr.reply_num FROM cy_remake cr WHERE id = #{id}")
    Long selectById(@Param("id") Long id);
}
