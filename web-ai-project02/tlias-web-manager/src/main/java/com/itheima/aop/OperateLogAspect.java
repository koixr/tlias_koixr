package com.itheima.aop;


import com.itheima.mapper.OperateLogMapper;
import com.itheima.pojo.OperateLog;
import com.itheima.utils.CurrentHolder;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

import java.lang.reflect.Method;
import java.time.LocalDateTime;
import java.util.Arrays;


/**
 * 操作日志切面
 */
@Aspect
@Component
@Slf4j
public class OperateLogAspect {

    //spring三种注入方法：注解、set、构造方法
    @Autowired
    private OperateLogMapper operateLogMapper;
//    private final OperateLogMapper operateLogMapper;
//    public OperateLogAspect(OperateLogMapper operateLogMapper) {
//        this.operateLogMapper = operateLogMapper;
//    } //构造方法注入

    // 环绕通知
    @Around("@annotation(com.itheima.anno.Log)")
    public Object logOperation(ProceedingJoinPoint joinPoint) throws Throwable {
        long startTime = System.currentTimeMillis();

        // 执行目标方法
        Object result = joinPoint.proceed();

        //  获取方法执行耗时
        long endTime = System.currentTimeMillis();
        long costTime = endTime - startTime;

        //构建日志实体
        com.itheima.pojo.OperateLog  olog = new com.itheima.pojo.OperateLog();
        olog.setOperateEmpId(getCurrentUserId());
        olog.setOperateTime(LocalDateTime.now());
        olog.setCostTime(costTime);
        olog.setClassName(joinPoint.getTarget().getClass().getName());
        olog.setMethodName(joinPoint.getSignature().getName());
        olog.setMethodParams(Arrays.toString(joinPoint.getArgs()));
        olog.setReturnValue(result!=null?result.toString():"无返回值");

        //日志记录对象和日志实体名称重复
        log.info("操作日志：{}", olog);

        operateLogMapper.insert(olog);

        return result;
    }

    private Integer getCurrentUserId() {
        return CurrentHolder.getCurrentId();
    }
}