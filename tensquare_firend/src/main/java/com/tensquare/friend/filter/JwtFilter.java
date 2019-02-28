package com.tensquare.friend.filter;

import entity.Identity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import util.JwtUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class JwtFilter extends HandlerInterceptorAdapter {
    @Autowired
    private JwtUtil jwtUtil;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        System.err.println("经过了拦截器");
        final String authorization = request.getHeader("Authorization");
        if (authorization != null && authorization.startsWith("Bearer ")) {
            final String token = authorization.substring(7);
            Identity identity = jwtUtil.parseToken(token);
            if (identity != null) {
                if (identity.getRole().equals("USER")) {
                    request.setAttribute("USER_CLAIMS", identity);
                } else if (identity.getRole().equals("ADMIN")) {
                    request.setAttribute("ADMIN_CLAIMS", identity);
                }
            }
        }
        return true;
    }
}
