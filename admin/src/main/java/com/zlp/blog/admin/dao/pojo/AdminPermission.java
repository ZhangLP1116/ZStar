package com.zlp.blog.admin.dao.pojo;

import lombok.Data;

@Data
public class AdminPermission {
    private long id;
    private long admin_id;
    private long permission_id;
}
