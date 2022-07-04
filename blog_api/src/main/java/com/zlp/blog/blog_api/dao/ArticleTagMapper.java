package com.zlp.blog.blog_api.dao;

import com.zlp.blog.blog_api.dao.pojo.ArticleTag;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface ArticleTagMapper {
    void insert(ArticleTag articleTag);

    List<Long> findArticleIdListByTagId(long id);
}
