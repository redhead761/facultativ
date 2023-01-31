package com.epam.facultative.controller.actions.impl.student;

import com.epam.facultative.controller.actions.Action;
import com.epam.facultative.controller.app_context.AppContext;
import com.epam.facultative.model.dto.CourseDTO;
import com.epam.facultative.model.dto.StudentDTO;
import com.epam.facultative.model.exception.ServiceException;
import com.epam.facultative.model.service.StudentService;
import com.epam.facultative.model.utils.param_builder.ParamBuilderForQuery;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.util.List;
import java.util.Map;

import static com.epam.facultative.controller.constants.AttributeConstants.*;
import static com.epam.facultative.controller.actions.ActionUtils.setUpPaginate;
import static com.epam.facultative.controller.constants.PageNameConstants.*;
import static com.epam.facultative.model.entities.Status.*;
import static com.epam.facultative.model.utils.param_builder.ParamBuilderForQueryUtil.courseParamBuilderForQuery;

/**
 * Accessible by student. Allows to get student`s courses list
 *
 * @author Oleksandr Panchenko
 * @version 1.0
 */
public class ShowStudentCoursesAction implements Action {
    private final StudentService studentService;

    public ShowStudentCoursesAction(AppContext appContext) {
        studentService = appContext.getStudentService();
    }

    /**
     * Called from doGet method in front-controller.
     *
     * @param req to get parameter
     * @return path page depending on the type
     */
    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws ServiceException {
        StudentDTO student = (StudentDTO) req.getSession().getAttribute(USER);
        String studentId = String.valueOf(student.getId());
        String type = req.getParameter(TYPE);
        Map.Entry<Integer, List<CourseDTO>> coursesWithRows;
        String path = null;
        ParamBuilderForQuery paramBuilder = courseParamBuilderForQuery()
                .setIdFilterForStudent(studentId)
                .setLimits(req.getParameter(CURRENT_PAGE), req.getParameter(RECORDS_PER_PAGE));
        switch (type) {
            case "all" -> path = STUDENT_COURSES_PAGE;
            case "coming_soon" -> {
                paramBuilder.setStatusFilterForCourse(String.valueOf(COMING_SOON.getId()));
                path = COMING_SOON_COURSES_PAGE;
            }
            case "completed" -> {
                paramBuilder.setStatusFilterForCourse(String.valueOf(COMPLETED.getId()));
                path = COMPLETED_COURSES_PAGE;
            }
            case "in_progress" -> {
                paramBuilder.setStatusFilterForCourse(String.valueOf(IN_PROCESS.getId()));
                path = IN_PROGRESS_COURSES_PAGE;
            }
        }
        coursesWithRows = studentService.getCoursesByJournal(paramBuilder.getParam());
        req.setAttribute(COURSES, coursesWithRows.getValue());
        setUpPaginate(req, coursesWithRows.getKey());
        return path;
    }
}
