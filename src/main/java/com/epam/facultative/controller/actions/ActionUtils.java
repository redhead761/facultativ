package com.epam.facultative.controller.actions;

import com.epam.facultative.dto.CourseDTO;
import com.epam.facultative.exception.ServiceException;
import com.epam.facultative.service.GeneralService;
import com.epam.facultative.utils.param_builders.ParamBuilderForQuery;
import jakarta.servlet.http.HttpServletRequest;

import java.util.List;
import java.util.Map;
import java.util.StringJoiner;

import static com.epam.facultative.controller.AttributeConstants.*;
import static com.epam.facultative.controller.actions.ActionNameConstants.CONTROLLER;
import static com.epam.facultative.utils.param_builders.ParamBuilderForQueryUtil.*;

public class ActionUtils {
    private ActionUtils() {
    }

    public static void setAllCourses(HttpServletRequest req, GeneralService generalService) throws ServiceException {
        String currentPage = req.getParameter(CURRENT_PAGE);
        String recordsPerPage = req.getParameter(RECORDS_PER_PAGE);

        String sort = req.getParameter(SORT);
        String order = req.getParameter(ORDER);
        String selectByCategory = req.getParameter(SELECT_BY_CATEGORY);
        String selectByTeacher = req.getParameter(SELECT_BY_TEACHER);

        ParamBuilderForQuery paramBuilderForQuery = courseParamBuilderForQuery().setSortFieldForCourse(sort)
                .setOrder(order)
                .setCategoryFilterForCourse(selectByCategory)
                .setTeacherFilterForCourse(selectByTeacher)
                .setLimits(currentPage, recordsPerPage);

        Map.Entry<Integer, List<CourseDTO>> coursesWithRows = generalService.getAllCourses(paramBuilderForQuery.getParam());

        testSetUp(req, coursesWithRows.getKey());

        req.setAttribute(COURSES, coursesWithRows.getValue());
        req.setAttribute(TEACHERS, generalService.getAllTeachers().getValue());
        req.setAttribute(CATEGORIES, generalService.getAllCategories().getValue());
    }

    public static void testSetUp(HttpServletRequest req, int noOfRecords) {
        int currentPage = getCorrectIntValue(req.getParameter(CURRENT_PAGE), 1);
        int recordsPerPage = getCorrectIntValue(req.getParameter(RECORDS_PER_PAGE), 5);
        int noOfPages = (int) Math.ceil(noOfRecords * 1.0 / recordsPerPage);
        req.setAttribute(NO_OF_PAGES, noOfPages);
        req.setAttribute(CURRENT_PAGE, currentPage);
        req.setAttribute(RECORDS_PER_PAGE, recordsPerPage);

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
