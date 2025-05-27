package com.itheima.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.itheima.mapper.EmpExprMapper;
import com.itheima.mapper.EmpMapper;
import com.itheima.pojo.*;
import com.itheima.service.EmpLogService;
import com.itheima.service.EmpService;
import com.itheima.utils.JwtUtils;
import lombok.extern.slf4j.Slf4j;
import net.sf.jsqlparser.expression.operators.relational.ExpressionList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.lang.reflect.Array;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//EmpExpr使用同一个Service即可
@Slf4j
@Service
public class EmpServiceImpl implements EmpService {

    @Autowired
    private EmpMapper empMapper;

    //注入新的mapper接口
    @Autowired
    private EmpExprMapper empExprMapper;

    @Autowired
    private EmpLogService empLogService;


//  ------------------------原始分页查询----------------------------
//    @Override
//    public PageResult<Emp> page(Integer page, Integer pageSize) {
//        //调用mapper接口，查询总记录数,调用接口前提前注入
//        Long total = empMapper.count();
//        //调用mapper接口，分页查询
//        List<Emp> rows =empMapper.list((page-1)*pageSize,pageSize);
//        //封装结果PageResult<Emp>
//        return new PageResult<Emp>(total, rows);
//    }


//    /**
//     * 分页查询--使用PageHElper分页插件
//     * @param page
//     * @param pageSize
//     * @return
//     */
//    @Override
//    public PageResult<Emp> page(Integer page, Integer pageSize,String name, Integer  gender, LocalDate begin, LocalDate end) {
//        //1.设置分页参数
//        PageHelper.startPage(page,pageSize);
//        //2.执行查询
//        List<Emp> emplist = empMapper.list(name, gender, begin, end);
//        //3.封装结果
//        Page<Emp> p =(Page<Emp>) emplist;
//        return new PageResult<>(p.getTotal(),p.getResult());
//    }
    @Override
    public PageResult<Emp> page(EmpQueryParam empQueryParam) {
        //1.设置分页参数
        PageHelper.startPage(empQueryParam.getPage(),empQueryParam.getPageSize());
        //2.执行查询
        List<Emp> emplist = empMapper.list(empQueryParam);
        //3.封装结果
        //这一步是为甚恶魔???
        Page<Emp> p =(Page<Emp>) emplist;
        return new PageResult<>(p.getTotal(),p.getResult());
    }

    //开启事务注解，结束，提交，回滚   推荐加在实现的方法，多次实现增删改的方法上面 rollbackfor可以控制出现什么类型的异常去回滚，默认是runtime异常才回滚 propogation控制事务的传播行为
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void save(Emp emp){
        try {
            //1.保存员工基本信息
            emp.setCreateTime(LocalDateTime.now());
            emp.setUpdateTime(LocalDateTime.now());
            empMapper.insert(emp);

            //2.保存员工入职信息
            //创建变量
            List<EmpExpr> exprList = emp.getExprList();
            //用cu库函数判空
            if(!CollectionUtils.isEmpty(exprList)){
                //遍历集合，为emp——id赋值
                exprList.forEach(empExpr -> {
                    empExpr.setEmpId(emp.getId());
                });
                empExprMapper.insertBatch(exprList);
            }
        } finally {
            //记录操作日志
            EmpLog empLog = new EmpLog(null, LocalDateTime.now(), "新增员工" + emp);
            empLogService.insertLog(empLog);
        }


    }

    //多步操作开启事务管理 防止出现问题 rollback控制回滚，任何形式异常均回滚，否则只有运行时异常才回滚
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(List<Integer> ids){
        //1.删除员工基本信息
        empMapper.deleteById(ids);
        //2.删除员工工作经历信息
        empExprMapper.deleteByEmpId(ids);
    }

    @Override
    public Emp getById(Integer id) {
        //1.查询员工基本信息
        Emp emp = empMapper.getById(id);
        return emp;
    }

    //多次操作开启事务
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void update(Emp emp) {
        //1.更新员工基本信息 先更新修改时间
        emp.setUpdateTime(LocalDateTime.now());
        empMapper.updateById(emp);
        //2.更新员工工作经历信息
        //1.先删除 得到id之后使用Arrays中的方法获取id的List集合
        empExprMapper.deleteByEmpId(Arrays.asList(emp.getId()));
        //2.在添加新工作经历 先给emp——id赋值,前端传回的数据并没有赋值给emp——id，所以需要手动赋值
        List<EmpExpr> exprList = emp.getExprList();
        //集合工具判断传回的数据非空的话，遍历工作经历集合赋值
        if(!CollectionUtils.isEmpty(exprList)){
            //遍历集合，为emp——id赋值
            exprList.forEach(empExpr -> {
                empExpr.setEmpId(emp.getId());
            });
            empExprMapper.insertBatch(exprList);
        }

    }

    /**
     * clazz新增班级中展示班主任姓名
     */
    @Override
    public List<Emp> listAll() {
        List<Emp> list = empMapper.listAll();
        return list;
    }

    /**
     * 登录
     */
    @Override
    public LoginInfo login(Emp emp) {
        //1.调用mapper接口查询员工信息
        Emp e = empMapper.getByUsernameAndPassword(emp);
        //2.判断是否存在
        if (e != null)
        //3.如果存在，返回登录信息
        {
            log.info("员工登录，员工信息：{}", e);
            Map<String, Object> claims = new HashMap<>();
            claims.put("id", e.getId());
            claims.put("username", e.getUsername());
            String jwt = JwtUtils.generateToken(claims);//claims数据集合生成令牌jwt后才是token
            return new LoginInfo(e.getId(), e.getUsername(), e.getName(), jwt);
        }
        //4.不存在，返回null
        return null;
    }
}
