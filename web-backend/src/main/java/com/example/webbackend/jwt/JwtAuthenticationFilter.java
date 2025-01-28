package com.example.webbackend.jwt;

import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/**
 * فیلتر بررسی JWT در هر درخواست.
 */
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {

        // لاگ پایه: نمایش اینکه وارد فیلتر شدیم و مسیر چیست
        System.out.println("[JwtAuthenticationFilter] Request URI: " + request.getRequestURI());

        String authHeader = request.getHeader("Authorization");

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            // اگر Authorization header وجود نداشته باشد یا فرمت اشتباه باشد
            System.out.println("[JwtAuthenticationFilter] No valid Authorization header found.");
            filterChain.doFilter(request, response);
            return;
        }

        // جدا کردن توکن
        String jwt = authHeader.substring(7);
        System.out.println("[JwtAuthenticationFilter] Token extracted: " + jwt);

        try {
            // اگر بخواهید جزییات Claims هم ببینید می‌توانید در JWTUtil لاگ کنید
            String username = JWTUtil.getUsernameFromToken(jwt);

            // نمایش لاگ برای دیباگ
            System.out.println("[JwtAuthenticationFilter] Token is valid. Username = " + username);

            // ساخت ابجکت Authentication
            var authToken = new UsernamePasswordAuthenticationToken(username, null, null);

            // قرار دادن در SecurityContext
            SecurityContextHolder.getContext().setAuthentication(authToken);

            // ادامه فیلترها
            filterChain.doFilter(request, response);

        } catch (JwtException e) {
            // اگر خطای توکن یا تاریخ انقضا و غیره باشد
            System.out.println("[JwtAuthenticationFilter] JWT Error: " + e.getMessage());

            // می‌توانیم خطای 401 یا 403 بدهیم. اینجا 401:
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid or expired token");
            // عدم ادامه زنجیره فیلتر
        }
    }
}
