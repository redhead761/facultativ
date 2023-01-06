package com.epam.facultative.controller.filters;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebFilter(filterName = "LocaleFilter")
public class LocaleFilter implements Filter {
    private static final String LOCALE = "language";
    private static final String REFERER = "referer";

    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        HttpServletResponse resp = (HttpServletResponse) servletResponse;
        String locale = req.getParameter(LOCALE);
        if (!isBlank(locale)) {
            req.getSession().setAttribute(LOCALE, locale);
            resp.sendRedirect((req).getHeader(REFERER));
        } else {
            filterChain.doFilter(servletRequest, servletResponse);
        }
    }

    private boolean isBlank(String locale) {
        return locale == null || locale.isEmpty();
    }

}