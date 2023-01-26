package com.epam.facultative.controller.actions.impl.student;

import com.epam.facultative.controller.actions.Action;
import com.epam.facultative.controller.app_context.AppContext;
import com.epam.facultative.model.dto.StudentDTO;
import com.epam.facultative.model.exception.ServiceException;
import com.epam.facultative.model.service.StudentService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import static com.epam.facultative.controller.constants.AttributeConstants.*;
import static com.epam.facultative.controller.constants.ActionNameConstants.SHOW_ALL_COURSES_ACTION;
import static com.epam.facultative.controller.actions.ActionUtils.getGetAction;

public class EnrollAction implements Action {
    private final StudentService studentService;

    public EnrollAction(AppContext appContext) {
        studentService = appContext.getStudentService();
    }

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws ServiceException {
        int courseId = Integer.parseInt(req.getParameter(COURSE_ID));
        String currentPage = req.getParameter(CURRENT_PAGE);
        String recordsPerPage = req.getParameter(RECORDS_PER_PAGE);
        String sort = req.getParameter(SORT);
        String selectByCategory = req.getParameter(SELECT_BY_CATEGORY);
        String selectByTeacher = req.getParameter(SELECT_BY_TEACHER);
        StudentDTO student = (StudentDTO) req.getSession().getAttribute(USER);
        int id = student.getId();
        try {
            studentService.enroll(courseId, id);
            req.getSession().setAttribute(MESSAGE, SUCCESSFUL);
        } catch (ServiceException e) {
            req.getSession().setAttribute(ERROR, ON_COURSE);
        }
        return getGetAction(SHOW_ALL_COURSES_ACTION, CURRENT_PAGE, currentPage, RECORDS_PER_PAGE, recordsPerPage, SORT, sort, SELECT_BY_CATEGORY, selectByCategory, SELECT_BY_TEACHER, selectByTeacher);
    }
}
