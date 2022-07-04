package com.zlp.blog.admin.service;

import com.zlp.blog.admin.dao.pojo.Admin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public class SecurityUserService implements UserDetailsService {
    @Autowired
    AdminService adminService;

    /**
     * 获取Security传递来的用户名，继续数据库查找，若用户存在则将密码传递给security
     * @param s
     * @return
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        Admin admin = adminService.findAdminByUserName(s);
        if (admin == null){
            // 登录失败
            return null;
        }
        UserDetails userDetails = new User(s,admin.getPassword(),new ArrayList<>());
        return userDetails;
    }
}
