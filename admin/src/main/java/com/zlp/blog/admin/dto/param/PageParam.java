package com.zlp.blog.admin.dto.param;

import lombok.Data;

@Data
public class PageParam {
    private Integer currentPage;
    private Integer pageSize;
    private String queryString;

}
