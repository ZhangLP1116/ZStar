package com.zlp.blog.blog_api.dao.pojo;

import lombok.Data;

@Data
public class Category {
    private int id;
    private String avatar;
    private String category_name;
    private String description;
}
