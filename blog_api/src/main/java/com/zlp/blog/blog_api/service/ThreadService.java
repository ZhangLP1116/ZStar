package com.zlp.blog.blog_api.service;

import com.zlp.blog.blog_api.dao.ArticleMapper;
import com.zlp.blog.blog_api.dao.pojo.Article;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
public class ThreadService {

    /**
     * 使用线程池执行，分布式场景下要是其他终端修该了view_counts则数据库中的View_counts和当前对象中的不等，
     * 所以不能直接使用当前View_counts+1进行更新
     * @param articleMapper
     * @param article
     */
    @Async("taskExecutor")
    public void updateArticleViewCount(ArticleMapper articleMapper, Article article){
        int view_count = article.getView_counts();
        long id = article.getId();
        articleMapper.updateView_counts(id,view_count);
    }
}
