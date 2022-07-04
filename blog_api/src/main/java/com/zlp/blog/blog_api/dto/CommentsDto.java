package com.zlp.blog.blog_api.dto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;

import java.util.List;

@Data
public class CommentsDto {
    @JsonSerialize(using = ToStringSerializer.class)
    private long id;
    private UserDto author;
    private String content;
    private String create_date;
    private long level;
    private List<CommentsDto> childrens;
    private UserDto toUser;
}
