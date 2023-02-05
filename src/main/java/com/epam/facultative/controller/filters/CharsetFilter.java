package com.epam.facultative.controller.filters;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;

import java.io.IOException;

/**
 * Sets encoding for any values from view
 *
 * @author Oleksandr Panchenko
 * @version 1.0
 */
@WebFilter(filterName = "CharsetFilter")
public class CharsetFilter implements Filter {
    /**
     * Sets default encoding for any values from user.
     *
     * @param servletRequest  passed by application
     * @param servletResponse passed by application
     * @param filterChain     passed by application
     */
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        servletRequest.setCharacterEncoding("UTF-8");
        servletResponse.setContentType("text/html; charset=UTF-8");
        servletResponse.setCharacterEncoding("UTF-8");
        filterChain.doFilter(servletRequest, servletResponse);
    }
}
