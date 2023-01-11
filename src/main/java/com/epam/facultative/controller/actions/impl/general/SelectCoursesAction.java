package com.epam.facultative.controller.actions.impl.general;

import com.epam.facultative.controller.actions.Action;
import com.epam.facultative.controller.actions.ActionUtils;
import com.epam.facultative.controller.AppContext;
import com.epam.facultative.dto.CourseDTO;
import com.epam.facultative.exception.ServiceException;
import com.epam.facultative.service.GeneralService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.util.List;

public class SelectCoursesAction implements Action {
    private final GeneralService generalService;

    public SelectCoursesAction(AppContext appContext) {
        generalService = appContext.getGeneralService();
    }

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws ServiceException {
        ActionUtils.removeRedundantAttribute(req);
        String selectType = req.getParameter("select_type");
        req.setAttribute("select_type", selectType);
        int currentPage = ActionUtils.getCurrentPage(req);
        int recordsPerPage = ActionUtils.getRecordsPerPage(req);
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
        req.setAttribute("courses", courses);
        int noOfRecords = generalService.getNoOfRecordsCourses();
        ActionUtils.setUpPagination(req, noOfRecords, currentPage, recordsPerPage);
        req.setAttribute("teachers", generalService.getAllTeachers());
        req.setAttribute("categories", generalService.getAllCategories());
        return ActionUtils.chooseCabinet(req);
    }

}
