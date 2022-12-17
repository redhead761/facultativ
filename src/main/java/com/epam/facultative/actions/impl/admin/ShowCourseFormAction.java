package com.epam.facultative.actions.impl.admin;

import com.epam.facultative.actions.Action;
import com.epam.facultative.exception.ServiceException;
import com.epam.facultative.service.*;
import jakarta.servlet.http.*;

import static com.epam.facultative.actions.Constants.*;

public class ShowCourseFormAction implements Action {
    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws ServiceException {
        req.getSession().removeAttribute("sort_type");
        req.getSession().removeAttribute("select_type");
        String courseId = req.getParameter("course_id");
        GeneralService generalService = ServiceFactory.getInstance().getGeneralService();
        req.getSession().setAttribute("categories", generalService.getAllCategories());
        req.getSession().setAttribute("course_id", courseId);
        return COURSE_FORM_PAGE;
    }
}
