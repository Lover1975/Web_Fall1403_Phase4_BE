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

public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {

        String authHeader = request.getHeader("Authorization");

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            System.out.println("[JwtAuthenticationFilter] No valid Authorization header found.");
            filterChain.doFilter(request, response);
            return;
        }

        String jwt = authHeader.substring(7);
        System.out.println("[JwtAuthenticationFilter] Token extracted from request: " + jwt);

        // در اینجا تمامی توکن‌هایی که سرور دارد (allTokens) را چاپ می‌کنیم
        System.out.println("[JwtAuthenticationFilter] All tokens in server's list =>");
        for (String t : JWTUtil.allTokens) {
            System.out.println("   " + t);
        }

        try {
            String username = JWTUtil.getUsernameFromToken(jwt);
            System.out.println("[JwtAuthenticationFilter] Token is valid. Username = " + username);

            var authToken = new UsernamePasswordAuthenticationToken(username, null, null);
            SecurityContextHolder.getContext().setAuthentication(authToken);

            filterChain.doFilter(request, response);

        } catch (JwtException e) {
            System.out.println("[JwtAuthenticationFilter] JWT Error: " + e.getMessage());
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid or expired token");
        }
    }
}
