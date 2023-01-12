package com.epam.facultative.controller.actions;

import com.epam.facultative.dto.CourseDTO;
import com.epam.facultative.exception.ServiceException;
import com.epam.facultative.service.AdminService;
import com.epam.facultative.service.GeneralService;
import jakarta.servlet.http.HttpServletRequest;

import java.util.List;

public class ActionUtils {
    private ActionUtils() {
    }

    public static void removeRedundantAttribute(HttpServletRequest req) {
        req.getSession().removeAttribute("message");
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
        req.setAttribute("categories", adminService.getAllCategoriesPagination((currentPage - 1) * recordsPerPage, recordsPerPage));
        int noOfRecords = adminService.getNoOfRecordsCategories();
        setUpPagination(req, noOfRecords, currentPage, recordsPerPage);
    }

    public static void setUpPaginationForAllCourses(HttpServletRequest req, GeneralService generalService) throws ServiceException {
        int currentPage = getCurrentPage(req);
        int recordsPerPage = getRecordsPerPage(req);
        req.setAttribute("courses", generalService.getAllCourses((currentPage - 1) * recordsPerPage, recordsPerPage));
        int noOfRecords = generalService.getNoOfRecordsCourses();
        req.setAttribute("teachers", generalService.getAllTeachers());
        req.setAttribute("categories", generalService.getAllCategories());
        setUpPagination(req, noOfRecords, currentPage, recordsPerPage);
    }

    public static void setUpPaginationForAllStudents(HttpServletRequest req, AdminService adminService) throws ServiceException {
        int currentPage = getCurrentPage(req);
        int recordsPerPage = getRecordsPerPage(req);
        req.setAttribute("students", adminService.getAllStudentsPagination((currentPage - 1) * recordsPerPage, recordsPerPage));
        int noOfRecords = adminService.getNoOfRecordsStudents();
        setUpPagination(req, noOfRecords, currentPage, recordsPerPage);
    }

    public static void setUpPaginationForTeachers(HttpServletRequest req, AdminService adminService) throws ServiceException {
        int currentPage = getCurrentPage(req);
        int recordsPerPage = getRecordsPerPage(req);
        req.setAttribute("teachers", adminService.getAllTeachersPagination((currentPage - 1) * recordsPerPage, recordsPerPage));
        int noOfRecords = adminService.getNoOfRecordsTeachers();
        setUpPagination(req, noOfRecords, currentPage, recordsPerPage);
    }

    public static void sort(HttpServletRequest req, GeneralService generalService) throws ServiceException {
        String sortType = req.getParameter("sort_type");
        req.setAttribute("sort_type", sortType);
        int currentPage = getCurrentPage(req);
        int recordsPerPage = getRecordsPerPage(req);
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
        req.setAttribute("courses", courses);
        int noOfRecords = generalService.getNoOfRecordsCourses();
        setUpPagination(req, noOfRecords, currentPage, recordsPerPage);
        req.setAttribute("teachers", generalService.getAllTeachers());
        req.setAttribute("categories", generalService.getAllCategories());
    }

    public static void select(HttpServletRequest req, GeneralService generalService) throws ServiceException {
        String selectType = req.getParameter("select_type");
        req.setAttribute("select_type", selectType);
        int currentPage = getCurrentPage(req);
        int recordsPerPage = getRecordsPerPage(req);
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
        setUpPagination(req, noOfRecords, currentPage, recordsPerPage);
        req.setAttribute("teachers", generalService.getAllTeachers());
        req.setAttribute("categories", generalService.getAllCategories());
    }
}
