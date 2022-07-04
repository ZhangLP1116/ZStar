package com.zlp.blog.blog_api.service;

import com.zlp.blog.blog_api.dto.LoginParams;
import com.zlp.blog.blog_api.dto.Result;

public interface LoginService {
    Result login(LoginParams loginParams);

    Result logout(String token);

    Result register(LoginParams loginParams);
}
