package com.zlp.blog.admin.service;

import com.zlp.blog.admin.dao.pojo.Permission;
import com.zlp.blog.admin.dto.Result;
import com.zlp.blog.admin.dto.param.PageParam;

import java.util.List;

public interface PermissionService {
    Result permissionList(PageParam pageParam);

    Result add(Permission permission);

    Result update(Permission permission);

    Result delete(long id);

    List<Permission> findListByAdminId(long id);
}
