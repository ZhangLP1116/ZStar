package com.zlp.blog.blog_api.dto;

import lombok.Data;

@Data
public class PageParams {
    private int page=1;
    private int pageSize=10;
    private Long categoryId;
    private Long tagId;
    private String month;
    private String year;

    public String getMonth() {
        if (month!=null && month.length()==1){
            return "0" + month;
        }
        return this.month;
    }
}
