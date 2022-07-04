package com.zlp.blog.blog_api.utils;

import com.zlp.blog.blog_api.dao.pojo.SysUser;

public class UserThreadLocal {
    public UserThreadLocal() {
    }

    private static final ThreadLocal<SysUser> LOCAL = new ThreadLocal<>();

    static public void put(SysUser sysUser){
        LOCAL.set(sysUser);
    }

    static public SysUser get(){
        return LOCAL.get();
    }

    static public void remove(){
        LOCAL.remove();
    }
}
