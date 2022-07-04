package com.zlp.blog.blog_api.dao.pojo;

import lombok.Data;

@Data
public class ArticleTag {
    private long id;
    private long article_id;
    private long tag_id;
}
