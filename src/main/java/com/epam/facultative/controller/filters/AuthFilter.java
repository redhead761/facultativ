package com.epam.facultative.controller.filters;

import com.epam.facultative.model.dto.UserDTO;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

import static com.epam.facultative.controller.constants.ActionAccessConstants.*;
import static com.epam.facultative.controller.constants.PageAccessConstants.*;

@WebFilter(filterName = "AuthFilter")
public class AuthFilter implements Filter {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        HttpServletResponse resp = (HttpServletResponse) servletResponse;
        UserDTO user = (UserDTO) req.getSession().getAttribute("user");
        String action = req.getParameter("action");
        String servletPath = req.getServletPath();
        if (user == null) {
            checkNoLoggedAccess(req, resp, servletPath, action, filterChain);
        } else {
            switch (user.getRole()) {
                case ADMIN -> checkAdminAccess(req, resp, servletPath, action, filterChain);
                case TEACHER -> checkTeacherAccess(req, resp, servletPath, action, filterChain);
                case STUDENT -> checkStudentAccess(req, resp, servletPath, action, filterChain);
            }
        }
    }

    private void checkNoLoggedAccess(HttpServletRequest req, HttpServletResponse resp, String servletPath, String action, FilterChain filterChain) throws ServletException, IOException {
        if (getNoLoggedUserActions().contains(action) || getNoLoggedUserPages().contains(servletPath.substring(1))) {
            filterChain.doFilter(req, resp);
        } else {
            accessDenied(req, resp);
        }
    }

    private void checkAdminAccess(HttpServletRequest req, HttpServletResponse resp, String servletPath, String action, FilterChain filterChain) throws ServletException, IOException {
        if (getAdminActions().contains(action) || getAdminPages().contains(servletPath.substring(1))) {
            filterChain.doFilter(req, resp);
        } else {
            accessDenied(req, resp);
        }
    }

    private void checkTeacherAccess(HttpServletRequest req, HttpServletResponse resp, String servletPath, String action, FilterChain filterChain) throws ServletException, IOException {
        if (getTeacherActions().contains(action) || getTeacherPages().contains(servletPath.substring(1))) {
            filterChain.doFilter(req, resp);
        } else {
            accessDenied(req, resp);
        }
    }

    private void checkStudentAccess(HttpServletRequest req, HttpServletResponse resp, String servletPath, String action, FilterChain filterChain) throws ServletException, IOException {
        if (getStudentActions().contains(action) || getStudentPages().contains(servletPath.substring(1))) {
            filterChain.doFilter(req, resp);
        } else {
            accessDenied(req, resp);
        }
    }

    private void accessDenied(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        req.getSession().invalidate();
        resp.sendRedirect(req.getContextPath() + "/auth.jsp");
    }
}