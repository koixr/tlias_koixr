package com.itheima.interceptor;

import com.itheima.utils.JwtUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

/**
 * 令牌校验拦截器
 */
//webconfig 控制拦截器的拦截路径
@Slf4j
@Component
public class TokenInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        //1.获取请求路径
        String reqURI = request.getRequestURI();

        //2.判断路径是否是登录路径
        if(reqURI.contains("/login"))

        //3.如果是，放行
        {
            log.info("登录请求,放行");
            //interceptor放行控制返回值即可
            return true;
        }

        //获取请求头中的token
        String token = request.getHeader("token");

        //4.如果不是，判断请求头中是否有token，没有token，401
        if(token == null || token.isEmpty()){
            log.info("令牌为空， 响应401");
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);//即401
            return false;
        }

        //5.如果有，解析token
        try{
            JwtUtils.parseToken(token);
        }catch (Exception e){
            log.info("令牌解析失败，响应401");
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return false;
        }
        //6.解析成功，放行
        log.info("令牌解析成功， 放行");
        return true;
    }
}
