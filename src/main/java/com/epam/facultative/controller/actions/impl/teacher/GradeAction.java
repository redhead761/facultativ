package com.epam.facultative.controller.actions.impl.teacher;

import com.epam.facultative.controller.actions.Action;
import com.epam.facultative.controller.app_context.AppContext;
import com.epam.facultative.model.exception.ServiceException;
import com.epam.facultative.model.service.TeacherService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import static com.epam.facultative.controller.constants.AttributeConstants.*;
import static com.epam.facultative.controller.constants.ActionNameConstants.*;
import static com.epam.facultative.controller.actions.ActionUtils.getGetAction;

/**
 * Accessible by teacher. Allows to grade student on course. Implements PRG pattern.
 *
 * @author Oleksandr Panchenko
 * @version 1.0
 */
public class GradeAction implements Action {
    private final TeacherService teacherService;

    public GradeAction(AppContext appContext) {
        teacherService = appContext.getTeacherService();
    }

    /**
     * Called from doPost method in front-controller. Tries to grade.R
     *
     * @param req to get users id and set message in case of successful grade
     * @return path to redirect to executeGet method through front-controller
     */
    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws ServiceException {
        int studentId = Integer.parseInt(req.getParameter(STUDENT_ID));
        int grade = Integer.parseInt(req.getParameter(GRADE));
        String courseId = req.getParameter(COURSE_ID);
        String currentPage = req.getParameter(CURRENT_PAGE);
        String recordsPerPage = req.getParameter(RECORDS_PER_PAGE);
        teacherService.grading(Integer.parseInt(courseId), studentId, grade);
        req.getSession().setAttribute(MESSAGE, SUCCESSFUL);
        return getGetAction(SHOW_JOURNAL_ACTION, CURRENT_PAGE, currentPage, RECORDS_PER_PAGE, recordsPerPage, COURSE_ID, courseId);
    }
}
