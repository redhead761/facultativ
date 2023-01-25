package com.epam.facultative.service;

import com.epam.facultative.data_layer.daos.DaoFactory;
import com.epam.facultative.service.implementation.*;

public class ServiceFactory {
    private final GeneralService generalService;
    private final AdminService adminService;
    private final TeacherService teacherService;
    private final StudentService studentService;


    private ServiceFactory(DaoFactory daoFactory) {
        this.generalService = new GeneralServiceImpl(daoFactory.getCourseDao(), daoFactory.getUserDao(), daoFactory.getCategoryDao(), daoFactory.getTeacherDao(), daoFactory.getStudentDao());
        this.adminService = new AdminServiceImpl(daoFactory.getCourseDao(), daoFactory.getCategoryDao(), daoFactory.getStudentDao(), daoFactory.getTeacherDao());
        this.teacherService = new TeacherServiceImpl(daoFactory.getCourseDao(), daoFactory.getStudentDao(), daoFactory.getTeacherDao());
        this.studentService = new StudentServiceImpl(daoFactory.getCourseDao(), daoFactory.getStudentDao());
    }

    public static ServiceFactory getInstance(DaoFactory daoFactory) {
        return new ServiceFactory(daoFactory);
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