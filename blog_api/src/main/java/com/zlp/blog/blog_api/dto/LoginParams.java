package com.zlp.blog.blog_api.dto;

import lombok.Data;

@Data
public class LoginParams {
    private String account;
    private String password;
    private String nickname;
}
