package com.epam.facultative.controller.actions;

import com.epam.facultative.data_layer.entities.Category;
import com.epam.facultative.dto.CategoryDTO;
import com.epam.facultative.dto.CourseDTO;
import com.epam.facultative.dto.StudentDTO;
import com.epam.facultative.dto.TeacherDTO;
import com.epam.facultative.exception.ServiceException;
import com.epam.facultative.service.AdminService;
import com.epam.facultative.service.GeneralService;
import jakarta.servlet.http.HttpServletRequest;

import java.util.List;
import java.util.Map;
import java.util.StringJoiner;

import static com.epam.facultative.controller.actions.ActionNameConstants.CONTROLLER;

public class ActionUtils {
    private ActionUtils() {
    }

    public static int getCurrentPage(HttpServletRequest req) {
        if (req.getParameter("page") != null) {
            return Integer.parseInt(req.getParameter("page"));
        } else {
            return 1;
        }
    }

    public static int getRecordsPerPage(HttpServletRequest req) {
        if (req.getParameter("records_per_page") != null) {
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

    public static void setUpPaginationForAllCategories(HttpServletRequest req, AdminService adminService) throws ServiceException {
        int currentPage = getCurrentPage(req);
        int recordsPerPage = getRecordsPerPage(req);
        Map.Entry<Integer, List<CategoryDTO>> categoriesWithRows = adminService.getAllCategoriesPagination((currentPage - 1) * recordsPerPage, recordsPerPage);
        req.setAttribute("categories", categoriesWithRows.getValue());
        int noOfRecords = categoriesWithRows.getKey();
        setUpPagination(req, noOfRecords, currentPage, recordsPerPage);
    }

    public static void setUpPaginationForAllCourses(HttpServletRequest req, GeneralService generalService) throws ServiceException {
        int currentPage = getCurrentPage(req);
        int recordsPerPage = getRecordsPerPage(req);
        Map.Entry<Integer, List<CourseDTO>> coursesWithRows = generalService.getAllCourses((currentPage - 1) * recordsPerPage, recordsPerPage);
        req.setAttribute("courses", coursesWithRows.getValue());
        int noOfRecords = coursesWithRows.getKey();
        req.setAttribute("teachers", generalService.getAllTeachers());
        req.setAttribute("categories", generalService.getAllCategories());
        setUpPagination(req, noOfRecords, currentPage, recordsPerPage);
    }

    public static void setUpPaginationForAllStudents(HttpServletRequest req, AdminService adminService) throws ServiceException {
        int currentPage = getCurrentPage(req);
        int recordsPerPage = getRecordsPerPage(req);
        Map.Entry<Integer, List<StudentDTO>> studentsWithRows = adminService.getAllStudentsPagination((currentPage - 1) * recordsPerPage, recordsPerPage);
        req.setAttribute("students", studentsWithRows.getValue());
        int noOfRecords = studentsWithRows.getKey();
        setUpPagination(req, noOfRecords, currentPage, recordsPerPage);
    }

    public static void setUpPaginationForAllTeachers(HttpServletRequest req, AdminService adminService) throws ServiceException {
        int currentPage = getCurrentPage(req);
        int recordsPerPage = getRecordsPerPage(req);
        Map.Entry<Integer, List<TeacherDTO>> teachersWithRows = adminService.getAllTeachersPagination((currentPage - 1) * recordsPerPage, recordsPerPage);
        req.setAttribute("teachers", teachersWithRows.getValue());
        int noOfRecords = teachersWithRows.getKey();
        setUpPagination(req, noOfRecords, currentPage, recordsPerPage);
    }

    public static void setAllCourses(HttpServletRequest req, GeneralService generalService) throws ServiceException {
        String sortType = req.getParameter("sort_type");
        String selectType = req.getParameter("select_type");
        if (sortType != null && !sortType.isBlank()) {
            sort(req, generalService);
        } else if (selectType != null && !selectType.isBlank()) {
            select(req, generalService);
        } else {
            setUpPaginationForAllCourses(req, generalService);
        }
    }

    private static void sort(HttpServletRequest req, GeneralService generalService) throws ServiceException {
        String sortType = req.getParameter("sort_type");
        req.setAttribute("sort_type", sortType);
        int currentPage = getCurrentPage(req);
        int recordsPerPage = getRecordsPerPage(req);
        Map.Entry<Integer, List<CourseDTO>> coursesWithROws = null;
        switch (sortType) {
            case "alphabet" ->
                    coursesWithROws = generalService.sortCoursesByAlphabet((currentPage - 1) * recordsPerPage, recordsPerPage);
            case "reverse alphabet" ->
                    coursesWithROws = generalService.sortCoursesByAlphabetReverse((currentPage - 1) * recordsPerPage, recordsPerPage);
            case "duration" ->
                    coursesWithROws = generalService.sortCoursesByDuration((currentPage - 1) * recordsPerPage, recordsPerPage);
            case "amount students" ->
                    coursesWithROws = generalService.sortCoursesByAmountOfStudents((currentPage - 1) * recordsPerPage, recordsPerPage);
        }
        req.setAttribute("courses", coursesWithROws.getValue());
        int noOfRecords = coursesWithROws.getKey();
        setUpPagination(req, noOfRecords, currentPage, recordsPerPage);
        req.setAttribute("teachers", generalService.getAllTeachers());
        req.setAttribute("categories", generalService.getAllCategories());
    }

    private static void select(HttpServletRequest req, GeneralService generalService) throws ServiceException {
        String selectType = req.getParameter("select_type");
        req.setAttribute("select_type", selectType);
        int currentPage = getCurrentPage(req);
        int recordsPerPage = getRecordsPerPage(req);
        Map.Entry<Integer, List<CourseDTO>> coursesWithRows = null;
        switch (selectType) {
            case "by_teacher" -> {
                int teacherId = Integer.parseInt(req.getParameter("teacher_id"));
                req.setAttribute("teacher_id", teacherId);
                coursesWithRows = generalService.getCoursesByTeacher(teacherId, (currentPage - 1) * recordsPerPage, recordsPerPage);
            }
            case "by_category" -> {
                int categoryId = Integer.parseInt(req.getParameter("category_id"));
                req.setAttribute("category_id", categoryId);
                coursesWithRows = generalService.getCoursesByCategory(categoryId, (currentPage - 1) * recordsPerPage, recordsPerPage);
            }
        }
        req.setAttribute("courses", coursesWithRows.getValue());
        int noOfRecords = coursesWithRows.getKey();
        setUpPagination(req, noOfRecords, currentPage, recordsPerPage);
        req.setAttribute("teachers", generalService.getAllTeachers());
        req.setAttribute("categories", generalService.getAllCategories());
    }

    public static String getGetAction(String action, String... parameters) {
        String base = CONTROLLER + action;
        StringJoiner stringJoiner = new StringJoiner("&", "&", "");
        for (int i = 0; i < parameters.length; i += 2) {
            stringJoiner.add(parameters[i] + "=" + parameters[i + 1]);
        }
        return base + (parameters.length > 0 ? stringJoiner : "");
    }

}
