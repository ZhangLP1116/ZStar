package com.zlp.blog.blog_api.dao;

import com.zlp.blog.blog_api.dao.pojo.Category;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface CategoryMapper {
    Category findCategoryById(long id);

    List<Category> findCategoryAll();
}
