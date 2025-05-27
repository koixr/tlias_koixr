package com.itheima.service;


import com.itheima.pojo.Dept;

import java.util.List;

public interface DeptService {

    /**
     *  查询所有部门数据
     * @return
     */
    List<Dept> findAll();

    /**
     * 根据id删除部门
     * @param id
     */
    void deleteById(Integer id);

    /**
     * 新增部门
     * @param dept
     */
    void insert(Dept dept);

    /**
     * 根据id查询部门
     * @return
     */
    Dept getInfo(Integer id);

    /**
     * 修改部门
     * @param dept
     */
    void update(Dept dept);
}
