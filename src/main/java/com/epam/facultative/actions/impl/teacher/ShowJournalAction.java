package com.epam.facultative.actions.impl.teacher;

import com.epam.facultative.actions.Action;
import com.epam.facultative.actions.ActionUtils;
import com.epam.facultative.dto.StudentDTO;
import com.epam.facultative.exception.ServiceException;
import com.epam.facultative.service.ServiceFactory;
import com.epam.facultative.service.TeacherService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.util.List;

import static com.epam.facultative.actions.PageNameConstants.*;

public class ShowJournalAction implements Action {
    private final TeacherService teacherService;

    public ShowJournalAction() {
        teacherService = ServiceFactory.getInstance().getTeacherService();
    }

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws ServiceException {
        int currentPage = ActionUtils.getCurrentPage(req);
        int recordsPerPage = 5;
        int courseId = Integer.parseInt(req.getParameter("course_id"));
        req.setAttribute("course_id", courseId);
        List<StudentDTO> students = teacherService.getStudentsByCourse(courseId, (currentPage - 1) * recordsPerPage, recordsPerPage);
        req.setAttribute("students", students);
        return JOURNAL_PAGE;
    }
}
