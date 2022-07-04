package com.zlp.blog.admin.service;



import com.zlp.blog.admin.dao.pojo.Admin;
import com.zlp.blog.admin.dao.pojo.Permission;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.List;


@Service
public class AuthService {
    @Autowired
    AdminService adminService;
    @Autowired
    PermissionService permissionService;


    /**
     * 判断用户是否有权限访问对应的接口
     * 1. 获取登录用户信息，查询ms_admin_permission表，获得对应的权限id
     * 2. 通过权限id查询ms_permission表，获得该权限对应的路径
     * 3. 判断请求路径和权限路径是否匹配，匹配则发行
     * @param request
     * @param authentication
     * @return
     */
    public Boolean auth(HttpServletRequest request, Authentication authentication){
        // 获取请求路径
        String url = request.getRequestURI();
        // 获取用户信息
        Object principal = authentication.getPrincipal();
        // 未登录或者匿名用户，直接拦截
        if (principal == null || "anonymousUser".equals(principal)){
            return false;
        }
        UserDetails userDetails = (UserDetails) principal;
        String name  = userDetails.getUsername();
        Admin admin = adminService.findAdminByUserName(name);
        // 1号超级管理员不进行权限验证，默认可执行所有权限
        if (admin.getId() == 1){
            return true;
        }
        List<Permission> permissionList = permissionService.findListByAdminId(admin.getId());
        url = StringUtils.split(url,'?')[0];
        for (Permission permission : permissionList) {
            if(url.equals(permission.getPath())){
                return true;
            }
        }
        return false;
    }
}
