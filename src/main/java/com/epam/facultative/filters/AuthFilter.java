package com.epam.facultative.filters;

import com.epam.facultative.dto.UserDTO;
import com.epam.facultative.entity.Role;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

import static com.epam.facultative.filters.Constants.*;

@WebFilter(urlPatterns = "/*", servletNames = "/controller")
public class AuthFilter implements Filter {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        System.out.println("In auth filter");
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        UserDTO user = (UserDTO) request.getSession().getAttribute("user");
        String action = (String) request.getSession().getAttribute("action");
        System.out.println("Action = " + action);
        if (user == null && (action == null || action.equals("auth") || action.equals("register"))) {
            System.out.println("In first");
            filterChain.doFilter(request, response);
        } else if (user != null && user.getRole().equals(Role.ADMIN) && ADMIN_ACTIONS.contains(action)) {
            System.out.println("In second");
            filterChain.doFilter(request, response);
        } else if (user != null && user.getRole().equals(Role.STUDENT) && STUDENT_ACTIONS.contains(action)) {
            System.out.println("In third");
            filterChain.doFilter(request, response);
        } else if (user != null && user.getRole().equals(Role.TEACHER) && TEACHER_ACTIONS.contains(action)) {
            System.out.println("In fours");
            filterChain.doFilter(request, response);
        } else {
            System.out.println("In else");
            request.getSession().invalidate();
            response.sendRedirect("auth.jsp");
        }
    }
}
