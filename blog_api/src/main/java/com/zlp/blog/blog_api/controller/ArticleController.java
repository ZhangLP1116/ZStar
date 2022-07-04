package com.zlp.blog.blog_api.controller;

import com.zlp.blog.blog_api.aop.Cache;
import com.zlp.blog.blog_api.aop.LogAnnotation;
import com.zlp.blog.blog_api.dto.ArticleParams;
import com.zlp.blog.blog_api.dto.PageParams;
import com.zlp.blog.blog_api.dto.Result;
import com.zlp.blog.blog_api.service.ArticleService;
import com.zlp.blog.blog_api.utils.IpUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;


@RestController
@RequestMapping("/api/articles")
public class ArticleController {
    @Autowired
    private ArticleService articleService;

    /**
     * 文章列表获取
     * @param pageParams
     * @return
     */
    @PostMapping
    @LogAnnotation(module = "文章", operation = "获取文章列表")
    //@Cache(expire = 5 * 60 * 1000,name = "articlesList")
    public Result articlesList(@RequestBody PageParams pageParams){
        return articleService.listArticle(pageParams);
    }

    /**
     * 最热文章获取
     * @return
     */
    @PostMapping("/hot")
    //@Cache(expire = 5 * 60 * 1000,name = "hot_article")
    public Result hotArticles(){
        int limit = 5;
        return articleService.hotArticles(limit);
    }

    /**
     * 最新文章获取
     * @return
     */
    @PostMapping("/new")
    public Result newArticles(){
        int limit = 5;
        return articleService.newArticles(limit);
    }

    /**
     * 文章归档信息获取
     * @return
     */
    @PostMapping("/listArchives")
    public Result listArchives(){
        return articleService.listArchives();
    }

    @PostMapping("/view/{id}")
    public Result view(@PathVariable("id") long id){
        return articleService.findArticle(id);
    }

    /**
     * 保存文章
     * @param articleParams
     * @return
     */
    @PostMapping("publish")
    public Result publish(@RequestBody ArticleParams articleParams){
        return articleService.publish(articleParams);
    }
}
