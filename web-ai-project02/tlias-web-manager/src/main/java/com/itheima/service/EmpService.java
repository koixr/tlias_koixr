package com.itheima.service;

import com.itheima.pojo.Emp;
import com.itheima.pojo.EmpQueryParam;
import com.itheima.pojo.LoginInfo;
import com.itheima.pojo.PageResult;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.List;

public interface EmpService {

    /**
     * 分页查询
     * @return
     */
    //PageResult<Emp> page(Integer page, Integer pageSize, String name, Integer  gender, LocalDate begin, LocalDate end);
    PageResult<Emp> page(EmpQueryParam  empQueryParam);

    /**
     * 保存员工信息
     * @param emp
     */
    void save(Emp emp);

    /**
     * 批量删除员工
     * @param ids
     */
    void delete(List<Integer> ids);

    /**
     * 根据id查询员工信息
     */
    Emp getById(Integer id);

    /**
     * 更新员工信息
     */
    void update(Emp emp);

    /**
     * clazz新增班级中展示班主任姓名
     */
    List<Emp> listAll();

    /**
     * 员工登录
     */
    LoginInfo login(Emp emp);
}
