package com.epam.facultative.controller.actions.impl.general;

import com.epam.facultative.controller.AppContext;
import com.epam.facultative.controller.actions.Action;
import com.epam.facultative.controller.actions.ActionUtils;
import com.epam.facultative.exception.ServiceException;
import com.epam.facultative.service.GeneralService;
import com.epam.facultative.utils.query_builders.QueryBuilder;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

import static com.epam.facultative.controller.actions.PageNameConstants.COURSES_PAGE;
import static com.epam.facultative.utils.query_builders.QueryBuilderUtil.courseQueryBuilder;

public class CoursesAction implements Action {
    private final GeneralService generalService;

    public CoursesAction(AppContext appContext) {
        generalService = appContext.getGeneralService();
    }

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException, ServiceException {
//        ActionUtils.setAllCourses(req, generalService);

        int currentPage = ActionUtils.getCurrentPage(req);
        int recordsPerPage = ActionUtils.getRecordsPerPage(req);

        QueryBuilder queryBuilder = getQueryBuilder(req);
        req.setAttribute("teachers", generalService.getAllTeachers().getValue());
        req.setAttribute("categories", generalService.getAllCategories().getValue());


        var test = generalService.getTest(queryBuilder.getQuery());

        req.setAttribute("courses", test.getValue());
        req.setAttribute("noOfPages", test.getKey());

        ActionUtils.setUpPagination(req, test.getKey(), currentPage, recordsPerPage);

        req.setAttribute("sort", req.getParameter("sort"));
        req.setAttribute("select_by_category", req.getParameter("select_by_category"));
        req.setAttribute("order", req.getParameter("order"));
        req.setAttribute("select_by_teacher", req.getParameter("select_by_teacher"));

        System.out.println(queryBuilder.getQuery());

        return COURSES_PAGE;
    }

    private QueryBuilder getQueryBuilder(HttpServletRequest req) {
        return courseQueryBuilder()
                .setSortFieldForCourse(req.getParameter("sort"))
                .setOrder(req.getParameter("order"))
                .setCategoryFilterForCourse(req.getParameter("select_by_category"))
                .setTeacherFilterForCourse(req.getParameter("select_by_teacher"))
                .setLimits((req.getParameter("page")), req.getParameter("records_per_page"));
    }
}

