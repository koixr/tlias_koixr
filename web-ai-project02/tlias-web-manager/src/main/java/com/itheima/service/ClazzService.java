package com.itheima.service;

import com.itheima.pojo.Clazz;
import com.itheima.pojo.ClazzQueryParam;
import com.itheima.pojo.PageResult;

import java.util.List;

public interface ClazzService {

    /**
     * 查询所有班级信息
     * @return
     */
    PageResult<Clazz> page(ClazzQueryParam clazzQueryParam);

    /**
     * 删除班级信息
     */
    void deleteById(Integer id);

    /**
     * 新增班级信息
     */
    void save(Clazz clazz);

    /**
     * 修改班级信息
     */
    void update(Clazz clazz);

    /**
     * 根据id查询班级信息
     */
    Clazz getById(Integer id);

    /**
     * 查询所有班级信息
     */
    List<Clazz> listAll();
}
