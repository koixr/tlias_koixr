package com.itheima.controller;

import com.itheima.mapper.EmpMapper;
import com.itheima.pojo.Emp;
import com.itheima.pojo.EmpQueryParam;
import com.itheima.pojo.PageResult;
import com.itheima.pojo.Result;
import com.itheima.service.EmpService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.bind.DefaultValue;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

/**
 * 员工管理
 */
@Slf4j
@RequestMapping("/emps")
@RestController
public class EmpController {

    @Autowired
    private EmpService empService;

//    /**
//     * 分页查询
//     */
//    @GetMapping
//    public Result page(@RequestParam(defaultValue = "1") Integer page,
//                       @RequestParam(defaultValue = "10") Integer pageSize,
//                       String name, Integer  gender,
//                       @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate begin,
//                       @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate end) {
//        log.info("分页查询，当前页码：{}，每页记录数：{}, name:{}, gender:{}, begin:{}, end: {}", page, pageSize, name, gender, begin, end);
//        // 模拟查询到的总记录数
//        PageResult<Emp> pageResult = empService.page(page, pageSize, name, gender, begin, end);
//        return Result.success(pageResult);
    /**
     * 分页查询
     */
    @GetMapping
    public Result page(EmpQueryParam empQueryParam) {
        log.info("分页查询:{}",empQueryParam );
        // 模拟查询到的总记录数
        PageResult<Emp> pageResult = empService.page(empQueryParam);
        return Result.success(pageResult);
    }

    /**
     * 新增员工
     * 加postMapping注解
     */
    @PostMapping
    public Result save(@RequestBody  Emp emp) {
        log.info("新增员工，员工信息：{}", emp);
        empService.save(emp);
        return Result.success();
    }

    /**
     * 删除员工--数组接受
     */
//    @DeleteMapping
//    public Result delete(Integer[] ids){
//        //借用Arrays.toString()方法输出数组中的值，否则看不见元素
//        log.info("删除员工：{}",Arrays.toString(ids));
//        empService.delete(ids);
//        return Result.success();
//    }

    /**
     * 删除员工--list集合
     */
    //使用list集合封装ids更方便，可以调用其中的方法对其中元素做操作
    //记得加头注解 list集合需要添加requestParam注解才能获取参数 标识着接受前端传递过来的参数
    //莫名其妙 啥也没改 重新运行几遍就能用了
    //这里请求样例为/emps?ids=1,2,3与后面的不同
    @DeleteMapping
    public Result delete(@RequestParam List<Integer> ids){
        log.info("删除员工：{}",ids);
        empService.delete(ids);
        return Result.success();
    }

    /**
     * 修改员工-查询回显
     */
    @GetMapping("/{id}")
    //路径数据绑定id需要@PathVariable注解
    public Result getInfo(@PathVariable Integer id){
        log.info("查询员工信息：{}",id);
        //需要返回信息 使用emp接受返回的信息并发送给前端
        Emp emp = empService.getById(id);
        return Result.success(emp);
    }

    /**
     * 修改员工 先删除在添加，可以使用前两个接口
     */
    @PutMapping
    //json格式数据封装到对象当中需要@RequestBody注解
    public Result update(@RequestBody Emp emp) {
        log.info("修改员工信息：{}", emp);
        empService.update(emp);
        return Result.success();
    }

    /**
     * clazz新增班级中展示班主任姓名
     */
    @GetMapping("/list")
    public Result listAll(){
        log.info("查询所有员工信息");
        List<Emp> list = empService.listAll();
        return Result.success(list);
    }
}

