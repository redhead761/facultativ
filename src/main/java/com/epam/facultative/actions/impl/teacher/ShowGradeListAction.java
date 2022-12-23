package com.epam.facultative.actions.impl.teacher;

import com.epam.facultative.actions.Action;
import com.epam.facultative.dto.UserDTO;
import com.epam.facultative.exception.ServiceException;
import com.epam.facultative.service.ServiceFactory;
import com.epam.facultative.service.TeacherService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.util.List;

import static com.epam.facultative.actions.Constants.*;

public class ShowGradeListAction implements Action {
    private final TeacherService teacherService;

    public ShowGradeListAction() {
        teacherService = ServiceFactory.getInstance().getTeacherService();
    }

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws ServiceException {
        int courseId = 0;
        if (req.getParameter("course_id") != null) {
            courseId = Integer.parseInt(req.getParameter("course_id"));
            req.getSession().setAttribute("course_id", courseId);
        }
//        List<UserDTO> students = teacherService.getStudentsByCourse(courseId, 0, 5);
//        req.getSession().setAttribute("students", students);
        return GRADE_PAGE;
    }
}
