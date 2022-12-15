package com.epam.facultative.actions.impl.admin;

import com.epam.facultative.actions.Action;
import com.epam.facultative.exception.ServiceException;
import com.epam.facultative.service.*;
import jakarta.servlet.http.*;

import static com.epam.facultative.actions.Constants.*;

public class ManageTeachersAction implements Action {
    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws ServiceException {
        GeneralService generalService = ServiceFactory.getInstance().getGeneralService();
        req.getSession().setAttribute("teachers", generalService.getAllTeachers());
        return MANAGE_TEACHERS_PAGE;
    }
}
