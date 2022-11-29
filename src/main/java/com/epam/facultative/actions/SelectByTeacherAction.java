package com.epam.facultative.actions;

import com.epam.facultative.dto.CourseDTO;
import com.epam.facultative.exception.ServiceException;
import com.epam.facultative.service.GeneralService;
import com.epam.facultative.service.ServiceFactory;
import jakarta.servlet.http.HttpServletRequest;

import java.util.List;

import static com.epam.facultative.actions.Constants.*;

public class SelectByTeacherAction implements Action {
    @Override
    public String execute(HttpServletRequest req) {
        GeneralService generalService = ServiceFactory.getInstance().getGeneralService();
        try {
            String teacherId = req.getParameter("teacher_id");
            List<CourseDTO> courses = generalService.getCoursesByTeacher(Integer.parseInt(teacherId));
            req.setAttribute("courses", courses);
        } catch (ServiceException e) {
            throw new RuntimeException(e);
        }
        return ADMIN_PAGE;
    }
}
