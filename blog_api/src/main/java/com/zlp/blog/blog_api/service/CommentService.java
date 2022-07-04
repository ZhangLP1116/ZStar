package com.zlp.blog.blog_api.service;

import com.zlp.blog.blog_api.dto.CommentParams;
import com.zlp.blog.blog_api.dto.Result;

public interface CommentService {
    Result findArticleComments(long id);

    Result createComment(CommentParams commentParams);
}
