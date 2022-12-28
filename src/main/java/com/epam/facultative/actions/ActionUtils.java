package com.epam.facultative.actions;

import com.epam.facultative.dto.CourseDTO;
import com.epam.facultative.entities.Role;
import com.epam.facultative.exception.ServiceException;
import com.epam.facultative.service.AdminService;
import com.epam.facultative.service.GeneralService;
import com.epam.facultative.service.StudentService;
import jakarta.servlet.http.HttpServletRequest;

import java.util.List;

import static com.epam.facultative.actions.PageNameConstants.*;

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

    public static void setUpPaginationForCategories(HttpServletRequest req, AdminService adminService, int currentPage, int recordsPerPage) throws ServiceException {
        req.setAttribute("categories", adminService.getAllCategoriesPagination((currentPage - 1) * recordsPerPage, recordsPerPage));
        int noOfRecords = adminService.getNoOfRecordsCategories();
        int noOfPages = (int) Math.ceil(noOfRecords * 1.0 / recordsPerPage);
        req.setAttribute("noOfCategoriesPages", noOfPages);
        req.setAttribute("currentPage", currentPage);
    }

    public static void setUpPaginationForCourses(HttpServletRequest req, GeneralService generalService, int currentPage, int recordsPerPage) throws ServiceException {
        req.setAttribute("courses", generalService.getAllCourses((currentPage - 1) * recordsPerPage, recordsPerPage));
        int noOfRecords = generalService.getNoOfRecordsCourses();
        int noOfPages = (int) Math.ceil(noOfRecords * 1.0 / recordsPerPage);
        req.setAttribute("noOfCoursesPages", noOfPages);
        req.setAttribute("currentPage", currentPage);
    }

    public static void setUpPaginationForStudents(HttpServletRequest req, AdminService adminService, int currentPage, int recordsPerPage) throws ServiceException {
        req.setAttribute("students", adminService.getAllStudentsPagination((currentPage - 1) * recordsPerPage, recordsPerPage));
        int noOfRecords = adminService.getNoOfRecordsStudents();
        int noOfPages = (int) Math.ceil(noOfRecords * 1.0 / recordsPerPage);
        req.setAttribute("noOfStudentsPages", noOfPages);
        req.setAttribute("currentPage", currentPage);
    }

    public static void setUpPaginationForTeachers(HttpServletRequest req, AdminService adminService, int currentPage, int recordsPerPage) throws ServiceException {
        req.setAttribute("teachers", adminService.getAllTeachersPagination((currentPage - 1) * recordsPerPage, recordsPerPage));
        int noOfRecords = adminService.getNoOfRecordsTeachers();
        int noOfPages = (int) Math.ceil(noOfRecords * 1.0 / recordsPerPage);
        req.setAttribute("noOfTeachersPages", noOfPages);
        req.setAttribute("currentPage", currentPage);
    }

    public static void setUpPagination(HttpServletRequest req, GeneralService generalService, int currentPage, int recordsPerPage, List<CourseDTO> courses) throws ServiceException {
        req.setAttribute("courses", courses);
        int noOfRecords = generalService.getNoOfRecordsCourses();
        int noOfPages = (int) Math.ceil(noOfRecords * 1.0 / recordsPerPage);
        req.setAttribute("noOfCoursesPages", noOfPages);
        req.setAttribute("currentPage", currentPage);
        req.setAttribute("teachers", generalService.getAllTeachers());
        req.setAttribute("categories", generalService.getAllCategories());
    }

    public static void setUpPaginationStudent(HttpServletRequest req, StudentService studentService, int currentPage, int recordsPerPage) {
        int noOfRecords = studentService.getNoOfRecordsCourses();
        int noOfPages = (int) Math.ceil(noOfRecords * 1.0 / recordsPerPage);
        req.setAttribute("noOfCoursesPages", noOfPages);
        req.setAttribute("currentPage", currentPage);
    }

}
