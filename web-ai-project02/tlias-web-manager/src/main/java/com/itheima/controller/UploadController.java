package com.itheima.controller;

import com.itheima.pojo.Result;
import com.itheima.utils.AliyunOSSOperator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Slf4j
@RestController
public class UploadController {

    /**
     * 本地磁盘文件上传
     * @param file
     * @return
     * @throws IOException
     */
//    @PostMapping("/upload")
//    public Result upload(String name, Integer age, MultipartFile file) throws IOException {
//        log.info("文件上传开始：{}，{}，{}",name,age,file);
//        //获取原始文件名
//        String originalFilename = file.getOriginalFilename();
//        //获取新文件名
//        String extension = originalFilename.substring(originalFilename.lastIndexOf("."));
//        String newFilename = UUID.randomUUID().toString() + extension;
//        //储存文件 文件名不能写死 文件名可能重复 导致后来覆盖前一张图片 默认最大图片大小1mb
//        file.transferTo(new File("D:/cccodewwwork/shujuku/images/" + newFilename));
//        return Result.success();
//        //文件磁盘饱满等等 使用阿里oss云服务-对象存储服务
//        //准备账号工作
//        //创建对象存储容器bucket 创建密钥 配置访问凭证
//        //根据官方sdk编写程序 sdk-软件开发工具包 引入阿里OSS依赖
//        //集成使用
//    }

    /**
     * 基于阿里云oss云服务-对象存储服务
     */
    @Autowired
    private AliyunOSSOperator aliyunOSSOperator;

    @PostMapping("/upload")
    public Result upload(MultipartFile file) throws Exception {
        log.info("文件上传开始：{}",file.getOriginalFilename());
        //使用工具类将文件上传到阿里云oss云服务
        String url = aliyunOSSOperator.upload(file.getBytes(), file.getOriginalFilename());
        return Result.success(url);
    }
}
