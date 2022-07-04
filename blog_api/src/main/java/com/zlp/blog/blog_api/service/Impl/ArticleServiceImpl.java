package com.zlp.blog.blog_api.service.Impl;

import com.zlp.blog.blog_api.dao.ArticleBodyMapper;
import com.zlp.blog.blog_api.dao.ArticleMapper;
import com.zlp.blog.blog_api.dao.ArticleTagMapper;
import com.zlp.blog.blog_api.dao.pojo.*;
import com.zlp.blog.blog_api.dto.*;
import com.zlp.blog.blog_api.service.*;
import com.zlp.blog.blog_api.utils.UserThreadLocal;
import org.joda.time.DateTime;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ArticleServiceImpl implements ArticleService {
    @Autowired
    ArticleMapper articleMapper;
    @Autowired
    ArticleBodyMapper articleBodyMapper;
    @Autowired
    ThreadService threadService;
    @Autowired
    TagService tagService;
    @Autowired
    SysUserService sysUserService;
    @Autowired
    CategoryService categoryService;
    @Autowired
    ArticleTagMapper articleTagMapper;

    /**
     *
     * @param limit
     * @return 获取观看最多的文章
     */
    @Override
    public Result hotArticles(int limit) {
        List<Article> articles = articleMapper.getArticlesInfo(limit);
        List<ArticleDto> articleDtos = copyList(articles,false,false);
        return Result.success(articleDtos);
    }

    @Override
    public Result newArticles(int limit) {
        List<Article> articles = articleMapper.getNewArticlesInfo(limit);
        List<ArticleDto> articleDtos = copyList(articles,false,false);
        return Result.success(articleDtos);
    }

    @Override
    public Result listArchives() {
        List<ArchivesDto> archivesDtos= articleMapper.listArchives();
        return Result.success(archivesDtos);
    }

    /**
     * 保存文章
     * 1. 保存文章标签信息 （ms_article_tag）
     * 2. 保存文章信息 （ms_article）
     * 3. 保存文章体信息 （ms_article_body）
     * 4.
     * @param articleParams
     * @return
     */
    @Transactional
    @Override
    public Result publish(ArticleParams articleParams) {
        SysUser sysUser = UserThreadLocal.get();
        // 保存文章信息 （ms_article）
        Article article = new Article();
        article.setAuthor_id(sysUser.getId());
        article.setView_counts(0);
        article.setCategory_id(articleParams.getCategory().getId());
        article.setCreate_date(System.currentTimeMillis());
        article.setSummary(articleParams.getSummary());
        article.setTitle(articleParams.getTitle());
        articleMapper.insert(article);

        // 保存文章体信息 （ms_article_body）
        ArticleBody articleBody = new ArticleBody();
        articleBody.setContent(articleParams.getBody().getContent());
        articleBody.setContent_html(articleParams.getBody().getContentHtml());
        articleBody.setArticle_id(article.getId());
        articleBodyMapper.insert(articleBody);

        // 更新文章body_id信息 （ms_article）
        article.setBody_id(articleBody.getId());
        articleMapper.update(article);

        // 保存文章标签信息 （ms_article_tag）
        List<TagDto> tagDtoList = articleParams.getTags();
        if (tagDtoList!=null){
            for (TagDto tagDto : tagDtoList) {
                ArticleTag articleTag = new ArticleTag();
                articleTag.setArticle_id(article.getId());
                articleTag.setTag_id(tagDto.getId());
                articleTagMapper.insert(articleTag);
            }
        }
        Map<String,String> map = new HashMap<>();
        map.put("id",Long.toString(article.getId()));
        return Result.success(map);
    }


    /**
     * 获取文章内容
     * 使用线程池更新view_counts，不影响正常响应速度
     * @param id
     * @return
     */
    @Override
    public Result findArticle(long id) {
        Article article = articleMapper.findArticleById(id);
        if (article == null){
            return Result.fail(ErrorCode.ARTICLE_NOT_EXIST.getMsg(),ErrorCode.ARTICLE_NOT_EXIST.getCode());
        }
        ArticleDto articleDto = toArticle(article,true,true,true,true);
        threadService.updateArticleViewCount(articleMapper,article);
        return Result.success(articleDto);
    }


    /**
     *
     * @param pageParams 根据分页获取文章表数据
     * @return
     */
    @Override
    public Result listArticle(PageParams pageParams) {
        pageParams.setPage(pageParams.getPage()-1);
        if(pageParams.getCategoryId()!=null){
            List<Article> articles  = articleMapper.findArticleListByCategoryID(pageParams.getCategoryId());
            List<ArticleDto> articleDtoList = copyList(articles,true,true);
            return Result.success(articleDtoList);
        }
        if(pageParams.getTagId()!=null){
            List<Long> articleIdList = articleTagMapper.findArticleIdListByTagId(pageParams.getTagId());
            List<Article> articleList = articleMapper.findArticleListByID(articleIdList);
            List<ArticleDto> articleDtoList = copyList(articleList,true,true);
            return Result.success(articleDtoList);
        }
        if(pageParams.getYear()!=null && pageParams.getYear().length()>0
        && pageParams.getMonth()!=null && pageParams.getMonth().length()>0){
            List<Article> articleList = articleMapper.findArticleListByDate(pageParams.getYear(),pageParams.getMonth());
            List<ArticleDto> articleDtoList = copyList(articleList,true,true);
            return Result.success(articleDtoList);
        }
        List<Article> articles = articleMapper.ListArticle(pageParams);
        List<ArticleDto> articleDtoList = copyList(articles,true,true);
        return Result.success(articleDtoList);
    }


    /**
     *
     * @param articles
     * @return List<Article> ----> List<ArticleDto>
     */
    private List<ArticleDto> copyList(List<Article> articles,boolean isTag, boolean isAuthor) {
        List<ArticleDto> articleDtoList = new ArrayList<>();
        for (Article article : articles) {
            articleDtoList.add(toArticle(article,isTag,isAuthor));
        }
        return articleDtoList;
    }


    /**
     *
     * @param article
     * @param isTag 不是所有接口都需要tag、anthor
     * @param isAuthor
     * @return Article ---------> ArticleDto
     */
    private ArticleDto toArticle(Article article, boolean isTag, boolean isAuthor) {
        ArticleDto articleDto = new ArticleDto();
        BeanUtils.copyProperties(article,articleDto);
        articleDto.setCreate_date(new DateTime(articleDto.getCreate_date()).toString("yyyy-MM-dd HH:mm"));
        if(isTag){
            long articleId = article.getId();
            articleDto.setTags(tagService.findTagsByArticleID(articleId));
        }
        if(isAuthor){
            long id = article.getAuthor_id();
            SysUser sysUser = sysUserService.findSysUserByID(id);
            articleDto.setAuthor(sysUser.getNickname());
        }
        return articleDto;
    }

    private ArticleDto toArticle(Article article, boolean isTag, boolean isAuthor,boolean isArticleBodyDto,boolean isCategoryDto){
        ArticleDto articleDto = new ArticleDto();
        BeanUtils.copyProperties(article,articleDto);
        articleDto.setCreate_date(new DateTime(articleDto.getCreate_date()).toString("yyyy-MM-dd HH:mm"));
        if(isTag){
            long articleId = article.getId();
            articleDto.setTags(tagService.findTagsByArticleID(articleId));
        }
        if(isAuthor){
            long id = article.getAuthor_id();
            SysUser sysUser = sysUserService.findSysUserByID(id);
            articleDto.setAuthor(sysUser.getNickname());
        }
        if (isArticleBodyDto){
            long body_id = article.getBody_id();
            ArticleBody articleBody = articleBodyMapper.findArticleBodyById(body_id);
            ArticleBodyDto articleBodyDto = new ArticleBodyDto();
            articleBodyDto.setContent(articleBody.getContent());
            articleDto.setBody(articleBodyDto);
        }
        if (isCategoryDto){
            long category_id = article.getCategory_id();
            Category category = categoryService.findCategoryById(category_id);
            CategoryDto categoryDto = new CategoryDto();
            BeanUtils.copyProperties(category,categoryDto);
            articleDto.setCategoryDto(categoryDto);
        }
        return articleDto;
    }
}

