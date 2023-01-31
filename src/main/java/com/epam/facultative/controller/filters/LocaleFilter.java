package com.epam.facultative.controller.filters;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

/**
 * Sets and changes locale
 *
 * @author Oleksandr Panchenko
 * @version 1.0
 */
@WebFilter(filterName = "LocaleFilter")
public class LocaleFilter extends HttpFilter {
    private static final String LOCALE = "language";
    private static final String REFERER = "referer";

    /**
     * Checks if request contains locale parameter and sets locale to session as attribute if present.
     * Returns previous page in this case.
     * In other case checks if locale presents in session.
     *
     * @param req         passed by application
     * @param resp        passed by application
     * @param filterChain passed by application
     */
    public void doFilter(HttpServletRequest req, HttpServletResponse resp, FilterChain filterChain) throws IOException, ServletException {
        String locale = req.getParameter(LOCALE);
        if (!isBlank(locale)) {
            req.getSession().setAttribute(LOCALE, locale);
            resp.sendRedirect((req).getHeader(REFERER));
        } else {
            String sessionLocale = (String) req.getSession().getAttribute(LOCALE);
            if (isBlank(sessionLocale)) {
                req.getSession().setAttribute(LOCALE, "en");
            }
            filterChain.doFilter(req, resp);
        }
    }

    private boolean isBlank(String locale) {
        return locale == null || locale.isEmpty();
    }

}