//package com.epam.facultative.filters;
//
//import com.epam.facultative.dto.UserDTO;
//import com.epam.facultative.entities.Role;
//import jakarta.servlet.*;
//import jakarta.servlet.annotation.WebFilter;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//
//import java.io.IOException;
//
//import static com.epam.facultative.filters.FilterConstants.*;
//
//@WebFilter(filterName = "AuthFilter")
//public class AuthFilter implements Filter {
//
//    @Override
//    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
//        System.out.println("In AUTH FILTER");
//        HttpServletRequest request = (HttpServletRequest) servletRequest;
//        HttpServletResponse response = (HttpServletResponse) servletResponse;
//
//        UserDTO user = (UserDTO) request.getSession().getAttribute("user");
//        String action = request.getParameter("action");
//        System.out.println("User= " + user  );
//        System.out.println("Action =" + action);
//        System.out.println("URI =" + request.getRequestURI());
//        System.out.println("Query = " + request.getQueryString());
//        if (user == null && (action == null || action.equals("auth") || action.equals("register"))) {
//            filterChain.doFilter(request, response);
//        } else if (user != null && user.getRole().equals(Role.ADMIN) && ADMIN_ACTIONS.contains(action)) {
//            filterChain.doFilter(request, response);
//        } else if (user != null && user.getRole().equals(Role.STUDENT) && STUDENT_ACTIONS.contains(action)) {
//            filterChain.doFilter(request, response);
//        } else if (user != null && user.getRole().equals(Role.TEACHER) && TEACHER_ACTIONS.contains(action)) {
//            filterChain.doFilter(request, response);
//        } else {
//            request.getSession().invalidate();
//            response.sendRedirect("auth.jsp");
//        }
//    }
//}