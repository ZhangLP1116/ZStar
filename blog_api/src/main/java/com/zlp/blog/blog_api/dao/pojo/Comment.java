package com.zlp.blog.blog_api.dao.pojo;

import lombok.Data;

@Data
public class Comment {
    private long id;
    private String content;
    private long create_date;
    private long article_id;
    private long author_id;
    private Long parent_id;
    private long to_uid;
    private long level;
}
