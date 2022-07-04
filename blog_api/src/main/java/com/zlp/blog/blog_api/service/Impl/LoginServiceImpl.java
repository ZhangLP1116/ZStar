package com.zlp.blog.blog_api.service.Impl;

import com.alibaba.fastjson.JSON;
import com.zlp.blog.blog_api.dao.SysUserMapper;
import com.zlp.blog.blog_api.dao.pojo.SysUser;
import com.zlp.blog.blog_api.dto.ErrorCode;
import com.zlp.blog.blog_api.dto.LoginParams;
import com.zlp.blog.blog_api.dto.Result;
import com.zlp.blog.blog_api.service.LoginService;
import com.zlp.blog.blog_api.service.SysUserService;
import com.zlp.blog.blog_api.service.TokenCheckService;
import com.zlp.blog.blog_api.utils.JWTUtils;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.concurrent.TimeUnit;


@Service
@Transactional
public class LoginServiceImpl implements LoginService {

    @Autowired
    SysUserService sysUserService;
    @Autowired
    RedisTemplate<String, String> redisTemplate;
    @Autowired
    TokenCheckService tokenCheckService;
    @Autowired
    SysUserMapper sysUserMapper;

    private static final String slat = "mszlu!@#";

    /**
     * 登录功能
     * 1. 检查参数合法
     * 2. 根据账号查询数据库，并验证密码是否正确
     * 3. 不正确则登录失败
     * 4. 正确则使用JWT生成token，返回给前端
     * 5. 将token保存在redis中并设置过期时间，token：user信息 time
     * 访问需要登录的页面时先检查token，若存在且正确则响应数据，若不存在或不正确则返回失败信息
     * @param loginParams
     * @return
     */
    @Override
    public Result login(LoginParams loginParams) {
        String account  = loginParams.getAccount();
        String password = loginParams.getPassword();
        if (StringUtils.isBlank(account) || StringUtils.isBlank(password)){
            return Result.fail(ErrorCode.PARAMS_ERROR.getMsg(),ErrorCode.PARAMS_ERROR.getCode());
        }
        password = DigestUtils.md5Hex(password+slat);
        SysUser sysUser = sysUserService.findUser(account,password);
        if (sysUser == null){
            return Result.fail(ErrorCode.ACCOUNT_PWD_NOT_EXIST.getMsg(),ErrorCode.ACCOUNT_PWD_NOT_EXIST.getCode());
        }
        String token = JWTUtils.createToken(sysUser.getId());
        redisTemplate.opsForValue().set("TOKEN_"+token, JSON.toJSONString(sysUser),1, TimeUnit.DAYS);
        return Result.success(token);
    }

    /**
     * 删除Redis中的token实现退出
     * @param token
     * @return
     */
    @Override
    public Result logout(String token) {
        if (StringUtils.isBlank(token)){
            return Result.fail(ErrorCode.TOKEN_ERROR.getMsg(),ErrorCode.TOKEN_ERROR.getCode());
        }
        redisTemplate.delete("TOKEN_"+token);
        return Result.success(null);
    }

    /**
     * 注册功能
     * 0. 检查参数合法，查询数据库中是否已经存在
     * 1. 写入数据库
     * 2. 创建token
     * 3. 保存在redis中
     * 4. 返回redis
     * 5. 注册过程需要事务性
     * @param loginParams
     * @return
     */
    @Override
    public Result register(LoginParams loginParams) {
        if (StringUtils.isBlank(loginParams.getAccount())
        || StringUtils.isBlank(loginParams.getNickname())
        || StringUtils.isBlank(loginParams.getPassword()))
        {
            return Result.fail(ErrorCode.PARAMS_ERROR.getMsg(),ErrorCode.PARAMS_ERROR.getCode());
        }
        SysUser sysUser = sysUserMapper.findUserByAccount(loginParams.getAccount());
        if (sysUser!=null){
            return Result.fail(ErrorCode.ACCOUNT_EXIST.getMsg(),ErrorCode.ACCOUNT_EXIST.getCode());
        }
        sysUser = new SysUser();
        // sysUser.setId(); ID生成
        sysUser.setAccount(loginParams.getAccount());
        sysUser.setNickname(loginParams.getNickname());
        sysUser.setPassword(DigestUtils.md5Hex(loginParams.getPassword()+slat));
        sysUser.setAdmin(1);
        sysUser.setCreate_date(System.currentTimeMillis());
        sysUser.setLast_login(System.currentTimeMillis());
        sysUser.setDeleted(0);
        sysUserMapper.saveUser(sysUser);
        String token = JWTUtils.createToken(sysUser.getId());
        redisTemplate.opsForValue().set("TOKEN_"+token, JSON.toJSONString(sysUser),1, TimeUnit.DAYS);
        return Result.success(token);
    }

}
