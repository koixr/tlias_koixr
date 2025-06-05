package com.itheima.anno;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD) //注解作用在方法上
@Retention(RetentionPolicy.RUNTIME) //注解在运行时存在生效
public @interface Log {
}
