package com.zlp.blog.blog_api.dao;

import com.zlp.blog.blog_api.dao.pojo.SysUser;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface SysUserMapper {
    SysUser findUserByAccount(String account);

    int saveUser(SysUser sysUser);

    SysUser findSysUserByID(long id);

    SysUser findUser(@Param("account") String account, @Param("password") String password);
}
