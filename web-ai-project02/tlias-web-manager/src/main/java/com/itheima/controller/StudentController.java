package com.itheima.controller;

import com.itheima.pojo.PageResult;
import com.itheima.pojo.Result;
import com.itheima.pojo.Student;
import com.itheima.pojo.StudentQueryParam;
import com.itheima.service.StudentService;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Delete;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.InputStream;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RequestMapping("/students")
@RestController
public class StudentController {

    @Autowired
    private StudentService studentService;

    /**
     * 学生列表查询
     */
    @GetMapping
    public Result page(StudentQueryParam studentQueryParam){
        log.info("学生列表查询，参数：{}", studentQueryParam);
        PageResult<Student> pageResult = studentService.page(studentQueryParam);
        return Result.success(pageResult);
    }

    /**
     * 删除学生信息
     * 请求样例为：/students/1,2,3
     * 重启一下就好了，又是删除莫名其妙的
     */
    @DeleteMapping("/{ids}")
    public Result delete(@PathVariable List<Integer> ids){
        log.info("删除学生信息，参数：{}", ids);
        studentService.delete(ids);
        return Result.success();
    }

    /**
     * 新增学生信息
     */
    @PostMapping
    public Result save(@RequestBody Student student){
        log.info("新增学生信息：{}", student);
        studentService.save(student);
        return Result.success();
    }

    /**
     * 根据id查询学生信息,数据回显
     */
    @GetMapping("/{id}")
    public Result getById(@PathVariable Integer id){
        log.info("查询学生信息：{}", id);
        Student student = studentService.getById(id);
        return Result.success(student);
    }

    /**
     * 修改学生信息
     */
    @PutMapping
    public Result update(@RequestBody Student student){
        log.info("修改学生信息：{}", student);
        studentService.update(student);
        return Result.success();
    }

    /**
     * 违纪信息
     * 注意路径上的violation，并不包含在Student的大路径中
     */
    @PutMapping("/violation/{id}/{score}")
    public Result updateViolation(@PathVariable Integer id, @PathVariable Short score){
        log.info("修改学生信息：{},{}", id, score);
        studentService.updateViolation(id,score);
        return Result.success();
    }
}
