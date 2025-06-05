package com.itheima.service.impl;

import com.itheima.anno.LogOperation;
import com.itheima.mapper.DeptMapper;
import com.itheima.pojo.Dept;
import com.itheima.service.DeptService;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class DeptServiceImpl implements DeptService {

    @Autowired  //tab错了，导致deptMapper可能为null
    private DeptMapper deptMapper;

    @LogOperation //使自己定义的注解生效
    @Override
    public List<Dept> findAll() {
        return deptMapper.findAll();
    }

    @LogOperation
    @Override
    public void deleteById(Integer id) {
        deptMapper.deleteById(id);
    }

    @Override
    public void insert(Dept dept) {
        //1.补全基础属性
        dept.setCreateTime(LocalDateTime.now());
        dept.setUpdateTime(LocalDateTime.now());
        //2.调用mapper
        deptMapper.insert(dept);
    }

    @Override
    public Dept getInfo(Integer id) {
        return deptMapper.getById(id);
    }

    @Override
    public void update(Dept dept) {
        dept.setUpdateTime(LocalDateTime.now());
        deptMapper.update(dept);
    }
}
