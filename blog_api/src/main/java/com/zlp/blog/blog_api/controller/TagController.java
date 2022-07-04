package com.zlp.blog.blog_api.controller;


import com.zlp.blog.blog_api.dto.Result;
import com.zlp.blog.blog_api.service.ArticleService;
import com.zlp.blog.blog_api.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/tags")
public class TagController {

    @Autowired
    TagService tagService;

    @GetMapping
    public Result tags(){
        return tagService.tags();
    }

    @GetMapping("/hot")
    public Result hot(){
        int limit = 10;
        return tagService.hots(limit);
    }

    @GetMapping("/detail")
    public Result detail(){
        return tagService.tags();
    }

    @GetMapping("/detail/{id}")
    public Result articleTap(@PathVariable("id") long id){
        return tagService.findTagById(id);
    }
}
