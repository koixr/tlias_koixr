package com.itheima.controller;

import com.itheima.pojo.Emp;
import com.itheima.pojo.LoginInfo;
import com.itheima.pojo.Result;
import com.itheima.service.EmpService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequestMapping
@RestController
public class LoginController {

    @Autowired
    EmpService  empService;

    /**
     *  账号登录
     *  post请求更为安全
     *  会话技术：浏览器和web服务器的多次请求和响应 一个浏览器和web是一次会话
     *  会话跟踪技术：Cookie、Session，检测请求是否是一个会话中的，实现浏览器和web服务器的多次请求和响应之间的数据共享
     *  cookie：数据存储在浏览器，大小小，只能由浏览器访问，不能被其他程序访问 响应头set_cookie:name=value; 请求头cookie:name=value
     *         http支持
     *         移动端无法使用cookie，存储在客户端用户可以手动操作禁止，cookie不可以跨域（协议，域名，端口）
     *  session：基于cookie的响应头set_cookie:JSESSIONID=1; 请求头cookie:JSESSIONID=1
     *          存储在服务端安全
     *          服务器集群环境下无法使用
     *          cookie的缺点他都有
     *  负载均衡服务器
     *  令牌方案：目前企业使用最多的方案
     *          支持pc 端和移动端小程序
     *          解决集群环境下的认证
     *          减轻服务器存储压力
     *          但是程序员自己实现
     * JWT: header头.payload有效载荷.signature签名   base64编码 =一般是补位符号 签名不是base64编码融入头和有效载荷 使用签名算法创建
     *      引入jjwt依赖
     *      eyJhbGciOiJIUzI1NiJ9.eyJpZCI6MSwiZXhwIjoxNzQ4MjMxMTUwLCJ1c2VybmFtZSI6ImFkbWluIn0.h565S1iynDyyMslCVrzlWHGPAYTjjKhbEv9Tt3iYMsg
     *      被篡改 或者 过期 都无法使用令牌
     *      注意builder和parser密钥一致
     * Servlet
     * Filter：拦截请求和响应，对请求和响应进行预处理，对请求和响应进行增强 eg：登录校验 统一编码处理 敏感字符处理
     *          定义一个类实现filter接口 并实现方法
     *          filter类上加入@WebFilter注解 引导启动类上加@ServletComponentScan
     * listener
     * Interceptor：spring提供的操作服务 拦截请求和响应，对请求和响应进行预处理，对请求和响应进行增强 eg：登录校验 统一编码处理 敏感字符处理
     */
    @PostMapping("/login")
        public Result login(@RequestBody Emp emp){
        log.info("员工登录，员工信息：{}", emp);
        LoginInfo loginInfo = empService.login(emp);
        if (loginInfo != null)  return Result.success(loginInfo);
        return Result.error("用户名或密码错误");
    }
}
