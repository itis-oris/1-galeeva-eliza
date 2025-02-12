package com.workout.filters;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebFilter("/*")
public class AuthenticationFilter implements Filter {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        if (httpServletRequest.getServletPath().startsWith("/static/") ||
                httpServletRequest.getServletPath().startsWith("/login_check") ||
                httpServletRequest.getServletPath().startsWith("/login")) {
            filterChain.doFilter(request, response);
        } else {
            HttpSession session = httpServletRequest.getSession(false);
            if (session != null) {
                filterChain.doFilter(request, response);
            } else {
                request.getRequestDispatcher("/login").forward(request, response);
            }
        }
    }
}
