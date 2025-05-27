package com.itheima.mapper;

import com.itheima.pojo.EmpExpr;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 员工工作经历
 */
@Mapper
public interface EmpExprMapper {

    /**
     * 批量保存员工工作经历
     * @param exprList
     */
//    批量保存工作经历 动态sql标签 遍历循环
    void insertBatch(List<EmpExpr> exprList);

    /**
     * 根据员工id删除员工工作经历
     */
    void deleteByEmpId(List<Integer> empIds);
}
