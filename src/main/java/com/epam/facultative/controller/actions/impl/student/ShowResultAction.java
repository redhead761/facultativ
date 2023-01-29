package com.epam.facultative.controller.actions.impl.student;

import com.epam.facultative.controller.app_context.AppContext;
import com.epam.facultative.controller.actions.Action;
import com.epam.facultative.model.dto.StudentDTO;
import com.epam.facultative.model.exception.ServiceException;
import com.epam.facultative.model.service.StudentService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import static com.epam.facultative.controller.constants.AttributeConstants.*;
import static com.epam.facultative.controller.actions.ActionUtils.transferAttributeFromSessionToRequest;
import static com.epam.facultative.controller.constants.PageNameConstants.*;

public class ShowResultAction implements Action {
    private final StudentService studentService;

    public ShowResultAction(AppContext appContext) {
        studentService = appContext.getStudentService();
    }

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws ServiceException {
        transferAttributeFromSessionToRequest(req, MESSAGE, ERROR);
        int courseId = Integer.parseInt(req.getParameter(COURSE_ID));
        StudentDTO student = (StudentDTO) req.getSession().getAttribute(USER);
        int studentId = student.getId();
        int grade = studentService.getGrade(courseId, studentId);
        req.setAttribute(GRADE, grade);
        return RESULT_PAGE;
    }
}
