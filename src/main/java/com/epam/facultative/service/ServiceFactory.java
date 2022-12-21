package com.epam.facultative.service;

import com.epam.facultative.daos.DaoFactory;
import com.epam.facultative.service.implementation.*;

public class ServiceFactory {

    private static final ServiceFactory INSTANCE = new ServiceFactory();
    private final GeneralService generalService;
    private final AdminService adminService;
    private final TeacherService teacherService;
    private final StudentService studentService;

    public ServiceFactory() {
        this.generalService = new GeneralServiceImpl(DaoFactory.getInstance().getCourseDao(),
                DaoFactory.getInstance().getUserDao(),
                DaoFactory.getInstance().getCategoryDao());
        this.adminService = new AdminServiceImpl(DaoFactory.getInstance().getCourseDao(),
                DaoFactory.getInstance().getCategoryDao(),
                DaoFactory.getInstance().getUserDao());
        this.teacherService = new TeacherServiceImpl(DaoFactory.getInstance().getCourseDao(),
                DaoFactory.getInstance().getUserDao());
        this.studentService = new StudentServiceImpl(DaoFactory.getInstance().getCourseDao(),
                DaoFactory.getInstance().getUserDao());
    }

    public static ServiceFactory getInstance() {
        return INSTANCE;
    }

    public GeneralService getGeneralService() {
        return generalService;
    }

    public AdminService getAdminService() {
        return adminService;
    }

    public TeacherService getTeacherService() {
        return teacherService;
    }

    public StudentService getStudentService() {
        return studentService;
    }
}
