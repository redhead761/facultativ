package com.epam.facultative.actions;

import com.epam.facultative.dto.CourseDTO;
import com.epam.facultative.exception.ServiceException;
import com.epam.facultative.service.GeneralService;
import com.epam.facultative.service.ServiceFactory;
import jakarta.servlet.http.HttpServletRequest;

import java.util.List;

public class AdminCoursesAction implements Action {
    @Override
    public String execute(HttpServletRequest req) {
        GeneralService generalService = ServiceFactory.getInstance().getGeneralService();
        try {
            List<CourseDTO> courses = generalService.sortByAlphabet();
            req.setAttribute("courses",courses);
        } catch (ServiceException e) {
            throw new RuntimeException(e);
        }
        return "admin_courses.jsp";
    }
}
