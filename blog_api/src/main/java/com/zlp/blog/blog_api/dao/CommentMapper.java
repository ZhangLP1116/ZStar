package com.zlp.blog.blog_api.dao;

import com.zlp.blog.blog_api.dao.pojo.Comment;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface CommentMapper {

    List<Comment> findArticleCommentsById(long id);

    List<Comment> findCommentsByParentId(long parent_id);

    void insertComment(Comment comment);
}
