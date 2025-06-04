package com.itheima.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect //标志不是普通类，是切面类aop
@Component  //  交给springboot容器管理
public class RecordTimeAspect {

    //统一抽取管理切入点表达式
    @Pointcut("execution(* com.itheima.service.impl.*.*(..))")
    private void pt(){}

    //哪里箭头是灰色的什么时候运行
    //前置通知 目标运行方法之前运行
    @Before("pt()")
    public void before(){
        log.info("before...");
    }

    //切面    其他所有可以作为连接点和切入点的方法
    // 五种通知类型：around（必须使用pjp让原始方法运行，返回类型是Object） before after afterreturning afterthrowing
    @Around("pt()") //切入点表达式 和目标对象
    //环绕通知 目标方法前和后运行
    public Object recordTime(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        //1.记录开始时间
        log.info("around ... before...");
        long begin = System.currentTimeMillis();

        //2.执行方法
        Object result = proceedingJoinPoint.proceed(); //代理对象

        //3.记录结束时间
        long end = System.currentTimeMillis();
        log.info("方法{}运行时间：{}ms",proceedingJoinPoint.getSignature().getName(),(end-begin));
        log.info("around ... after...");
        return result;
    }

    // 后置通知 无论是否异常都运行
    @After("pt()")
    public void after(){
        log.info("after ...");
    }

    // 返回通知 目标方法正常返回数据时运行
    @AfterReturning("pt()")
    public void afterReturning(){
        log.info("afterReturning ...");
    }

    //异常通知 目标方法出现异常时运行
    @AfterThrowing("pt()")
    public void afterThrowing(){
        log.info("afterThrowing ...");
    }
}
