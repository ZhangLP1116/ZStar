package com.zlp.blog.blog_api.service;

import com.zlp.blog.blog_api.dto.Result;
import com.zlp.blog.blog_api.dto.TagDto;

import java.util.List;

public interface TagService {
    List<TagDto> findTagsByArticleID(long articleId);

    Result hots(int limit);

    Result tags();

    Result findTagById(long id);
}
