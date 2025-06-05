package com.itheima.aop;

import com.itheima.pojo.Result;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.boot.autoconfigure.condition.ConditionalOnNotWarDeployment;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.lang.reflect.Array;
import java.util.Arrays;

@Slf4j
@Order(1) // 优先级,数字小的优先运行，没有order注解，则按照aspect类的类名顺序执行
//@Aspect //标志不是普通类，是切面类aop
@Component  //  交给springboot容器管理
public class RecordTimeAspect {

    //统一抽取管理切入点表达式 ？代表可以省略
    //execution(访问修饰符？ 返回值类型 包名.类名.？方法名(参数列表) throws 异常？)
    //* 单个任意符号，可以通配任意返回值，包名，类名，方法名，也可以是一部分
    //.. 多个连续的任意符号，可以通配任意层级的包，或是任意类型和个数的参数
    @Pointcut("execution(* com.itheima.service.impl.*.*(..))")
    private void pt(){}

    //哪里箭头是灰色的什么时候运行
    //前置通知 目标运行方法之前运行
    //@Before("execution(public void com.itheima.service.impl.DeptServiceImpl.delete(Java.lang.Integer))")
    //@Before("execution(void com.itheima.service.impl.DeptServiceImpl.delete(Java.lang.Integer))")
    //@Before("execution(void delete(Java.lang.Integer))") 省略包名，类名，不建议，性能过低
    //@Before("execution(* com.itheima.service.impl.DeptServiceImpl.delete(Java.lang.Integer))")
    //@Before("execution(* com.*.service.impl.DeptServiceImpl.delete(Java.lang.Integer))")
    //@Before("execution(* com.itheima.service.impl.*.delete(Java.lang.Integer))")
    //@Before("execution(* com.itheima.service.impl.*.*(Java.lang.Integer))")
    //@Before("execution(* com.itheima.service.impl.*.*(*))")
    //@Before("execution(* com.itheima.service.impl.*.del*(*))")
    //@Before("execution(* com.itheima.service.impl.*.*e(..))")
    //@Before("execution(* com..service.impl.*.*(..))")

    //@Before("execution(* com..service.impl.DeptServiceImpl.list(..)) ||" +
    //        "execution(* com..service.impl.DeptServiceImpl.delete(..))")

//    @Before("@annotation(com.itheima.anno.LogOperation)") //优先考虑execution表达式 若不方便以后在使用annotation
    // @before通过joinPoint 获取连接点信息
    @Before("pt()")
    public void before(JoinPoint joinPoint){
        log.info("before...");
        //1.获取目标对象
        Object taraget = joinPoint.getTarget();
        log.info("target: {}", taraget);

        //2.获取目标类
        String className = taraget.getClass().getName();
        log.info("className: {}", className);

        //3.获取目标方法
        String methodName = joinPoint.getSignature().getName();
        log.info("methodName: {}", methodName);

        //4.获取目标参数
        Object[] args = joinPoint.getArgs();
        log.info("args: {}", Arrays.toString(args));

    }

    //切面    其他所有可以作为连接点和切入点的方法
    // 五种通知类型：around（必须使用pjp让原始方法运行，返回类型是Object） before after afterreturning afterthrowing
    @Around("pt()") //切入点表达式 和目标对象
    //环绕通知 目标方法前和后运行
    //环绕通知使用ProceedingJoinPoint对象获取目标对象
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
