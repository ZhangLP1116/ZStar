package com.zlp.blog.blog_api.controller;


import com.zlp.blog.blog_api.dto.Result;
import com.zlp.blog.blog_api.service.SysUserService;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import sun.misc.Request;

@RestController
@RequestMapping("/api/users")
public class UserController {
    @Autowired
    SysUserService sysUserService;

    @GetMapping("/currentUser")
    public Result currentUser(@RequestHeader("Authorization") String token){
        return sysUserService.findSysUserByToken(token);
    }
}
