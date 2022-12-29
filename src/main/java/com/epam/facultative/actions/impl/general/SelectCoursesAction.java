package com.epam.facultative.actions.impl.general;

import com.epam.facultative.actions.Action;
import com.epam.facultative.actions.ActionUtils;
import com.epam.facultative.dto.CourseDTO;
import com.epam.facultative.exception.ServiceException;
import com.epam.facultative.service.GeneralService;
import com.epam.facultative.service.ServiceFactory;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.util.List;

public class SelectCoursesAction implements Action {
    private final GeneralService generalService;

    public SelectCoursesAction() {
        generalService = ServiceFactory.getInstance().getGeneralService();
    }

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws ServiceException {
        ActionUtils.removeRedundantAttribute(req);
        String selectType = req.getParameter("select_type");
        req.setAttribute("select_type", selectType);
        int currentPage = ActionUtils.getCurrentPage(req);
        int recordsPerPage = 5;
        List<CourseDTO> courses = null;
        switch (selectType) {
            case "by_teacher" -> {
                int teacherId = Integer.parseInt(req.getParameter("teacher_id"));
                req.setAttribute("teacher_id", teacherId);
                courses = generalService.getCoursesByTeacher(teacherId, (currentPage - 1) * recordsPerPage, recordsPerPage);
            }
            case "by_category" -> {
                int categoryId = Integer.parseInt(req.getParameter("category_id"));
                req.setAttribute("category_id", categoryId);
                courses = generalService.getCoursesByCategory(categoryId, (currentPage - 1) * recordsPerPage, recordsPerPage);
            }

        }
        ActionUtils.setUpPagination(req, generalService, currentPage, recordsPerPage, courses);
        return ActionUtils.chooseCabinet(req);
    }

}
