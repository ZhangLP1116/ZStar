package com.zlp.blog.admin.service.impl;

import com.zlp.blog.admin.dao.PermissionMapper;
import com.zlp.blog.admin.dao.pojo.Permission;
import com.zlp.blog.admin.dto.PageResult;
import com.zlp.blog.admin.dto.Result;
import com.zlp.blog.admin.dto.param.PageParam;
import com.zlp.blog.admin.service.PermissionService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PermissionServiceImpl implements PermissionService {
    @Autowired
    PermissionMapper permissionMapper;

    /**
     * 查询权限列表
     * @param pageParam
     * @return
     */
    @Override
    public Result permissionList(PageParam pageParam) {
        long start  = (pageParam.getCurrentPage()-1)*pageParam.getPageSize();
        List<Permission> permissionList;
        if (StringUtils.isBlank(pageParam.getQueryString())){
            permissionList= permissionMapper.findPermissionList(start,pageParam.getPageSize());
        }
        else {
            permissionList= permissionMapper.findPermissionListByName(start,pageParam.getPageSize(),pageParam.getQueryString());
        }
        long total = permissionMapper.count();
        PageResult<Permission> pageResult = new PageResult<>();
        pageResult.setList(permissionList);
        pageResult.setTotal(total);
        return Result.success(pageResult);
    }

    @Override
    public Result add(Permission permission) {
        permissionMapper.add(permission);
        return Result.success(null);
    }

    @Override
    public Result update(Permission permission) {
        permissionMapper.update(permission);
        return Result.success(null);
    }

    @Override
    public Result delete(long id) {
        permissionMapper.delete(id);
        return Result.success(null);
    }

    @Override
    public List<Permission> findListByAdminId(long id) {
        List<Permission> permissionList = permissionMapper.findListByAdminId(id);
        return permissionList;
    }
}
