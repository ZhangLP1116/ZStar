package com.zlp.blog.blog_api.dao.pojo;

import lombok.Data;

import javax.swing.*;

@Data
public class SysUser {
    private long id;
    private String account;
    private Integer admin;
    private String avatar;
    private long create_date;
    private Integer deleted;
    private String email;
    private long last_login;
    private String mobile_phone_number;
    private String nickname;
    private String password;
    private String salt;
    private String status;
}
