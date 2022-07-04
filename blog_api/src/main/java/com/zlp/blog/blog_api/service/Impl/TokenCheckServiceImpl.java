package com.zlp.blog.blog_api.service.Impl;

import com.zlp.blog.blog_api.service.TokenCheckService;
import com.zlp.blog.blog_api.utils.JWTUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class TokenCheckServiceImpl implements TokenCheckService {

    @Override
    public boolean tokenCheck(String token) {
        Map<String, Object> res = JWTUtils.checkToken(token);
        return res != null;
    }
}
