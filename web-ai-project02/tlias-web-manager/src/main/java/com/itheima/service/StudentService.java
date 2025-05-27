package com.itheima.service;

import com.itheima.pojo.PageResult;
import com.itheima.pojo.Student;
import com.itheima.pojo.StudentQueryParam;

import java.util.List;

public interface StudentService {
    /**
     * 分页查询
     * @param studentQueryParam
     * @return
     */
    PageResult<Student> page(StudentQueryParam studentQueryParam);

    /**
     * 删除student
     */
    void delete(List<Integer> ids);

    /**
     * 新增学生信息
     */
    void save(Student student);

    /**
     * 根据id查询学生信息，数据回显
     */
    Student getById(Integer id);

    /**
     * 修改学生信息
     * @param student
     */
    void update(Student student);

    /**
     * 修改学生违规信息
     */
    void updateViolation(Integer id, Short score);
}
