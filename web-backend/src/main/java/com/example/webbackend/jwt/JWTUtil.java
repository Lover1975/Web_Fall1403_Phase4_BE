package com.example.webbackend.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.nio.charset.StandardCharsets;
import java.util.Date;

/**
 * کلاس کمکی برای ساخت و اعتبارسنجی JWT
 */
public class JWTUtil {

    // کلید سری در پروژه واقعی باید امن‌تر نگهداری شود
    private static final String SECRET_KEY = "1234567890123456789012345678901212345678901234567890123456789012";


    // زمان انقضای توکن (مثلاً 24 ساعت)
    private static final long EXPIRATION_TIME = 24 * 60 * 60 * 1000; // یک روز

    /**
     * ساخت توکن با موضوع username
     */
    public static String generateToken(String username) {
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY.getBytes(StandardCharsets.UTF_8))
                .compact();
    }




    /*
    *
     * پارس و بررسی یک توکن JWT
     */
    public static Claims validateToken(String token) {
        return Jwts.parser()
                .setSigningKey(SECRET_KEY)
                .parseClaimsJws(token)
                .getBody();
    }

    /**
     * استخراج نام کاربری (Subject) از توکن
     */
    public static String getUsernameFromToken(String token) {
        Claims claims = validateToken(token);
        return claims.getSubject();
    }
}
