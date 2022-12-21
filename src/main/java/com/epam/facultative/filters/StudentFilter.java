package com.epam.facultative.filters;

import com.epam.facultative.dto.UserDTO;
import com.epam.facultative.entities.Role;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;


@WebFilter(urlPatterns="/student/*", servletNames="controller")
public class StudentFilter implements Filter {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        String loginURI = request.getContextPath();

        UserDTO user = (UserDTO) request.getSession().getAttribute("user");
        if (user != null && user.getRole().equals(Role.STUDENT)){
            filterChain.doFilter(request,response);
        }
        else {
            request.getSession().invalidate();
            response.sendRedirect(loginURI);
        }
    }
}
