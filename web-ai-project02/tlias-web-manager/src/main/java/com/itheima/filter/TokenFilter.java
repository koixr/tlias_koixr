package com.itheima.filter;

import com.itheima.utils.CurrentHolder;
import com.itheima.utils.JwtUtils;
import io.jsonwebtoken.Claims;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

import javax.annotation.processing.Filer;
import java.io.IOException;

@Slf4j
@WebFilter(urlPatterns = "/*")
public class TokenFilter implements Filter {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {
        //转换类型
        HttpServletRequest  req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;

        //1.获取请求路径
        String reqURI = req.getRequestURI();

        //2.判断路径是否是登录路径
        if(reqURI.contains("/login"))

        //3.如果是，放行
        {
            log.info("登录请求,放行");
            //filter放行使用doFilter函数
            chain.doFilter(request, response);
            return;
        }
        //获取请求头中的token
        String token = req.getHeader("token");
        //4.如果不是，判断请求头中是否有token，没有token，401
        if(token == null || token.isEmpty()){
            log.info("令牌为空， 响应401");
            resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);//即401
            return;
        }
        //5.如果有，解析token
        try{
            Claims claims = JwtUtils.parseToken(token);
            Integer employeeId = (Integer) claims.get("id");
            CurrentHolder.setCurrentId(employeeId);
            log.info("令牌解析成功， 获取员工id:{}", employeeId);
        }catch (Exception e){
            log.info("令牌解析失败，响应401");
            resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }
        //6.解析成功，放行
        log.info("令牌解析成功， 放行");
        chain.doFilter(request,response);

        //删除threadlocal中的数据 在线程执行完之后，线程会自动销毁
        CurrentHolder.remove();
    }
}
