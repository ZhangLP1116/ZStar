package com.zlp.blog.blog_api.dto;

import lombok.Data;

@Data
public class ArchivesDto {
    private Integer year;
    private Integer month;
    private long count;
}
