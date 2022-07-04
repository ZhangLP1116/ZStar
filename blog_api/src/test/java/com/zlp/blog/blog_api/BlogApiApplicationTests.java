package com.zlp.blog.blog_api;

import com.zlp.blog.blog_api.dao.ArticleMapper;
import com.zlp.blog.blog_api.dao.pojo.Article;
import com.zlp.blog.blog_api.dto.PageParams;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class BlogApiApplicationTests {
    @Autowired
    ArticleMapper articleMapper;

    @Test
    void contextLoads() {
        PageParams pageParams = new PageParams();
        pageParams.setPage(0);
        List<Article> articles = articleMapper.ListArticle(pageParams);
        articles.forEach(System.out::println);
    }

}
