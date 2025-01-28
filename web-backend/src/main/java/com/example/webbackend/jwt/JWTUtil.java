package com.example.webbackend.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class JWTUtil {

    private static final String SECRET_KEY = "12345678901234567890123456789012";
    private static final long EXPIRATION_TIME = 24 * 60 * 60 * 1000; // یک روز

    // فهرست همهٔ توکن‌های ساخته‌شده / موردانتظار
    public static List<String> allTokens = new ArrayList<>();

    // متد ساخت توکن
    public static String generateToken(String username) {
        String token = Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY.getBytes(StandardCharsets.UTF_8))
                .compact();

        // اضافه کردن به فهرست
        allTokens.add(token);
        System.out.println("[JWTUtil] Generated token => " + token);

        return token;
    }

    // اعتبارسنجی
    public static Claims validateToken(String token) {
        return Jwts.parser()
                .setSigningKey(SECRET_KEY.getBytes(StandardCharsets.UTF_8))
                .parseClaimsJws(token)
                .getBody();
    }

    // گرفتن سابجکت (username)
    public static String getUsernameFromToken(String token) {
        Claims claims = validateToken(token);
        return claims.getSubject();
    }
}
