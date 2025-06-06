package com.itheima.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

//filter是web级别的 interceptor是springboot集成的 所以先运行filter后interceptor
@Slf4j
@Component
public class DemoInterceptor implements HandlerInterceptor {
    //在目标资源方法运行之前运行 true放行 false拦截
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        log.info("preHandle...运行");
        return HandlerInterceptor.super.preHandle(request, response, handler);
    }
    //在目标资源方法运行之后运行
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        log.info("postHandle...运行");
        HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
    }
    //视图渲染之后运行
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        log.info("afterCompletion...运行");
        HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
    }
}
