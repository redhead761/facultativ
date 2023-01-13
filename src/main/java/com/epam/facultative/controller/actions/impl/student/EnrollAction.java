package com.epam.facultative.controller.actions.impl.student;

import com.epam.facultative.controller.actions.Action;
import com.epam.facultative.controller.actions.ActionUtils;
import com.epam.facultative.controller.AppContext;
import com.epam.facultative.dto.StudentDTO;
import com.epam.facultative.exception.ServiceException;
import com.epam.facultative.service.GeneralService;
import com.epam.facultative.service.StudentService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import static com.epam.facultative.controller.AttributeConstants.*;
import static com.epam.facultative.controller.actions.PageNameConstants.*;

public class EnrollAction implements Action {
    private final StudentService studentService;
    private final GeneralService generalService;

    public EnrollAction(AppContext appContext) {
        studentService = appContext.getStudentService();
        generalService = appContext.getGeneralService();
    }

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws ServiceException {
        int courseId = Integer.parseInt(req.getParameter(COURSE_ID));
        StudentDTO student = (StudentDTO) req.getSession().getAttribute(USER);
        int id = student.getId();
        ActionUtils.setUpPaginationForAllCourses(req, generalService);
        try {
            studentService.enroll(courseId, id);
            req.getSession().setAttribute(MESSAGE, SUCCESSFUL);
        } catch (ServiceException e) {
            req.getSession().setAttribute(MESSAGE, ON_COURSE);
        }
        return STUDENT_PAGE;
    }
}
