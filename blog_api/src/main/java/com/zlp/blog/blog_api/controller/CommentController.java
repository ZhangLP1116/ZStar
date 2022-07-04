package com.zlp.blog.blog_api.controller;

import com.zlp.blog.blog_api.dto.CommentParams;
import com.zlp.blog.blog_api.dto.Result;
import com.zlp.blog.blog_api.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/comments")
public class CommentController {
    @Autowired
    CommentService commentService;

    @GetMapping("/article/{id}")
    public Result comments(@PathVariable("id") long id){
        return commentService.findArticleComments(id);
    }

    @PostMapping("/create/change")
    public Result createComment(@RequestBody CommentParams commentParams){
        return commentService.createComment(commentParams);
    }
}

