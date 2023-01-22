package com.epam.facultative.controller.actions;

import com.epam.facultative.dto.CourseDTO;
import com.epam.facultative.dto.TeacherDTO;
import com.epam.facultative.exception.ServiceException;
import com.epam.facultative.service.AdminService;
import com.epam.facultative.service.GeneralService;
import com.epam.facultative.utils.query_builders.ParamBuilderForQuery;
import jakarta.servlet.http.HttpServletRequest;

import java.util.List;
import java.util.Map;
import java.util.StringJoiner;

import static com.epam.facultative.controller.actions.ActionNameConstants.CONTROLLER;
import static com.epam.facultative.utils.query_builders.ParamBuilderForQueryUtil.*;

public class ActionUtils {
    private ActionUtils() {
    }

    public static int getCurrentPage(HttpServletRequest req) {
        if (req.getParameter("page") != null && !req.getParameter("page").isBlank()) {
            return Integer.parseInt(req.getParameter("page"));
        } else {
            return 1;
        }
    }

    public static int getRecordsPerPage(HttpServletRequest req) {
        if (req.getParameter("records_per_page") != null && !req.getParameter("records_per_page").isBlank()) {
            return Integer.parseInt(req.getParameter("records_per_page"));
        } else {
            return 5;
        }
    }

    public static void setUpPagination(HttpServletRequest req, int noOfRecords, int currentPage, int recordsPerPage) {
        int noOfPages = (int) Math.ceil(noOfRecords * 1.0 / recordsPerPage);
        req.setAttribute("noOfPages", noOfPages);
        req.setAttribute("currentPage", currentPage);
        req.setAttribute("records_per_page", recordsPerPage);
    }

    public static void setUpPaginationForAllCourses(HttpServletRequest req, GeneralService generalService) throws ServiceException {
        int currentPage = getCurrentPage(req);
        int recordsPerPage = getRecordsPerPage(req);
        ParamBuilderForQuery paramBuilderForQuery = courseParamBuilderForQuery().setLimits(req.getParameter("page"), req.getParameter("records_per_page"));
        Map.Entry<Integer, List<CourseDTO>> coursesWithRows = generalService.getAllCourses(paramBuilderForQuery.getParam());
        req.setAttribute("courses", coursesWithRows.getValue());
        int noOfRecords = coursesWithRows.getKey();
        req.setAttribute("teachers", generalService.getAllTeachers().getValue());
        req.setAttribute("categories", generalService.getAllCategories().getValue());
        setUpPagination(req, noOfRecords, currentPage, recordsPerPage);
    }

    public static void setUpPaginationForAllTeachers(HttpServletRequest req, AdminService adminService) throws ServiceException {
        int currentPage = getCurrentPage(req);
        int recordsPerPage = getRecordsPerPage(req);
        ParamBuilderForQuery paramBuilderForQuery = teacherParamBuilderForQuery().setLimits(req.getParameter("page"), req.getParameter("records_per_page"));
        Map.Entry<Integer, List<TeacherDTO>> teachersWithRows = adminService.getAllTeachersPagination(paramBuilderForQuery.getParam());
        req.setAttribute("teachers", teachersWithRows.getValue());
        int noOfRecords = teachersWithRows.getKey();
        setUpPagination(req, noOfRecords, currentPage, recordsPerPage);
    }

    public static void setAllCourses(HttpServletRequest req, GeneralService generalService) throws ServiceException {
        String currentPage = req.getParameter("current_page");
        String recordsPerPage = req.getParameter("records_per_page");

        String sort = req.getParameter("sort");
        String order = req.getParameter("order");
        String selectByCategory = req.getParameter("select_by_category");
        String selectByTeacher = req.getParameter("select_by_teacher");

        ParamBuilderForQuery paramBuilderForQuery = courseParamBuilderForQuery().setSortFieldForCourse(sort)
                .setOrder(order)
                .setCategoryFilterForCourse(selectByCategory)
                .setTeacherFilterForCourse(selectByTeacher)
                .setLimits(currentPage, recordsPerPage);

        Map.Entry<Integer, List<CourseDTO>> coursesWithRows = generalService.getAllCourses(paramBuilderForQuery.getParam());

        testSetUp(req, coursesWithRows.getKey());

        req.setAttribute("courses", coursesWithRows.getValue());
        req.setAttribute("teachers", generalService.getAllTeachers().getValue());
        req.setAttribute("categories", generalService.getAllCategories().getValue());
    }

    public static void testSetUp(HttpServletRequest req, int noOfRecords) {
        int currentPage = getCorrectIntValue(req.getParameter("current_page"), 1);
        int recordsPerPage = getCorrectIntValue(req.getParameter("records_per_page"), 5);
        int noOfPages = (int) Math.ceil(noOfRecords * 1.0 / recordsPerPage);
        req.setAttribute("no_of_pages", noOfPages);
        req.setAttribute("current_page", currentPage);
        req.setAttribute("records_per_page", recordsPerPage);

    }

    private static int getCorrectIntValue(String value, int defaultValue) {
        try {
            int i = Integer.parseInt(value);
            if (i <= 0) {
                return defaultValue;
            }
        } catch (NumberFormatException e) {
            return defaultValue;
        }
        return Integer.parseInt(value);
    }

    public static String getGetAction(String action, String... parameters) {
        String base = CONTROLLER + action;
        StringJoiner stringJoiner = new StringJoiner("&", "&", "");
        for (int i = 0; i < parameters.length; i += 2) {
            stringJoiner.add(parameters[i] + "=" + parameters[i + 1]);
        }
        return base + (parameters.length > 0 ? stringJoiner : "");
    }

    public static boolean isPostMethod(HttpServletRequest request) {
        return request.getMethod().equals("POST");
    }

    public static void transferAttributeFromSessionToRequest(HttpServletRequest req, String... attributeNames) {
        for (String attributeName : attributeNames) {
            Object obj = req.getSession().getAttribute(attributeName);
            req.setAttribute(attributeName, obj);
            req.getSession().removeAttribute(attributeName);
        }
    }
}
