package com.zlp.blog.admin.dao.pojo;

import lombok.Data;

@Data
public class Permission {
    private long id;
    private String name;
    private String path;
    private String description;
}
