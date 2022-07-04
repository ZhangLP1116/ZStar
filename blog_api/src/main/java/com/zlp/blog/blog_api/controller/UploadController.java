package com.zlp.blog.blog_api.controller;

import com.zlp.blog.blog_api.dto.Result;
import com.zlp.blog.blog_api.utils.QiniuUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@RestController
@RequestMapping("/api/upload")
public class UploadController {
    @Autowired
    QiniuUtils qiniuUtils;

    @PostMapping
    public Result upload(@RequestPart("image") MultipartFile multipartFile) throws IOException {
        String originalFilename = multipartFile.getOriginalFilename();
        // 生成文件名
        String filename = UUID.randomUUID() + "." + StringUtils.substringAfter(originalFilename, ".");
        // 使用字节数组上传到七牛云
        Boolean res = qiniuUtils.upload(filename,multipartFile.getBytes());
        return Result.fail("上传失败",20000);
    }
}
