package com.epam.facultative.actions;

import com.epam.facultative.exception.ServiceException;
import com.epam.facultative.exception.ValidateException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public interface Action {
    String execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException, ServiceException, ValidateException;
}
