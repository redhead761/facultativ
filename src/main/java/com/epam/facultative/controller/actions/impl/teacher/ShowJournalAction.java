package com.epam.facultative.controller.actions.impl.teacher;

import com.epam.facultative.controller.actions.Action;
import com.epam.facultative.controller.actions.ActionUtils;
import com.epam.facultative.controller.AppContext;
import com.epam.facultative.exception.ServiceException;
import com.epam.facultative.service.TeacherService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import static com.epam.facultative.controller.AttributeConstants.*;
import static com.epam.facultative.controller.actions.PageNameConstants.*;

public class ShowJournalAction implements Action {
    private final TeacherService teacherService;

    public ShowJournalAction(AppContext appContext) {
        teacherService = appContext.getTeacherService();
    }

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws ServiceException {
        int currentPage = ActionUtils.getCurrentPage(req);
        int recordsPerPage = ActionUtils.getRecordsPerPage(req);
        int courseId = Integer.parseInt(req.getParameter(COURSE_ID));
        req.setAttribute(COURSE_ID, courseId);
        req.setAttribute(STUDENTS, teacherService.getStudentsByCourse(courseId, (currentPage - 1) * recordsPerPage, recordsPerPage));
        int noOfRecords = teacherService.getNoOfRecordsStudents();
        ActionUtils.setUpPagination(req, noOfRecords, currentPage, recordsPerPage);
        return JOURNAL_PAGE;
    }
}
