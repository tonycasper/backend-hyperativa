package com.hyperativa.challenge.filter;

import org.springframework.stereotype.Component;

import javax.servlet.FilterChain;
import javax.servlet.Filter;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.IOException;

@Component
public class LoggingFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        System.out.println("Request: " + request.toString());
        chain.doFilter(request, response);
        System.out.println("Response: " + response.toString());
    }

    @Override
    public void destroy() {
    }
}