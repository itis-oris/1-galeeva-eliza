package com.workout.filters;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebFilter("/*")
public class RegistrationFilter implements Filter {


    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {

        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        if (httpServletRequest.getServletPath().startsWith("/registration_check") ||
                httpServletRequest.getServletPath().startsWith("/register")) {
            filterChain.doFilter(request, response);
        } else {
            HttpSession session = httpServletRequest.getSession(false);
            if (session != null) {
                filterChain.doFilter(request, response);
            } else {
                request.getRequestDispatcher("/register").forward(request, response);
            }
        }
    }
}