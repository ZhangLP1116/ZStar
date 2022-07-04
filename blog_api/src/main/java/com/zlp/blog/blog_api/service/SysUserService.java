package com.zlp.blog.blog_api.service;

import com.zlp.blog.blog_api.dao.pojo.SysUser;
import com.zlp.blog.blog_api.dto.LoginParams;
import com.zlp.blog.blog_api.dto.Result;
import org.apache.commons.lang3.StringUtils;
import sun.misc.Request;

public interface SysUserService {
    SysUser findSysUserByID(long sysUserId);

    SysUser findUser(String account,String password);

    Result findSysUserByToken(String token);
}
