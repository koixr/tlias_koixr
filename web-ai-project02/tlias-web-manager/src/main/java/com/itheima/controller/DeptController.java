package com.itheima.controller;

import com.itheima.pojo.Dept;
import com.itheima.pojo.Result;
import com.itheima.service.DeptService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.integration.IntegrationAutoConfiguration;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RequestMapping("/depts")
@RestController
public class DeptController {

    //private static final Logger log = LoggerFactory.getLogger(DeptController.class);  有lombok就不用写了，在最前面加入@Slf4j即可

    @Autowired
    private DeptService deptService;

    //@RequestMapping(value = "/depts", method = RequestMethod.GET)
    @GetMapping
    public Result list(){
        //System.out.println("select all dept");
        log.info("查询全部部门dept");
        List<Dept> deptList = deptService.findAll();
        return Result.success(deptList);
    }

    /**
     * 删除方式一：httprequest
     */
//    @DeleteMapping("/depts")
//    public Result delete(HttpServletRequest request){
//        String idStr = request.getParameter("id");
//        int id =Integer.parseInt(idStr);
//        System.out.println("delete dept"+id);
//        return Result.success();
//    }

    /**
     * 删除方式二：httprequest
     * 注意事项：参数必须传递，否则报错
     * required = false 就不会报错
     */
//    @DeleteMapping("/depts")
//    public Result delete(@RequestParam("id") Integer deptId){
//        System.out.println("删除部门为："+deptId);
//        return Result.success();
//    }

    /**
     * 删除方式三：省略@RequestParam,前端参数和服务端参数一致时候可以省略
     */
    @DeleteMapping
    public Result delete(Integer id){
        //System.out.println("删除部门为："+id);
        log.info("删除部门为：{}", id);
        deptService.deleteById(id);
        return Result.success();
    }

    /**
     * 添加部门
     * @return
     */
    @PostMapping
    public Result add(@RequestBody Dept dept){
        //System.out.println("add部门为："+ dept);
        log.info("添加部门为：{}", dept);
        deptService.insert(dept);
        return Result.success();
    }

    /**
     * 根据ID查询部门
     * @return
     */
    @GetMapping("/{id}")
    public Result getInfo(@PathVariable Integer id){
        //System.out.println("根据ID查询部门为："+ id);
        log.info("根据ID查询部门为：{}", id);
        Dept dept = deptService.getInfo(id);
        return Result.success(dept);
    }

    /**
     * 修改部门
     * @return
     */
    @PutMapping
    public Result update(@RequestBody Dept dept){
        //System.out.println("修改部门为："+ dept);
        log.info("修改部门为：{}", dept);
        deptService.update(dept);
        return Result.success();
    }

}
