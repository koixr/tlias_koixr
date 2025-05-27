package com.itheima.exception;

import com.itheima.pojo.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
//全局异常处理需要在类上加入@RestControllerAdvice注解
@RestControllerAdvice
public class GlobalExceptionHandler {

    //全局异常处理器需要在方法前添加@ExceptionHandler注解
    @ExceptionHandler
    public Result handleException(Exception e){
        log.error("服务器发生异常:{}",e);
        return Result.error("出错了，请联系工作人员");
    }

    //通过继承关系从下向上匹配，所以执行子类异常处理器
    @ExceptionHandler
    public Result handleException(DuplicateKeyException e){
        log.error("服务器发生异常:{}",e);
        String message = e.getMessage();
        if (message.contains("Duplicate entry")){
            int i = message.indexOf("Duplicate entry");
            String errMsg = message.substring(i);
            String[] msg = errMsg.split(" ");
            return Result.error(msg[2] + "已存在");
        }
        return Result.error("出错了，请联系工作人员");
    }
}
