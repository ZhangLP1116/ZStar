package com.zlp.blog.blog_api.dto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;

@Data
public class CategoryDto {
    private int id;
    private String avatar;
    private String category_name;
    private String description;
}
