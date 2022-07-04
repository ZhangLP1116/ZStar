package com.zlp.blog.blog_api.service.Impl;

import com.zlp.blog.blog_api.dao.CategoryMapper;
import com.zlp.blog.blog_api.dao.pojo.Category;
import com.zlp.blog.blog_api.dto.CategoryDto;
import com.zlp.blog.blog_api.dto.Result;
import com.zlp.blog.blog_api.service.CategoryService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    CategoryMapper categoryMapper;

    /**
     * 查询某个文章的分类，工具文章ID
     * @param id
     * @return
     */
    @Override
    public Category findCategoryById(long id) {
        Category category = categoryMapper.findCategoryById(id);
        return category;
    }

    /**
     * 查询所有标签
     * @return
     */
    @Override
    public Result categorys() {
        List<Category> categoryList = categoryMapper.findCategoryAll();
        List<CategoryDto> categoryDtoList = copyList(categoryList);
        return Result.success(categoryDtoList);
    }

    private List<CategoryDto> copyList(List<Category> categoryList) {
        List<CategoryDto> categoryDtoList= new ArrayList<>();
        for (Category category : categoryList) {
            categoryDtoList.add(copy(category));
        }
        return categoryDtoList;
    }

    private CategoryDto copy(Category category) {
        CategoryDto categoryDto = new CategoryDto();
        BeanUtils.copyProperties(category,categoryDto);
        return categoryDto;
    }
}
