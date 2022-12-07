package com.epam.facultative.filters;

import com.epam.facultative.dto.UserDTO;
import com.epam.facultative.entity.Role;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

import static com.epam.facultative.filters.Constants.*;

@WebFilter(urlPatterns = "/*", servletNames = "/controller")
public class AuthFilter implements Filter {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        System.out.println("Int auth filter");
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        HttpSession session = request.getSession(false);
        String loginURI = request.getContextPath();

        UserDTO user = (UserDTO) request.getSession().getAttribute("user");
        String action = request.getParameter("action");
        System.out.println("action = " + action);
        if (user == null && (action == null || action.equals("auth"))) {
            System.out.println("in auth");
            System.out.println("-----------------------");
            filterChain.doFilter(request, response);
        } else if (user != null && user.getRole().equals(Role.ADMIN) && ADMIN_ACTIONS.contains(action)) {
            System.out.println("in adm");
            System.out.println("-----------------------");
            filterChain.doFilter(request, response);
        } else if (user != null && user.getRole().equals(Role.STUDENT) && STUDENT_ACTIONS.contains(action)) {
            System.out.println("in stud");
            System.out.println("-----------------------");
            filterChain.doFilter(request, response);
        } else if (user != null && user.getRole().equals(Role.TEACHER) && TEACHER_ACTIONS.contains(action)) {
            System.out.println("in teach");
            System.out.println("-----------------------");
            filterChain.doFilter(request, response);
        } else {
            request.getSession().invalidate();
            System.out.println("in invalidate");
            System.out.println("-----------------------");
            response.sendRedirect("auth.jsp");
            //request.getRequestDispatcher("../auth.jsp").forward(request, response);
        }
    }
}
