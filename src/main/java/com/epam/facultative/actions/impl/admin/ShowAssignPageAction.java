package com.epam.facultative.actions.impl.admin;

import com.epam.facultative.actions.Action;
import com.epam.facultative.dto.CourseDTO;
import com.epam.facultative.dto.UserDTO;
import com.epam.facultative.exception.ServiceException;
import com.epam.facultative.service.*;
import jakarta.servlet.http.*;

import java.util.List;

import static com.epam.facultative.actions.Constants.*;

public class ShowAssignPageAction implements Action {
    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws ServiceException {
        req.getSession().removeAttribute("sort_type");
        req.getSession().removeAttribute("select_type");
        int courseId = Integer.parseInt(req.getParameter("course_id"));
        AdminService adminService = ServiceFactory.getInstance().getAdminService();
        GeneralService generalService = ServiceFactory.getInstance().getGeneralService();
        CourseDTO course = adminService.getCourse(courseId);
        List<UserDTO> teachers = generalService.getAllTeachers();
        req.getSession().setAttribute("course", course);
        req.getSession().setAttribute("teachers", teachers);
        return ASSIGN_PAGE;
    }
}
