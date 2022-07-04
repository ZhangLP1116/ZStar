package com.zlp.blog.blog_api.dto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;

import java.util.List;

@Data
public class ArticleDto {
    @JsonSerialize(using = ToStringSerializer.class)
    private long id;

    private String title;

    private String summary;

    private int comment_counts;

    private int view_counts;

    private int weight;
    /**
     * 创建时间
     */
    private String create_date;

    private String author;

    private ArticleBodyDto body;

    private List<TagDto> tags;

    private CategoryDto categoryDto;


}
