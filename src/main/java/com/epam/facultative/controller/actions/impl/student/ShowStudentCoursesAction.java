package com.epam.facultative.controller.actions.impl.student;

import com.epam.facultative.controller.actions.Action;
import com.epam.facultative.controller.actions.ActionUtils;
import com.epam.facultative.controller.AppContext;
import com.epam.facultative.dto.CourseDTO;
import com.epam.facultative.dto.StudentDTO;
import com.epam.facultative.exception.ServiceException;
import com.epam.facultative.service.StudentService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.util.List;
import java.util.Map;

import static com.epam.facultative.controller.AttributeConstants.*;
import static com.epam.facultative.controller.actions.PageNameConstants.*;

public class ShowStudentCoursesAction implements Action {
    private final StudentService studentService;

    public ShowStudentCoursesAction(AppContext appContext) {
        studentService = appContext.getStudentService();
    }

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws ServiceException {
        int currentPage = ActionUtils.getCurrentPage(req);
        int recordsPerPage = ActionUtils.getRecordsPerPage(req);
        StudentDTO student = (StudentDTO) req.getSession().getAttribute(USER);
        String type = req.getParameter(TYPE);
        Map.Entry<Integer, List<CourseDTO>> coursesWithRows = null;
        String path = null;
        switch (type) {
            case "all" -> {
                coursesWithRows = studentService.getCoursesByStudent(student.getId(), (currentPage - 1) * recordsPerPage, recordsPerPage);
                path = STUDENT_COURSES_PAGE;
            }
            case "coming_soon" -> {
                coursesWithRows = studentService.getCoursesComingSoon(student.getId(), (currentPage - 1) * recordsPerPage, recordsPerPage);
                path = COMING_SOON_COURSES_PAGE;
            }
            case "completed" -> {
                coursesWithRows = studentService.getCoursesCompleted(student.getId(), (currentPage - 1) * recordsPerPage, recordsPerPage);
                path = COMPLETED_COURSES_PAGE;
            }
            case "in_progress" -> {
                coursesWithRows = studentService.getCoursesInProgress(student.getId(), (currentPage - 1) * recordsPerPage, recordsPerPage);
                path = IN_PROGRESS_COURSES_PAGE;
            }
        }
        req.setAttribute(COURSES, coursesWithRows.getValue());
        int noOfRecords = coursesWithRows.getKey();
        ActionUtils.setUpPagination(req, noOfRecords, currentPage, recordsPerPage);
        return path;
    }
}
