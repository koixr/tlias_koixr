package com.itheima.pojo;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

//查询参数实体类记得添加@Data注解，才能调用PageHelper中的getPage（）和getPagesize（）方法
@Data
public class ClazzQueryParam {
    private String name;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date begin;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date end;
    //默认赋值
    private Integer page =1;
    private Integer pageSize=5;
}
