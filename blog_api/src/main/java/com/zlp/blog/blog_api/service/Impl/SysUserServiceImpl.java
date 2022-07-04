package com.zlp.blog.blog_api.service.Impl;

import com.alibaba.fastjson.JSON;
import com.zlp.blog.blog_api.dao.SysUserMapper;
import com.zlp.blog.blog_api.dao.pojo.SysUser;
import com.zlp.blog.blog_api.dto.ErrorCode;
import com.zlp.blog.blog_api.dto.LoginParams;
import com.zlp.blog.blog_api.dto.LoginUserDto;
import com.zlp.blog.blog_api.dto.Result;
import com.zlp.blog.blog_api.service.SysUserService;
import com.zlp.blog.blog_api.service.TokenCheckService;
import com.zlp.blog.blog_api.utils.JWTUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import sun.misc.Request;

import java.util.Map;

@Service
public class SysUserServiceImpl implements SysUserService {
    @Autowired
    SysUserMapper sysUserMapper;
    @Autowired
    RedisTemplate<String, String> redisTemplate;
    @Autowired
    TokenCheckService tokenCheckService;

    @Override
    public SysUser findSysUserByID(long id) {
        SysUser sysUser = sysUserMapper.findSysUserByID(id);
        return sysUser;
    }

    @Override
    public SysUser findUser(String account,String password) {
        SysUser sysUser = sysUserMapper.findUser(account,password);
        return sysUser;
    }

    /**
     * 根据token从redis中获取用户信息
     * 1. 判断token是否合法
     * 2. 失败返回错误信息
     * 3. 成功则返回LoginUserDto对象
     * @param token
     * @return
     */
    @Override
    public Result findSysUserByToken(String token) {
        if (tokenCheckService.tokenCheck(token)){
            String res = redisTemplate.opsForValue().get("TOKEN_" + token);
            if(StringUtils.isBlank(res)){
                return Result.fail(ErrorCode.TOKEN_ERROR.getMsg(), ErrorCode.TOKEN_ERROR.getCode());
            }
            SysUser sysUser = JSON.parseObject(res, SysUser.class);
            LoginUserDto loginUserDto = new LoginUserDto();
            BeanUtils.copyProperties(sysUser,loginUserDto);
            return Result.success(loginUserDto);
        }
        return Result.fail(ErrorCode.TOKEN_ERROR.getMsg(),ErrorCode.TOKEN_ERROR.getCode());
    }
}
