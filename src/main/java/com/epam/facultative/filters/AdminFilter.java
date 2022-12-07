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

@WebFilter(urlPatterns="/admin/*", servletNames="controller")
public class AdminFilter implements Filter {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
System.out.println("in filter");
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        HttpSession session = request.getSession(false);
        String loginURI = request.getContextPath();

        UserDTO user = (UserDTO) request.getSession().getAttribute("user");
        String action = request.getParameter("action");
        System.out.println(action);
        if (user != null && user.getRole().equals(Role.ADMIN)){
            System.out.println("in IF");
            filterChain.doFilter(request,response);
        }
        else {
            request.getSession().invalidate();
            response.sendRedirect(loginURI);
        }
    }
}
