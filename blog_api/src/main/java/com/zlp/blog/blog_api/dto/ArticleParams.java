package com.zlp.blog.blog_api.dto;


import lombok.Data;

import java.util.List;

@Data
public class ArticleParams {
    private long id;
    private String title;
    private ArticleBodyParams body;
    private CategoryDto category;
    private String summary;
    private List<TagDto> tags;
}
