package com.zlp.blog.blog_api.service;

import com.zlp.blog.blog_api.dto.ArticleParams;
import com.zlp.blog.blog_api.dto.PageParams;
import com.zlp.blog.blog_api.dto.Result;

public interface ArticleService {
    Result listArticle(PageParams pageParams);

    Result hotArticles(int limit);

    Result newArticles(int limit);

    Result listArchives();

    Result findArticle(long id);

    Result publish(ArticleParams articleParams);

}

