package com.zlp.blog.admin.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Result {
    private Boolean success;
    private int code;
    private String msg;
    private Object data;

    public static Result success(Object data){
        return new Result(true,200,"成功", data);
    }

    public static Result fail(String msg, int code){
        return new Result(false,code,msg,null);
    }
}
