package com.itheima.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;
import java.util.Map;

public class JwtUtils {

    // 密钥（建议从配置文件中读取，这里写死用于示例）
    private static final String SECRET_KEY = "5a6L5a6256a+";

    // 过期时间（单位：毫秒），比如 1 小时
    private static final long EXPIRATION = 1000 * 60 * 60;

    /**
     * 生成JWT令牌
     *
     * @param claims 要嵌入的自定义信息（如用户ID、用户名等）
     * @return 返回生成的JWT字符串
     */
    public static String generateToken(Map<String, Object> claims) {
        return Jwts.builder()
                .setClaims(claims)
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION))
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                .compact();
    }

    /**
     * 解析JWT令牌，获取其中包含的信息
     *
     * @param token JWT令牌字符串
     * @return 返回解析后的Claims对象，里面包含了所有自定义信息
     */
    public static Claims parseToken(String token) {
        return Jwts.parser()
                .setSigningKey(SECRET_KEY)
                .parseClaimsJws(token)
                .getBody();
    }

    /**
     * 获取token中的某个字段值（例如 username、id 等）
     *
     * @param token JWT令牌字符串
     * @param key   要获取的字段名
     * @param clazz 字段对应的类型
     * @return 返回字段对应的值
     */
    public static <T> T getClaimFromToken(String token, String key, Class<T> clazz) {
        final Claims claims = parseToken(token);
        return claims.get(key, clazz);
    }

    /**
     * 获取token是否过期
     *
     * @param token JWT令牌字符串
     * @return 是否过期
     */
    public static boolean isTokenExpired(String token) {
        Date expiration = parseToken(token).getExpiration();
        return expiration.before(new Date());
    }
}