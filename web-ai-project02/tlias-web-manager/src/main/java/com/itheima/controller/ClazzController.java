package com.itheima.controller;

import com.itheima.pojo.*;
import com.itheima.service.ClazzService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RequestMapping("/clazzs")
@RestController
public class ClazzController {

    @Autowired
    private ClazzService clazzService;

    /**
     * 分页展示所有班级信息
     * @return
     */
    @GetMapping
    public Result page(ClazzQueryParam clazzQueryParam) {
        log.info("查询所有班级信息:{}",clazzQueryParam);
        PageResult<Clazz> pageResult = clazzService.page(clazzQueryParam);
        return Result.success(pageResult);
    }

    /**
     * 删除班级信息
     */
    @DeleteMapping("/{id}")
    //此处路径为http://localhost:90/api/clazzs/8，是路径风格应该使用@PathVariable注解
    //之前路径为http://localhost:90/api/clazzs?id=8，是查询风格应该使用@RequestParam注解
    public Result delete(@PathVariable Integer id){
        log.info("删除班级信息:{}",id);
        clazzService.deleteById(id);
        return Result.success();
    }

    /**
     * 添加班级信息
     */
    @PostMapping
    public Result save(@RequestBody Clazz clazz){
        log.info("添加班级信息:{}",clazz);
        clazzService.save(clazz);
        return Result.success();
    }

    /**
     * 修改班级信息前的，数据回显
     */
    @GetMapping("/{id}")
    public Result getById(@PathVariable Integer id){
        log.info("查询班级信息:{}",id);
        Clazz clazz = clazzService.getById(id);
        return Result.success(clazz);
    }

    /**
     * 修改班级信息
     */
    @PutMapping
    public Result update(@RequestBody Clazz clazz){
        log.info("修改班级信息:{}",clazz);
        clazzService.update(clazz);
        return Result.success();
    }

    /**
     * 查询所有班级信息
     */
    @GetMapping("/list")
    public Result listAll(){
        log.info("查询所有班级信息");
        List<Clazz> list = clazzService.listAll();
        return Result.success(list);
    }

}
