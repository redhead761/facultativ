package com.epam.facultative.controller.actions.impl.teacher;

import com.epam.facultative.controller.actions.Action;
import com.epam.facultative.controller.app_context.AppContext;
import com.epam.facultative.model.dto.StudentDTO;
import com.epam.facultative.model.exception.ServiceException;
import com.epam.facultative.model.service.TeacherService;
import com.epam.facultative.model.utils.param_builder.ParamBuilderForQuery;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.util.List;
import java.util.Map;

import static com.epam.facultative.controller.constants.AttributeConstants.*;
import static com.epam.facultative.controller.actions.ActionUtils.setUpPaginate;
import static com.epam.facultative.controller.actions.ActionUtils.transferAttributeFromSessionToRequest;
import static com.epam.facultative.controller.constants.PageNameConstants.*;
import static com.epam.facultative.model.utils.param_builder.ParamBuilderForQueryUtil.courseParamBuilderForQuery;

/**
 * Accessible by teacher. Allows to show e-journal with students and grades.
 *
 * @author Oleksandr Panchenko
 * @version 1.0
 */
public class ShowJournalAction implements Action {
    private final TeacherService teacherService;

    public ShowJournalAction(AppContext appContext) {
        teacherService = appContext.getTeacherService();
    }

    /**
     * Called from doGet method in front-controller. Obtains required path and transfer attributes from session
     * to request
     *
     * @param req to get parameters and set students
     * @return journal page after trying to show journal
     */
    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws ServiceException {
        transferAttributeFromSessionToRequest(req, ERROR, MESSAGE);
        String courseId = req.getParameter(COURSE_ID);
        ParamBuilderForQuery paramBuilder = courseParamBuilderForQuery()
                .setIdFilterCourseForStudent(courseId)
                .setLimits(req.getParameter(CURRENT_PAGE), req.getParameter(RECORDS_PER_PAGE));
        Map.Entry<Integer, List<StudentDTO>> studentsWithRows = teacherService.getStudentsByCourse(paramBuilder.getParam());
        req.setAttribute(STUDENTS, studentsWithRows.getValue());
        setUpPaginate(req, studentsWithRows.getKey());
        return JOURNAL_PAGE;
    }
}
