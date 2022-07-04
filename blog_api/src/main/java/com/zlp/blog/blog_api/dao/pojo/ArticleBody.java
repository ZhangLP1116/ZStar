package com.zlp.blog.blog_api.dao.pojo;

import lombok.Data;

@Data
public class ArticleBody {
    private long id;
    private String content;
    private String content_html;
    private long article_id;
}
