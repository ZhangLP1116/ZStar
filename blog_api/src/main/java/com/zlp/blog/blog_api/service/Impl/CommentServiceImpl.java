package com.zlp.blog.blog_api.service.Impl;

import com.zlp.blog.blog_api.dao.CommentMapper;
import com.zlp.blog.blog_api.dao.pojo.Comment;
import com.zlp.blog.blog_api.dao.pojo.SysUser;
import com.zlp.blog.blog_api.dto.CommentParams;
import com.zlp.blog.blog_api.dto.CommentsDto;
import com.zlp.blog.blog_api.dto.Result;
import com.zlp.blog.blog_api.dto.UserDto;
import com.zlp.blog.blog_api.service.CommentService;
import com.zlp.blog.blog_api.service.SysUserService;
import com.zlp.blog.blog_api.utils.UserThreadLocal;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {
    @Autowired
    CommentMapper commentMapper;
    @Autowired
    SysUserService sysUserService;

    /**
     * 获取文章评论
     * 1. 根据文章id获得所有评论数据
     * 2. 根据评论中的作者id查询作者信息
     * 3. 根据to_uid查询被评论人信息
     * 4. 根据评论层级信息，整合数据到CommentDto中
     * @param id
     * @return
     */
    @Override
    public Result findArticleComments(long id) {
        List<Comment> commentList =  commentMapper.findArticleCommentsById(id);
        List<CommentsDto> commentsDtoList = copyList(commentList);
        return Result.success(commentsDtoList);
    }


    private List<CommentsDto> copyList(List<Comment> commentList) {
        List<CommentsDto> commentsDtoList= new ArrayList<>();
        for (Comment comment : commentList) {
            commentsDtoList.add(copy(comment));
        }
        return commentsDtoList;
    }

    private CommentsDto copy(Comment comment) {
        CommentsDto commentsDto = new CommentsDto();
        BeanUtils.copyProperties(comment,commentsDto);
        // 设置作者信息
        SysUser anchor = sysUserService.findSysUserByID(comment.getAuthor_id());
        UserDto anchorDto = new UserDto();
        BeanUtils.copyProperties(anchor,anchorDto);
        commentsDto.setAuthor(anchorDto);
        // 设置子评论信息
        if (comment.getLevel()==1){
            List<CommentsDto> commentsDtoList = findCommentsByParentId(comment.getId());
            commentsDto.setChildrens(commentsDtoList);
        }
        // 设置子评论对象信息
        if (comment.getLevel()>1){
            SysUser to_user = sysUserService.findSysUserByID(comment.getTo_uid());
            UserDto to_userDto = new UserDto();
            BeanUtils.copyProperties(to_user,to_userDto);
            commentsDto.setToUser(to_userDto);
        }
        return commentsDto;
    }

    /**
     * 查询子评论
     * @param parent_id
     * @return
     */
    private List<CommentsDto> findCommentsByParentId(long parent_id) {
        List<Comment> comment = commentMapper.findCommentsByParentId(parent_id);
        List<CommentsDto> commentsDtoList = copyList(comment);
        return commentsDtoList;
    }

    /**
     * 创建评论
     * @param commentParams
     * @return
     */
    @Override
    public Result createComment(CommentParams commentParams) {
        Comment comment = new Comment();
        SysUser author = UserThreadLocal.get();
        comment.setArticle_id(commentParams.getArticleId());
        comment.setContent(commentParams.getContent());
        comment.setCreate_date(System.currentTimeMillis());
        comment.setAuthor_id(author.getId());
        Long parent = commentParams.getParent();
        if (parent == null || parent == 0){
            comment.setLevel(1);
            comment.setParent_id(0L);
        }
        else{
            comment.setLevel(2);
            comment.setParent_id(parent);
            comment.setTo_uid(commentParams.getToUserId());
        }
        commentMapper.insertComment(comment);
        return Result.success(null);
    }
}
