package com.zlp.blog.blog_api.handler;

import com.alibaba.fastjson.JSON;
import com.zlp.blog.blog_api.dao.pojo.SysUser;
import com.zlp.blog.blog_api.dto.ErrorCode;
import com.zlp.blog.blog_api.dto.Result;
import com.zlp.blog.blog_api.service.TokenCheckService;
import com.zlp.blog.blog_api.utils.UserThreadLocal;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class LoginInterceptor implements HandlerInterceptor {
    @Autowired
    RedisTemplate<String,String> redisTemplate;
    @Autowired
    TokenCheckService tokenCheckService;

    /**
     * 登录状态拦截
     * 1. 只拦截访问控制器的请求（控制器方法类型为HandlerMethod）
     * 2. 判断token是否为空
     * 3. 判断token是否合法
     * 4. 判断token是否过期
     * @param request
     * @param response
     * @param handler
     * @return
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if(!(handler instanceof HandlerMethod)){
            return true;
        }
        String token = request.getHeader("Oauth-Token");
        if (StringUtils.isBlank(token)){
            Result res = Result.fail(ErrorCode.NO_LOGIN.getMsg(),ErrorCode.NO_LOGIN.getCode());
            response.setContentType("application/json;charset=utf-8");
            response.getWriter().print(JSON.toJSONString(res));
            return false;
        }
        if (!tokenCheckService.tokenCheck(token)){
            Result res = Result.fail(ErrorCode.TOKEN_ERROR.getMsg(),ErrorCode.TOKEN_ERROR.getCode());
            response.setContentType("application/json;charset=utf-8");
            response.getWriter().print(JSON.toJSONString(res));
            return false;
        }
        String info = redisTemplate.opsForValue().get("TOKEN_" + token);
        if(info==null){
            Result res = Result.fail(ErrorCode.SESSION_TIME_OUT.getMsg(),ErrorCode.SESSION_TIME_OUT.getCode());
            response.setContentType("application/json;charset=utf-8");
            response.getWriter().print(JSON.toJSONString(res));
            return false;
        }
        SysUser sysUser = JSON.parseObject(info,SysUser.class);
        UserThreadLocal.put(sysUser);
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        UserThreadLocal.remove();
    }
}
