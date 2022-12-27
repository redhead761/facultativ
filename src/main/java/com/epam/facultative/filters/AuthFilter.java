package com.epam.facultative.filters;

import com.epam.facultative.dto.UserDTO;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebFilter(filterName = "AuthFilter")
public class AuthFilter implements Filter {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        System.out.println("In AUTH FILTER");
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        HttpServletResponse resp = (HttpServletResponse) servletResponse;

        UserDTO user = (UserDTO) req.getSession().getAttribute("user");
        String action = req.getParameter("action");
        String servletPath = req.getServletPath();
        System.out.println("User= " + user);
        System.out.println("Action =" + action);
//        System.out.println("URI =" + req.getRequestURI());
//        System.out.println("Query = " + req.getQueryString());
        System.out.println("Servlet = " + req.getServletPath());
        System.out.println("COntext = " + req.getContextPath());
        if (user == null) {
            if (ActionAccessConstants.getNoLoggedUserActions().contains(action)
                    || PageAccessConstants.getNoLoggedUserPages().contains(servletPath.substring(1))) {
                System.out.println("In case null");
                filterChain.doFilter(req, resp);
            } else {
                req.getSession().invalidate();
                resp.sendRedirect(req.getContextPath() + "/auth.jsp");
            }
        } else {
            System.out.println("IN ELSE");
            switch (user.getRole()) {
                case ADMIN -> {
                    System.out.println("In case ADMIN");
                    if (ActionAccessConstants.getAdminActions().contains(action)
                            || PageAccessConstants.getAdminPages().contains(servletPath.substring(1))) {
                        System.out.println("IN ADMIN");
                        filterChain.doFilter(req, resp);
                    } else {
                        req.getSession().invalidate();
                        resp.sendRedirect(req.getContextPath() + "/auth.jsp");
                    }
                }
                case TEACHER -> {
                    System.out.println("In case TEACHER");
                    if (ActionAccessConstants.getTeacherActions().contains(action)
                            || PageAccessConstants.getTeacherPages().contains(servletPath.substring(1))) {

                        filterChain.doFilter(req, resp);
                    } else {
                        req.getSession().invalidate();
                        resp.sendRedirect(req.getContextPath() + "/auth.jsp");
                    }
                }
                case STUDENT -> {
                    System.out.println("In case STUDENT");
                    if (ActionAccessConstants.getStudentActions().contains(action)
                            || PageAccessConstants.getStudentPages().contains(servletPath.substring(1))) {

                        filterChain.doFilter(req, resp);
                    } else {
                        req.getSession().invalidate();
                        resp.sendRedirect(req.getContextPath() + "/auth.jsp");
                    }
                }
            }
        }
    }
}