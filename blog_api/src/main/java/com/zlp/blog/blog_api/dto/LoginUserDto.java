package com.zlp.blog.blog_api.dto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;

@Data
public class LoginUserDto {
    @JsonSerialize(using = ToStringSerializer.class)
    private long id;
    private String account;
    private String nickname;
    private String avatar;
}
