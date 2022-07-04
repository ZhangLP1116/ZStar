package com.zlp.blog.admin.service;

import com.zlp.blog.admin.dao.pojo.Admin;

public interface AdminService {
    public Admin findAdminByUserName(String name);
}
