package com.epam.facultative.controller.actions;

import com.epam.facultative.data_layer.entities.Role;
import com.epam.facultative.exception.ServiceException;
import com.epam.facultative.service.AdminService;
import com.epam.facultative.service.GeneralService;
import jakarta.servlet.http.HttpServletRequest;

import static com.epam.facultative.controller.actions.PageNameConstants.*;

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

    public static String chooseCabinet(HttpServletRequest req) {
        String path = null;
        Role role = (Role) req.getSession().getAttribute("role");
        switch (role) {
            case ADMIN -> path = MANAGE_COURSES_PAGE;
            case STUDENT -> path = STUDENT_PAGE;
            case TEACHER -> path = TEACHER_PAGE;
        }
        return path;
    }
}
