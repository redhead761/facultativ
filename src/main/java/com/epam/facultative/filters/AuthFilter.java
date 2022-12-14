package com.epam.facultative.filters;

import com.epam.facultative.dto.UserDTO;
import com.epam.facultative.entity.Role;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Locale;

import static com.epam.facultative.filters.Constants.*;

@WebFilter(urlPatterns = "/*", servletNames = "/controller")
public class AuthFilter implements Filter {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        System.out.println("In auth filter");
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;


        System.out.println(request.getContextPath() + request.getServletPath() + request.getPathInfo());
        UserDTO user = (UserDTO) request.getSession().getAttribute("user");
        String action = (String) request.getAttribute("action");
//        Locale language = (Locale) request.getSession().getAttribute("language");
        String locale = (String) request.getAttribute("locale");
//        System.out.println("Language = " + language);
        System.out.println("Action = " + action);
//        if (language != null) {
//            System.out.println("In lang");
//            response.setIntHeader("Refresh", 0);
//            request.getSession().setAttribute("locale", locale);
//            request.getRequestDispatcher(request.getContextPath() + request.getServletPath()).forward(request, response);
//        } else
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
