package com.epam.facultative.service;

import com.epam.facultative.daos.DaoFactory;
import com.epam.facultative.repositories.RepositoryFactory;
import com.epam.facultative.service.implementation.*;

public class ServiceFactory {

    private static final ServiceFactory INSTANCE = new ServiceFactory();
    private final GeneralService generalService;
    private final AdminService adminService;
    private final TeacherService teacherService;
    private final StudentService studentService;

    public ServiceFactory() {
        this.generalService = new GeneralServiceImpl(RepositoryFactory.getInstance().getGeneralRepository());
        this.adminService = new AdminServiceImpl(RepositoryFactory.getInstance().getAdminRepository());
        this.teacherService = new TeacherServiceImpl(RepositoryFactory.getInstance().getTeacherRepository());
        this.studentService = new StudentServiceImpl(RepositoryFactory.getInstance().getStudentRepository());
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
