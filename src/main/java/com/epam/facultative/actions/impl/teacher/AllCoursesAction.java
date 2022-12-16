package com.epam.facultative.actions.impl.teacher;

import com.epam.facultative.actions.Action;
import com.epam.facultative.exception.ServiceException;
import com.epam.facultative.service.*;
import jakarta.servlet.http.*;

import static com.epam.facultative.actions.Constants.*;

public class AllCoursesAction implements Action {
    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws ServiceException {
        GeneralService generalService = ServiceFactory.getInstance().getGeneralService();
        req.getSession().setAttribute("courses", generalService.getAllCourses(0,3));
        req.getSession().setAttribute("categories", generalService.getAllCategories());
        req.getSession().setAttribute("teachers", generalService.getAllTeachers());
        return TEACHER_PAGE;
    }
}
