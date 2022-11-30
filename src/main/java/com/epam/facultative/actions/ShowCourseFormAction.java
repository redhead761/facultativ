package com.epam.facultative.actions;

import com.epam.facultative.exception.ServiceException;
import com.epam.facultative.service.GeneralService;
import com.epam.facultative.service.ServiceFactory;
import jakarta.servlet.http.HttpServletRequest;

import static com.epam.facultative.actions.Constants.*;

public class ShowCourseFormAction implements Action {
    @Override
    public String execute(HttpServletRequest req) {
        String path;
        String courseId = req.getParameter("course_id");
        GeneralService generalService = ServiceFactory.getInstance().getGeneralService();
        try {
            req.setAttribute("categories", generalService.getAllCategories());
            req.setAttribute("course_id", courseId);
            path = COURSE_FORM_PAGE;
        } catch (ServiceException e) {
            path = ERROR_PAGE;
            req.setAttribute("message", e.getMessage());
        }
        return path;
    }
}
