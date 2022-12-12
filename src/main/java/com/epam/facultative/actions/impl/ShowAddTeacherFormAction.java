package com.epam.facultative.actions.impl;

import com.epam.facultative.actions.Action;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

import static com.epam.facultative.actions.impl.Constants.*;

public class ShowAddTeacherFormAction implements Action {
    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        return ADD_TEACHER_PAGE;
    }
}
