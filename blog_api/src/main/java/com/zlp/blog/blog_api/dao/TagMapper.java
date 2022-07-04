package com.zlp.blog.blog_api.dao;

import com.zlp.blog.blog_api.dao.pojo.Tag;
import com.zlp.blog.blog_api.dto.Result;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface TagMapper {
    /**
     * 根据文章ID查询标签表
     * @param articleId
     * @return
     */
    List<Tag> findTagsByArticleID(long articleId);

    List<Tag> findHotTags(int limit);

    List<Tag> findTagALL();

    Tag findTagById(long id);
}
