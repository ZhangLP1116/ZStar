package com.zlp.blog.admin.service.impl;

import com.zlp.blog.admin.dao.AdminMapper;
import com.zlp.blog.admin.dao.pojo.Admin;
import com.zlp.blog.admin.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdminServiceImpl implements AdminService {
    @Autowired
    AdminMapper adminMapper;

    public Admin findAdminByUserName(String name){
        Admin admin = adminMapper.findAdminByUserName(name);
        return admin;
    }
}
