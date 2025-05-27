package com.itheima.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.itheima.controller.EmpController;
import com.itheima.mapper.ClazzMapper;
import com.itheima.mapper.EmpMapper;
import com.itheima.pojo.Clazz;
import com.itheima.pojo.ClazzQueryParam;
import com.itheima.pojo.Dept;
import com.itheima.pojo.PageResult;
import com.itheima.service.ClazzService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Service
public class ClazzServiceImpl implements ClazzService {

    @Autowired
    private ClazzMapper clazzMapper;
    @Autowired
    private EmpMapper empMapper;

    /**
     * 查询所有班级信息
     * @return
     */
    @Override
    public PageResult<Clazz> page(ClazzQueryParam clazzQueryParam) {
        PageHelper.startPage(clazzQueryParam.getPage(),clazzQueryParam.getPageSize());
        List<Clazz> clazzList = clazzMapper.list(clazzQueryParam);
        Page<Clazz> p = (Page<Clazz>) clazzList;
        return new PageResult<>(p.getTotal(), p.getResult());
    }

    /**
     * 删除班级信息
     */
    @Override
    public void deleteById(Integer id) {
        clazzMapper.deleteById(id);
    }

    /**
     * 新增班级信息
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void save(Clazz clazz){
        //补全基础属性
//        LocalDateTime date = LocalDateTime.now();
        clazz.setCreateTime(LocalDateTime.now());
        clazz.setUpdateTime(LocalDateTime.now());
        //调用mapper
        empMapper.listAll();
        clazzMapper.save(clazz);

    }

    /**
     * 根据id查询班级信息
     */
    @Override
    public Clazz getById(Integer id) {
        return clazzMapper.getById(id);
    }

    /**
     * 修改班级信息
     */
    @Override
    public void update(Clazz clazz) {
        //补全基础属性
        clazz.setUpdateTime(LocalDateTime.now());
        //调用mapper
        clazzMapper.updateById(clazz);
    }

    /**
     * 查询所有班级信息
     */
    @Override
    public List<Clazz> listAll() {
        return clazzMapper.listAll();
    }
}
