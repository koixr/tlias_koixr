package com.itheima.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.itheima.mapper.StudentMapper;
import com.itheima.pojo.PageResult;
import com.itheima.pojo.Student;
import com.itheima.pojo.StudentQueryParam;
import com.itheima.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class StudentServiceImpl implements StudentService {

    @Autowired
    private StudentMapper studentMapper;

    /**
     * 分页查询
     * @param studentQueryParam
     * @return
     */
    @Override
    public PageResult<Student> page(StudentQueryParam studentQueryParam) {
        PageHelper.startPage(studentQueryParam.getPage(),studentQueryParam.getPageSize());
        List<Student> studentList = studentMapper.list(studentQueryParam);
        Page<Student> p =(Page<Student>) studentList;
        return new PageResult<>(p.getTotal(),p.getResult());
    }

    /**
     * 批量删除student
     * @param ids
     */
    @Override
    public void delete(List<Integer> ids) {
        studentMapper.deleteByIds(ids);
    }

    /**
     * 新增学生信息
     */
    @Override
    public void save(Student student) {
        student.setCreateTime(LocalDateTime.now());
        student.setUpdateTime(LocalDateTime.now());
        studentMapper.insert(student);
    }

    /**
     * 根据id查询学生信息，用于回显
     */
    @Override
    public Student getById(Integer id) {
        return studentMapper.getById(id);
    }

    /**
     * 修改学生信息
     */
    @Override
    public void update(Student student) {
        student.setUpdateTime(LocalDateTime.now());
        studentMapper.update(student);
    }

    /**
     * 违纪修改
     */
    @Override
    public void updateViolation(Integer id, Short score) {
        if(score>0) {
            Student student = studentMapper.getById(id);
            Short violationCount = (short) (student.getViolationCount() + 1);
            Short violationScore = (short) (student.getViolationScore() + score);
            student.setViolationCount(violationCount);
            student.setViolationScore(violationScore);
            studentMapper.updateViolation(student);
        }
    }
}
