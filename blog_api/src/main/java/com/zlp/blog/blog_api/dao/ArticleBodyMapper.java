package com.zlp.blog.blog_api.dao;

import com.zlp.blog.blog_api.dao.pojo.ArticleBody;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface ArticleBodyMapper {
    ArticleBody findArticleBodyById(long id);

    void insert(ArticleBody articleBody);
}
