package com.itheima.filter;

import ch.qos.logback.classic.Logger;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import lombok.Builder;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

//过滤器执行流程
//放行前-> 放行-> 资源->放行后
// 拦截路径/*和/emp/* 第一个拦截所有，第二个拦截/emp下的所有资源
//过滤器链
//谁的类名在前就先执行谁
//多个过滤器，多个过滤器执行顺序，按顺序执行，如果第一个过滤器放行，第二个过滤器执行，如果第一个过滤器不放行，第二个过滤器不执行
//直接注释注解就不会执行
//@WebFilter(urlPatterns = "/*")
@Slf4j
//注意不要导错包 servlet下的filter包
public class DemoFilter implements Filter {

    //初始化方法，只在web服务器启动时执行一次
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        log.info("DemoFilter init初始化");
    }

    //拦截到，执行多次
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        log.info("DemoFilter doFilterlan拦截请求");
        //放行
        chain.doFilter(request, response);
    }

    //销毁方法，只在web服务器关闭时执行一次
    @Override
    public void destroy() {
        log.info("DemoFilter destroy结束");
    }
}
