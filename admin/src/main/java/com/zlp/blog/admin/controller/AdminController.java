package com.zlp.blog.admin.controller;

import com.zlp.blog.admin.dao.pojo.Permission;
import com.zlp.blog.admin.dto.Result;
import com.zlp.blog.admin.dto.param.PageParam;
import com.zlp.blog.admin.service.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    PermissionService permissionService;

    @PostMapping("/permission/permissionList")
    public Result permissionList(@RequestBody PageParam pageParam){
        return permissionService.permissionList(pageParam);
    }

    @PostMapping("/permission/add")
    public Result add(@RequestBody Permission permission){
        return permissionService.add(permission);
    }

    @PostMapping("/permission/update")
    public Result update(@RequestBody Permission permission){
        return permissionService.update(permission);
    }
    @GetMapping("/permission/delete/{id}")
    public Result delete(@PathVariable("id") long id){
        return permissionService.delete(id);
    }
}
