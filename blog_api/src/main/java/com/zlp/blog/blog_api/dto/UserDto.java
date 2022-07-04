package com.zlp.blog.blog_api.dto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;

@Data
public class UserDto {
    @JsonSerialize(using = ToStringSerializer.class)
    private long id;
    private String nickname;
    private String avatar;
}
