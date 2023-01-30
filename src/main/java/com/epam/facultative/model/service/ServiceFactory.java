package com.epam.facultative.model.service;

import com.epam.facultative.model.dao.DaoFactory;
import com.epam.facultative.model.service.implementation.AdminServiceImpl;
import com.epam.facultative.model.service.implementation.GeneralServiceImpl;
import com.epam.facultative.model.service.implementation.StudentServiceImpl;
import com.epam.facultative.model.service.implementation.TeacherServiceImpl;
import lombok.Getter;

/**
 * Service factory that provides concrete implementations of GeneralService, AdminService, TeacherService and StudentService classes
 *
 * @author Oleksandr Panchenko
 * @version 1.0
 */
@Getter
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

    /**
     * Obtains instance of ServiceFactory to get concrete Services
     *
     * @param daoFactory - pass concrete DAOFactory instance to get access to DAO
     * @return ServiceFactory instance
     */
    public static ServiceFactory getInstance(DaoFactory daoFactory) {
        return new ServiceFactory(daoFactory);
    }
}