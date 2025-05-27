package com.itheima;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.core.DataClassRowMapper;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class JwtTest {

    /**
     * 测试生成JWT
     * eyJhbGciOiJIUzI1NiJ9.eyJpZCI6MSwiZXhwIjoxNzQ4MjMxMTUwLCJ1c2VybmFtZSI6ImFkbWluIn0.h565S1iynDyyMslCVrzlWHGPAYTjjKhbEv9Tt3iYMsg
     */
    @Test
    public void testGenerate() {
        Map<String, Object> dataMap = new HashMap<>();
        dataMap.put("id", 1);
        dataMap.put("username", "admin");

        String jwt = Jwts.builder()
                .signWith(SignatureAlgorithm.HS256, "5a6L5a6256a+")//指定机密算法和密钥
                .setClaims(dataMap)//添加自定义信息
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60))// 设置过期时间
                .compact();//生成令牌
        System.out.println(jwt);
    }
    /**
     * 测试解析JWT
     */
    @Test
    public void testParse() {
        String jwt = "eyJhbGciOiJIUzI1NiJ9.eyJpZCI6MSwiZXhwIjoxNzQ4MjMxMTUwLCJ1c2VybmFtZSI6ImFkbWluIn0.h565S1iynDyyMslCVrzlWHGPAYTjjKhbEv9Tt3iYMsg";
        Claims claims = Jwts.parser()//解密令牌
                .setSigningKey("5a6L5a6256a+")//设置密钥
                .parseClaimsJws(jwt)//设置字符串
                .getBody();//开始解密
        System.out.println(claims);
    }
}