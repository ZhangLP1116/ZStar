package com.zlp.blog.admin.dao;

import com.zlp.blog.admin.dao.pojo.Permission;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface PermissionMapper {
    List<Permission> findPermissionList(@Param("start") long start,@Param("pageSize") Integer pageSize);

    long count();

    List<Permission> findPermissionListByName(@Param("start") long start,@Param("pageSize") Integer pageSize,@Param("queryString") String queryString);

    void add(Permission permission);

    void update(Permission permission);

    void delete(long id);

    List<Permission> findListByAdminId(long id);
}
