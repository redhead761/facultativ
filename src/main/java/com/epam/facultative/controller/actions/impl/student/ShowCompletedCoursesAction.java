package com.epam.facultative.controller.actions.impl.student;

import com.epam.facultative.controller.actions.Action;
import com.epam.facultative.controller.actions.ActionUtils;
import com.epam.facultative.controller.AppContext;
import com.epam.facultative.dto.UserDTO;
import com.epam.facultative.exception.ServiceException;
import com.epam.facultative.service.StudentService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import static com.epam.facultative.controller.actions.PageNameConstants.*;

public class ShowCompletedCoursesAction implements Action {
    private final StudentService studentService;

    public ShowCompletedCoursesAction(AppContext appContext) {
        studentService = appContext.getStudentService();
    }

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws ServiceException {
        int currentPage = ActionUtils.getCurrentPage(req);
        int recordsPerPage = ActionUtils.getRecordsPerPage(req);
        UserDTO user = (UserDTO) req.getSession().getAttribute("user");
        req.setAttribute("courses", studentService.getCoursesCompleted(user.getId(), (currentPage - 1) * recordsPerPage, recordsPerPage));
        req.setAttribute("courses", studentService.getCoursesByStudent(user.getId(), (currentPage - 1) * recordsPerPage, recordsPerPage));
        int noOfRecords = studentService.getNoOfRecordsCourses();
        ActionUtils.setUpPaginationStudent(req, noOfRecords, currentPage, recordsPerPage);
        return COMPLETED_COURSES_PAGE;
    }
}
