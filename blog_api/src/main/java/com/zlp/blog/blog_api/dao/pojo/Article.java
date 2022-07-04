package com.zlp.blog.blog_api.dao.pojo;

import lombok.Data;

@Data
public class Article {
    public static final int Article_TOP = 1;

    public static final int Article_Common = 0;

    private long id;
    private int comment_counts;
    private long create_date;
    private String summary;
    private String title;
    private int view_counts;
    private int weight = Article_Common;
    private long author_id;
    private long body_id;
    private int category_id;
}
