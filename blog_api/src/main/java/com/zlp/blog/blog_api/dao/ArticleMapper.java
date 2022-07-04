package com.zlp.blog.blog_api.dao;


import com.zlp.blog.blog_api.dao.pojo.Article;
import com.zlp.blog.blog_api.dao.pojo.ArticleBody;
import com.zlp.blog.blog_api.dto.ArchivesDto;
import com.zlp.blog.blog_api.dto.PageParams;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.List;

@Mapper
@Repository
public interface ArticleMapper {
    List<Article> ListArticle(PageParams pageParams);

    List<Article> getArticlesInfo(int limit);

    List<Article> getNewArticlesInfo(int limit);

    List<ArchivesDto> listArchives();

    Article findArticleById(long id);

    void updateView_counts(@Param("id") long id,@Param("view_count") int view_count);


    void insert(Article article);

    void update(Article article);

    List<Article> findArticleListByCategoryID(long id);

    List<Article> findArticleListByID(List<Long> articleIdList);

    List<Article> findArticleListByDate(@Param("year") String year,@Param("month") String month);
}
