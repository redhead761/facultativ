package com.epam.facultative.actions;

import com.epam.facultative.dto.UserDTO;
import com.epam.facultative.exception.ServiceException;
import com.epam.facultative.service.ServiceFactory;
import com.epam.facultative.service.TeacherService;
import jakarta.servlet.http.HttpServletRequest;

import java.util.List;

public class ShowGradeListAction implements Action {
    @Override
    public String execute(HttpServletRequest req) {
        int courseId = Integer.parseInt(req.getParameter("course_id"));
        req.setAttribute("course_id", courseId);
        TeacherService teacherService = ServiceFactory.getInstance().getTeacherService();
        try {
            List<UserDTO> students = teacherService.getStudentsByCourse(courseId);
            req.setAttribute("students", students);
        } catch (ServiceException e) {
            throw new RuntimeException(e);
        }
        return "grade_list.jsp";
    }
}
