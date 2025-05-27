package com.itheima.service.impl;

import com.itheima.mapper.EmpMapper;
import com.itheima.mapper.StudentMapper;
import com.itheima.pojo.ClazzCountOption;
import com.itheima.pojo.JobOption;
import com.itheima.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

//将其交给IOC容器管理
@Service
public class ReportServiceImpl implements ReportService {

    @Autowired
    private EmpMapper empMapper;

    @Autowired
    private StudentMapper studentMapper;

    @Override
    public JobOption getEmpJobData() {
        //1.查询员工数据
        List<Map<String,Object>> list = empMapper.coutEmpJobData();

        //2.创建两个集合，分别存储数据 最后需要tolist（）
        List<Object> jobList = list.stream().map(datamap -> datamap.get("pos")).toList();
        List<Object> dataList = list.stream().map(datamap -> datamap.get("num")).toList();

        return new JobOption(jobList,dataList);
    }

    @Override
    public List<Map<String, Object>> getEmpGenderData() {
        return empMapper.coutEmpGenderData();
    }

    /**
     * 获取学生学历数据
     * @return
     */
    @Override
    public List<Map<String, Object>> getStudentDegreeData() {
        return studentMapper.coutStudentDegreeData();
    }

    /**
     * 获取学生班级人数数据
     * @return
     */
    @Override
    public ClazzCountOption studentCountData() {
        //1.查询班级数据
        List<Map<String,Object>> list = studentMapper.studentCountData();

        //2.创建两个集合，分别存储数据 最后需要tolist（）
        List<Object> clazzList = list.stream().map(datamap -> datamap.get("clazz")).toList();
        List<Object> dataList = list.stream().map(datamap -> datamap.get("num")).toList();
        return new ClazzCountOption(clazzList,dataList);
    }
}
