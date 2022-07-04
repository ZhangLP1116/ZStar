package com.zlp.blog.blog_api.service;

import com.zlp.blog.blog_api.dao.pojo.Category;
import com.zlp.blog.blog_api.dto.Result;

public interface CategoryService {

    Category findCategoryById(long id);

    Result categorys();
}
