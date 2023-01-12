package com.epam.facultative.controller.actions.impl.teacher;

import com.epam.facultative.controller.actions.Action;
import com.epam.facultative.controller.actions.ActionUtils;
import com.epam.facultative.controller.AppContext;
import com.epam.facultative.dto.StudentDTO;
import com.epam.facultative.exception.ServiceException;
import com.epam.facultative.service.TeacherService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.util.List;

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
        int courseId = Integer.parseInt(req.getParameter("course_id"));
        req.setAttribute("course_id", courseId);
        req.setAttribute("students", teacherService.getStudentsByCourse(courseId, (currentPage - 1) * recordsPerPage, recordsPerPage));
        int noOfRecords = teacherService.getNoOfRecordsStudents();
        ActionUtils.setUpPagination(req, noOfRecords, currentPage, recordsPerPage);
        return JOURNAL_PAGE;
    }
}
