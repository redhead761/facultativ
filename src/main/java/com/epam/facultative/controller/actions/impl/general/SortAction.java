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

public class SortAction implements Action {
    private final GeneralService generalService;

    public SortAction(AppContext appContext) {
        generalService = appContext.getGeneralService();
    }

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws ServiceException {
        ActionUtils.removeRedundantAttribute(req);
        String sortType = req.getParameter("sort_type");
        req.setAttribute("sort_type", sortType);
        int currentPage = ActionUtils.getCurrentPage(req);
        int recordsPerPage = 5;
        List<CourseDTO> courses = null;
        switch (sortType) {
            case "alphabet" ->
                    courses = generalService.sortCoursesByAlphabet((currentPage - 1) * recordsPerPage, recordsPerPage);
            case "reverse alphabet" ->
                    courses = generalService.sortCoursesByAlphabetReverse((currentPage - 1) * recordsPerPage, recordsPerPage);
            case "duration" ->
                    courses = generalService.sortCoursesByDuration((currentPage - 1) * recordsPerPage, recordsPerPage);
            case "amount students" ->
                    courses = generalService.sortCoursesByAmountOfStudents((currentPage - 1) * recordsPerPage, recordsPerPage);
        }
        ActionUtils.setUpPagination(req, generalService, currentPage, recordsPerPage, courses);
        return ActionUtils.chooseCabinet(req);
    }

}
