package com.epam.facultative.actions;

import com.epam.facultative.exception.ServiceException;
import com.epam.facultative.service.AdminService;
import com.epam.facultative.service.GeneralService;
import jakarta.servlet.http.HttpServletRequest;

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

    public static void setUpPaginationForCategories(HttpServletRequest req, AdminService adminService, int currentPage, int recordsPerPage) throws ServiceException {
        req.getSession().setAttribute("categories", adminService.getAllCategoriesPagination((currentPage - 1) * recordsPerPage, recordsPerPage));
        int noOfRecords = adminService.getNoOfRecordsCategories();
        int noOfPages = (int) Math.ceil(noOfRecords * 1.0 / recordsPerPage);
        req.getSession().setAttribute("noOfCategoriesPages", noOfPages);
        req.getSession().setAttribute("currentPage", currentPage);
    }

    public static void setUpPaginationForCourses(HttpServletRequest req, GeneralService generalService, int currentPage, int recordsPerPage) throws ServiceException {
        req.getSession().setAttribute("courses", generalService.getAllCourses((currentPage - 1) * recordsPerPage, recordsPerPage));
        int noOfRecords = generalService.getNoOfRecordsCourses();
        int noOfPages = (int) Math.ceil(noOfRecords * 1.0 / recordsPerPage);
        req.getSession().setAttribute("noOfCoursesPages", noOfPages);
        req.getSession().setAttribute("currentPage", currentPage);
    }

    public static void setUpPaginationForStudents(HttpServletRequest req, AdminService adminService, int currentPage, int recordsPerPage) throws ServiceException {
        req.getSession().setAttribute("students", adminService.getAllStudentsPagination((currentPage - 1) * recordsPerPage, recordsPerPage));
        int noOfRecords = adminService.getNoOfRecordsStudents();
        int noOfPages = (int) Math.ceil(noOfRecords * 1.0 / recordsPerPage);
        req.getSession().setAttribute("noOfStudentsPages", noOfPages);
        req.getSession().setAttribute("currentPage", currentPage);
    }

    public static void setUpPaginationForTeachers(HttpServletRequest req, AdminService adminService, int currentPage, int recordsPerPage) throws ServiceException {
        req.getSession().setAttribute("teachers", adminService.getAllTeachersPagination((currentPage - 1) * recordsPerPage, recordsPerPage));
        int noOfRecords = adminService.getNoOfRecordsTeachers();
        int noOfPages = (int) Math.ceil(noOfRecords * 1.0 / recordsPerPage);
        req.getSession().setAttribute("noOfTeachersPages", noOfPages);
        req.getSession().setAttribute("currentPage", currentPage);
    }

}
