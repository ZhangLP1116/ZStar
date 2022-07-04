package com.zlp.blog.blog_api.service.Impl;

import com.zlp.blog.blog_api.dao.TagMapper;
import com.zlp.blog.blog_api.dao.pojo.Tag;
import com.zlp.blog.blog_api.dto.Result;
import com.zlp.blog.blog_api.dto.TagDto;
import com.zlp.blog.blog_api.service.TagService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
public class TagServiceImpl implements TagService {
    @Autowired
    TagMapper tagMapper;

    @Override
    public Result hots(int limit) {
        List<Tag> hotTags = tagMapper.findHotTags(limit);
        return Result.success(hotTags);
    }

    @Override
    public Result tags() {
        List<Tag> tagList = tagMapper.findTagALL();
        List<TagDto> tagDtoList = copyList(tagList);
        return Result.success(tagDtoList);
    }

    @Override
    public Result findTagById(long id) {
        Tag tag = tagMapper.findTagById(id);
        return  Result.success(toTagDao(tag));
    }


    @Override
    public List<TagDto> findTagsByArticleID(long articleId) {
        List<Tag> tagList = tagMapper.findTagsByArticleID(articleId);
        List<TagDto> tagDtoList = copyList(tagList);
        return tagDtoList;
    }


    private List<TagDto> copyList(List<Tag> tags) {
       List<TagDto> tagDtoList = new ArrayList<>();
        for (Tag tag : tags) {
            tagDtoList.add(toTagDao(tag));
        }
        return tagDtoList;
    }

    private TagDto toTagDao(Tag tag) {
        TagDto tagDto = new TagDto();
        BeanUtils.copyProperties(tag,tagDto);
        return tagDto;
    }
}
