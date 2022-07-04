package com.zlp.blog.admin.dao;

import com.zlp.blog.admin.dao.pojo.Admin;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface AdminMapper {
    Admin findAdminByUserName(String name);
}
