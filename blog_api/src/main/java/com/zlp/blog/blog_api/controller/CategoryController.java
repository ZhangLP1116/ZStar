package com.zlp.blog.blog_api.controller;


import com.zlp.blog.blog_api.dto.Result;
import com.zlp.blog.blog_api.service.ArticleService;
import com.zlp.blog.blog_api.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/categorys")
public class CategoryController {
    @Autowired
    CategoryService categoryService;

    /**
     * 获取所有分类
     * @return
     */
    @GetMapping
    public Result categorys(){
        return categoryService.categorys();
    }

    /**
     * 获取所有分类的详细信息
     * @return
     */
    @GetMapping("/detail")
    public Result detail(){
        return categoryService.categorys();
    }

    /**
     * 获取某一类下的所有文章
     * @param id
     * @return
     */
    @GetMapping("/detail/{id}")
    public Result articleCategory(@PathVariable("id") long id){
        return Result.success(categoryService.findCategoryById(id));
    }
}
